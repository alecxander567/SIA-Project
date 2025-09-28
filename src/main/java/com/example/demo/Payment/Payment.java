package com.example.demo.Payment;

import com.example.demo.Orders.Order;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tbl_payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private double amount;
    private LocalDate paymentDate;

    private Integer productId;
    private String paymongoId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public enum PaymentType {
        Gcash, PayMaya, CashOnDelivery
    }

    // Constructors
    public Payment() {}

    // Getters and Setters
    public Integer getPaymentId() { return paymentId; }
    public void setPaymentId(Integer paymentId) { this.paymentId = paymentId; }

    public PaymentType getPaymentType() { return paymentType; }
    public void setPaymentType(PaymentType paymentType) { this.paymentType = paymentType; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public String getPaymongoId() { return paymongoId; }
    public void setPaymongoId(String paymongoId) { this.paymongoId = paymongoId; }

    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
}
