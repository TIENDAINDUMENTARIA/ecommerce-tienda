package com.ecommerce.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.ecommerce.dto.cart.CartDto;
import com.ecommerce.ecommerce.dto.cartitem.CartItemCreateDto;
import com.ecommerce.ecommerce.service.cart.CartService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartDto> createCart() {
        return ResponseEntity.ok(cartService.createCart());
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartDto> getCart() {
        return ResponseEntity.ok(cartService.getCartByUserId());
    }

    @PostMapping("/items")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartDto> addItemToCart(@Valid @RequestBody CartItemCreateDto cartItemCreateDTO) {
        return ResponseEntity.ok(cartService.addItemToCart(cartItemCreateDTO));
    }

    @PatchMapping("/items/{cartItemId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CartDto> updateCartItem(@PathVariable Long cartItemId, @RequestBody Integer quantity) {
        return ResponseEntity.ok(cartService.updateCartItem(cartItemId, quantity));
    }

    @DeleteMapping("/items/{cartItemId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long cartItemId) {
        cartService.deleteCartItem(cartItemId);
        return ResponseEntity.noContent().build();
    }
}