package com.ecommerce.ecommerce.service.cart;

import com.ecommerce.ecommerce.dto.cart.CartDto;
import com.ecommerce.ecommerce.dto.cartitem.CartItemCreateDto;

public interface CartService {
    // Crear un nuevo carrito para un usuario
    CartDto createCart();

    // Obtener el carrito de un usuario
    CartDto getCartByUserId();

    // Agregar un ítem al carrito
    CartDto addItemToCart(CartItemCreateDto cartItemCreateDTO);

    // Actualizar la cantidad de un ítem en el carrito
    CartDto updateCartItem(Long cartItemId, Integer quantity);

    // Eliminar un ítem del carrito
    void deleteCartItem(Long cartItemId);

}
