/*package com.ecommerce.ecommerce.mappers.payment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ecommerce.ecommerce.domain.Payment;
import com.ecommerce.ecommerce.dto.payment.PaymentCreateDto;
import com.ecommerce.ecommerce.dto.payment.PaymentDto;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    // Mapear Payment a PaymentDTO
    @Mapping(target = "orderId", source = "order.id_order")
    PaymentDto paymentToPaymentDTO(Payment payment);

    // Mapear PaymentCreateDTO a Payment
    @Mapping(target = "id_payment", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "paymentDate", ignore = true)
    @Mapping(target = "externalTransactionId", ignore = true)
    Payment paymentCreateDTOToPayment(PaymentCreateDto paymentCreateDTO);
}
*/