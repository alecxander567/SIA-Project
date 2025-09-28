package com.example.demo.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/payments/webhook")
@CrossOrigin(origins = "http://localhost:5137")
public class PaymentWebhook {

    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping
    public ResponseEntity<String> handleWebhook(@RequestBody Map<String, Object> payload) {
        try {
            Map<String, Object> data = (Map<String, Object>) payload.get("data");
            if (data == null) return ResponseEntity.badRequest().body("Invalid payload");

            String paymentId = (String) data.get("id");
            Map<String, Object> attributes = (Map<String, Object>) data.get("attributes");
            String status = (String) attributes.get("status");

            Payment payment = paymentRepository.findByPaymongoId(paymentId);
            if (payment != null) {
                paymentRepository.save(payment);
            }

            return ResponseEntity.ok("Webhook received");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error handling webhook: " + e.getMessage());
        }
    }
}
