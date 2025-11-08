package com.example.demo.Orders;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Item.Item;
import com.example.demo.Item.ItemRepository;
import com.example.demo.Payment.Payment;
import com.example.demo.Payment.PaymentRepository;
import com.example.demo.Users.UserRepository;
import com.example.demo.Users.Users;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void assignRiderToOrder(Integer orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getPosition() != Users.Position.RIDER) {
            throw new RuntimeException("Only users with position RIDER can be assigned as employee");
        }

        order.setEmployee(user);
        orderRepository.save(order);
    }

    @Transactional
    public void cancelOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getItem() != null) {
            Item item = order.getItem();
            item.setQuantity(item.getQuantity() + order.getQuantity());
            itemRepository.save(item);
        }

        List<Payment> payments = paymentRepository.findByOrder_OrderId(orderId);
        if (!payments.isEmpty()) {
            paymentRepository.deleteAll(payments);
        }

        orderRepository.delete(order);
    }
}
