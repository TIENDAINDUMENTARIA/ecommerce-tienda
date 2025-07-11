package com.ecommerce.ecommerce.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ecommerce.ecommerce.domain.enums.StatusEnumOrder;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_order;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    // Fecha de creación del pedido
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnumOrder status;

    @Column(nullable = false)
    // Total del pedido
    private Double total;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    // Lista de ítems del pedido
    private List<OrderItem> items = new ArrayList<>();

    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    // Pago asociado al pedido
    private Payment payment;*/
}