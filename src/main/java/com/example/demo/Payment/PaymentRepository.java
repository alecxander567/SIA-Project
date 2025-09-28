package com.example.demo.Payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Payment findByPaymongoId(String paymongoId);
    List<Payment> findByOrder_OrderId(Integer orderId);

}

