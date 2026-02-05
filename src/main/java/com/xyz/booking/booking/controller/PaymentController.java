package com.xyz.booking.booking.controller;

import com.xyz.booking.booking.model.Payment;
import com.xyz.booking.booking.model.PaymentStatus;
import com.xyz.booking.booking.repository.PaymentRepository;
import com.xyz.booking.common.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @PostMapping("/{paymentId}/retry")
    public ResponseEntity<String> retryPayment(@PathVariable Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new BusinessException("Payment not found")
                );

        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            return ResponseEntity.ok("Payment already successful");
        }

        boolean success =
                com.xyz.booking.booking.service.PaymentGateway
                        .charge(payment.getAmount());

        if (success) {
            payment.markSuccess();
            paymentRepository.save(payment);
            return ResponseEntity.ok("Payment retry successful");
        }

        payment.markFailed();
        paymentRepository.save(payment);
        return ResponseEntity.ok("Payment retry failed");
    }
}
