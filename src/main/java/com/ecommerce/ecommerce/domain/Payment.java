/*package com.ecommerce.ecommerce.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_payment;

    @OneToOne(mappedBy = "payment")
    // Pedido asociado al pago
    private Order order;

    @Column(nullable = false)
    // Monto del pago
    private Double amount;

    @Column(nullable = false)
    // Método de pago (STRIPE, PAYPAL, TRANSFERENCIA)
    private String paymentMethod;

    @Column(nullable = false)
    // Estado del pago (PENDIENTE, COMPLETADO, FALLIDO)
    private String status;

    @Column(nullable = false)
    // Fecha del pago
    private LocalDateTime paymentDate;

    @Column
    // ID de la transacción proporcionado por la pasarela de pago
    private String externalTransactionId;
}*/