/*package com.ecommerce.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.dto.payment.PaymentCreateDto;
import com.ecommerce.ecommerce.dto.payment.PaymentDto;
import com.ecommerce.ecommerce.service.payment.PaymentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/payment")
@AllArgsConstructor
public class PaymentController {
    private PaymentService paymentService;

    // Iniciar un nuevo pago
    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@Valid @RequestBody PaymentCreateDto paymentCreateDTO) {
        return ResponseEntity.ok(paymentService.createPayment(paymentCreateDTO));
    }

    // Confirmar un pago
    @PostMapping("/{paymentId}/confirm")
    public ResponseEntity<PaymentDto> confirmPayment(@PathVariable Long paymentId, @RequestParam String externalTransactionId) {
        return ResponseEntity.ok(paymentService.confirmPayment(paymentId, externalTransactionId));
    }
}
*/