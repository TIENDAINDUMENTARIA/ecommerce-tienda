package com.ecommerce.ecommerce.service.cart;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.domain.Cart;
import com.ecommerce.ecommerce.domain.CartItem;
import com.ecommerce.ecommerce.domain.Product;
import com.ecommerce.ecommerce.domain.User;
import com.ecommerce.ecommerce.dto.cart.CartDto;
import com.ecommerce.ecommerce.dto.cartitem.CartItemCreateDto;
import com.ecommerce.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.ecommerce.mappers.cart.CartMapper;
import com.ecommerce.ecommerce.repository.CartItemRepository;
import com.ecommerce.ecommerce.repository.CartRepository;
import com.ecommerce.ecommerce.repository.ProductRepository;
import com.ecommerce.ecommerce.service.user.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartMapper cartMapper;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService; // Cambiamos UserRepository por UserService
    private final ProductRepository productRepository;

    @Override
    public CartDto createCart() {
        // Obtener el usuario autenticado
        User user = userService.getLoggingUser();
        
        // Verificar si ya existe un carrito
        Cart cart = cartRepository.findByUserId(user.getId_user())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setTotalPrice(0.0);
                    return cartRepository.save(newCart);
                });
        
        return cartMapper.cartToCartDTO(cart);
    }

    @Override
    public CartDto getCartByUserId() {
        // Obtener el usuario autenticado
        User user = userService.getLoggingUser();
        
        // Obtener el carrito del usuario
        Cart cart = cartRepository.findByUserId(user.getId_user())
                .orElseThrow(() -> new ResourceNotFoundException("Carrito para el usuario no existe."));
        return cartMapper.cartToCartDTO(cart);
    }

    @Override
    public CartDto addItemToCart(CartItemCreateDto cartItemCreateDTO) {
        // Obtener el usuario autenticado
        User user = userService.getLoggingUser();
        
        // Buscar o crear carrito
        Cart cart = cartRepository.findByUserId(user.getId_user())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setTotalPrice(0.0);
                    return cartRepository.save(newCart);
                });
        
        // Validar producto
        Product product = productRepository.findById(cartItemCreateDTO.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto con ID: " + cartItemCreateDTO.productId() + " no existe."));
        
        // Validar stock
        if (product.getStock() < cartItemCreateDTO.quantity()) {
            throw new IllegalArgumentException("Stock insuficiente para el producto: " + product.getName());
        }
    
        // Verificar si el producto ya está en el carrito
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId_product().equals(cartItemCreateDTO.productId()))
                .findFirst();
    
        CartItem cartItem;
        if (existingItem.isPresent()) {
            // Si el producto ya está, actualizar la cantidad
            cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + cartItemCreateDTO.quantity());
        } else {
            // Crear nuevo ítem del carrito
            cartItem = cartMapper.cartItemCreateDtoToCartItem(cartItemCreateDTO);
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setUnitPrice(product.getPrice());
            cart.getItems().add(cartItem);
        }
        
        // Actualizar precio total
        cart.setTotalPrice(cart.getItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
                .sum());
        
        cartRepository.save(cart);
        return cartMapper.cartToCartDTO(cart);
    }

    @Override
    public CartDto updateCartItem(Long cartItemId, Integer quantity) {
        // Validar ítem del carrito
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Ítem del carrito con ID: " + cartItemId + " no existe."));
        
        // Validar que el carrito pertenece al usuario autenticado
        User user = userService.getLoggingUser();
        if (!cartItem.getCart().getUser().getId_user().equals(user.getId_user())) {
            throw new SecurityException("El ítem del carrito no pertenece al usuario autenticado.");
        }

        // Validar stock
        Product product = cartItem.getProduct();
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Stock insuficiente para el producto: " + product.getName());
        }

        // Actualizar cantidad
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        // Actualizar precio total del carrito
        Cart cart = cartItem.getCart();
        cart.setTotalPrice(cart.getItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
                .sum());
        
        cartRepository.save(cart);
        return cartMapper.cartToCartDTO(cart);
    }

    @Override
    public void deleteCartItem(Long cartItemId) {
        // Validar ítem del carrito
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Ítem del carrito con ID: " + cartItemId + " no existe."));
        
        // Validar que el carrito pertenece al usuario autenticado
        User user = userService.getLoggingUser();
        if (!cartItem.getCart().getUser().getId_user().equals(user.getId_user())) {
            throw new SecurityException("El ítem del carrito no pertenece al usuario autenticado.");
        }

        // Eliminar ítem y actualizar precio total
        Cart cart = cartItem.getCart();
        cart.getItems().remove(cartItem);
        cart.setTotalPrice(cart.getItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
                .sum());
        
        cartItemRepository.delete(cartItem);
        cartRepository.save(cart);
    }
}