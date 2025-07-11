/*package com.ecommerce.ecommerce.dto.payment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PaymentCreateDto(
    @NotNull(message = "El ID del pedido es obligatorio")
    // ID del pedido asociado
    Long orderId,

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor que 0")
    // Monto del pago
    Double amount,

    @NotBlank(message = "El método de pago es obligatorio")
    // Método de pago (STRIPE, PAYPAL, etc.)
    String paymentMethod,

    // Detalles adicionales del método de pago (por ejemplo, token de Stripe)
    String paymentDetails

) {

}*/
