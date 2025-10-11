package com.example.demo.Orders;

import com.example.demo.Item.Item;
import com.example.demo.Item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.math.BigDecimal;
import com.example.demo.Notification.NotificationService;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private NotificationService notificationService;

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        if (order.getItem() != null && order.getItem().getId() != null) {
            Item item = itemRepository.findById(order.getItem().getId())
                    .orElseThrow(() -> new RuntimeException("Item not found"));
            order.setItem(item);
            order.setOrderName(item.getItemName());
        } else {
            throw new RuntimeException("Item ID is required");
        }

        // In OrderController.createOrder()
        if (order.getStatus() == null || order.getStatus().isEmpty()) {
            if ("CashOnDelivery".equals(order.getPayment_type())) {
                order.setStatus("Pending");
            } else if ("Gcash".equals(order.getPayment_type()) || "PayMaya".equals(order.getPayment_type())) {
                order.setStatus("Pending Payment");
            } else {
                order.setStatus("Pending");
            }
        }

        if (order.getContactNumber() == null || order.getContactNumber().isEmpty()) {
            throw new RuntimeException("Contact number is required");
        }

        return orderRepository.save(order);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(
            @PathVariable Integer orderId,
            @RequestBody Order updatedOrder
    ) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    int oldQuantity = order.getQuantity();
                    int newQuantity = updatedOrder.getQuantity();

                    order.setQuantity(newQuantity);
                    order.setAddress(updatedOrder.getAddress());

                    if (order.getItem() != null) {
                        Item item = order.getItem();

                        int stockChange = oldQuantity - newQuantity;
                        item.setQuantity(item.getQuantity() + stockChange);

                        itemRepository.save(item);

                        BigDecimal quantityBD = BigDecimal.valueOf(newQuantity);
                        BigDecimal totalPrice = item.getPrice().multiply(quantityBD);
                        order.setTotal_price(totalPrice);
                    }

                    Order savedOrder = orderRepository.save(order);
                    return ResponseEntity.ok(savedOrder);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{orderId}/delivered")
    public ResponseEntity<Order> markAsDelivered(@PathVariable Integer orderId) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    order.setStatus("Delivered");
                    Order savedOrder = orderRepository.save(order);

                    // ✅ Send notification
                    String message = "Order #" + orderId + " (" + order.getOrderName() + ") has been delivered.";
                    notificationService.createNotification("Order Delivered", message);

                    return ResponseEntity.ok(savedOrder);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Add method to mark as paid (to be called by webhook)
    @PutMapping("/{orderId}/paid")
    public ResponseEntity<Order> markAsPaid(@PathVariable Integer orderId) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    order.setStatus("Paid");
                    Order savedOrder = orderRepository.save(order);
                    return ResponseEntity.ok(savedOrder);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Integer orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Order cancelled successfully");
    }

    @GetMapping("/by-phone/{phone}")
    public List<Order> getOrdersByPhone(@PathVariable("phone") String phone) {
        return orderRepository.findByContactNumber(phone);
    }

    @PostMapping("/orders/{orderId}/assign-rider/{userId}")
    public void assignRider(@PathVariable Integer orderId, @PathVariable Long userId) {
        orderService.assignRiderToOrder(orderId, userId);
    }
}