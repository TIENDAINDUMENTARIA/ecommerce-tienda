package com.ecommerce.ecommerce.dto.cart;

import java.util.List;

import com.ecommerce.ecommerce.dto.cartitem.CartItemDto;

public record CartDto(
    Long id_cart,
    Double totalPrice,
    List<CartItemDto> items

) {

}
