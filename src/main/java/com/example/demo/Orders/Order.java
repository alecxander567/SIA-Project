package com.example.demo.Orders;

import com.example.demo.Item.Item;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;
import com.example.demo.Users.Users;

@Entity
@Table(name = "tbl_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", referencedColumnName = "Item_ID")
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", referencedColumnName = "user_id")
    private Users employee;

    private String customer_name;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    @Column(name = "order_name")
    private String orderName;

    @Column(precision = 10, scale = 2)
    private BigDecimal total_price;

    private String address;
    private String status = "Pending";
    private String contactNumber;
    private LocalDate order_date;
    private String payment_type;

    public enum Unit { kilo, box, piece }

    public Order() {}

    // Getters & Setters
    public Integer getOrderId() {
        return orderId;
    }
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }

    public String getCustomer_name() { return customer_name; }
    public void setCustomer_name(String customer_name) { this.customer_name = customer_name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Unit getUnit() { return unit; }
    public void setUnit(Unit unit) { this.unit = unit; }

    public BigDecimal getTotal_price() {
        return total_price;
    }
    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    @Column(name = "contact_number")
    public String getContactNumber() {
        return contactNumber;
    }
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public LocalDate getOrder_date() { return order_date; }
    public void setOrder_date(LocalDate order_date) { this.order_date = order_date; }

    public String getPayment_type() { return payment_type; }
    public void setPayment_type(String payment_type) { this.payment_type = payment_type; }

    public String getOrderName() {
        return orderName;
    }
    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Users getEmployee() {
        return employee;
    }

    public void setEmployee(Users employee) {
        this.employee = employee;
    }
}
