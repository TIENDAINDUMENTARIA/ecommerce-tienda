/*package com.ecommerce.ecommerce.service.payment;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.domain.Order;
import com.ecommerce.ecommerce.domain.Payment;
import com.ecommerce.ecommerce.dto.payment.PaymentCreateDto;
import com.ecommerce.ecommerce.dto.payment.PaymentDto;
import com.ecommerce.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.ecommerce.mappers.payment.PaymentMapper;
import com.ecommerce.ecommerce.repository.OrderRepository;
import com.ecommerce.ecommerce.repository.PaymentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentServiceImpl {
    private PaymentMapper paymentMapper;
    private PaymentRepository paymentRepository;
    private OrderRepository orderRepository;

    // Simulación de integración con pasarela de pago
    private StripeService stripeService; // Debes implementar esta clase según tu pasarela

    @Override
    public PaymentDto createPayment(PaymentCreateDto paymentCreateDTO) {
        // Validar pedido
        Order order = orderRepository.findById(paymentCreateDTO.orderId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido con ID: " + paymentCreateDTO.getOrderId() + " no existe."));
        
        // Validar monto
        if (!order.getTotal().equals(paymentCreateDTO.amount())) {
            throw new IllegalArgumentException("El monto del pago no coincide con el total del pedido.");
        }

        // Crear pago
        Payment payment = paymentMapper.paymentCreateDTOToPayment(paymentCreateDTO);
        payment.setOrder(order);
        payment.setStatus("PENDIENTE");
        payment.setPaymentDate(LocalDateTime.now());

        // Simular llamada a pasarela de pago
        String externalTransactionId = stripeService.processPayment(paymentCreateDTO.getAmount(), paymentCreateDTO.getPaymentDetails());
        payment.setExternalTransactionId(externalTransactionId);

        paymentRepository.save(payment);
        return paymentMapper.paymentToPaymentDTO(payment);
    }

    @Override
    public PaymentDto confirmPayment(Long paymentId, String externalTransactionId) {
        // Validar pago
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Pago con ID: " + paymentId + " no existe."));
        
        // Simular verificación con pasarela de pago
        if (stripeService.verifyPayment(externalTransactionId)) {
            payment.setStatus("COMPLETADO");
            payment.getOrder().setStatus("CONFIRMADO");
            orderRepository.save(payment.getOrder());
        } else {
            payment.setStatus("FALLIDO");
        }

        paymentRepository.save(payment);
        return paymentMapper.paymentToPaymentDTO(payment);
    }
}
*/