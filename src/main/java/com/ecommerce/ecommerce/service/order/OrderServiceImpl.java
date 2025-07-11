package com.ecommerce.ecommerce.service.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.domain.Cart;
import com.ecommerce.ecommerce.domain.Order;
import com.ecommerce.ecommerce.domain.OrderItem;
import com.ecommerce.ecommerce.domain.Product;
import com.ecommerce.ecommerce.domain.User;
import com.ecommerce.ecommerce.domain.enums.RoleEnumUser;
import com.ecommerce.ecommerce.domain.enums.StatusEnumOrder;
import com.ecommerce.ecommerce.dto.order.OrderCreateDto;
import com.ecommerce.ecommerce.dto.order.OrderDto;
import com.ecommerce.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.ecommerce.mappers.order.OrderMapper;
import com.ecommerce.ecommerce.repository.CartRepository;
import com.ecommerce.ecommerce.repository.OrderRepository;
import com.ecommerce.ecommerce.repository.ProductRepository;
import com.ecommerce.ecommerce.service.user.UserService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    @Override
    @Transactional
    public OrderDto createOrder(OrderCreateDto orderCreateDTO) {
        // Obtener el usuario autenticado
        User user = userService.getLoggingUser();
        
        // Obtener el carrito del usuario
        Cart cart = cartRepository.findByUserId(user.getId_user())
                .orElseThrow(() -> new ResourceNotFoundException("Carrito para el usuario no existe."));
        
        // Validar que el carrito no esté vacío
        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("El carrito está vacío. No se puede crear una orden.");
        }

        // Crear la orden
        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(StatusEnumOrder.PENDIENTE);
        
        // Procesar los ítems del carrito
        for (var cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();
            if (product.getStock() < cartItem.getQuantity()) {
                throw new IllegalArgumentException("Stock insuficiente para el producto: " + product.getName());
            }
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getUnitPrice());
            order.getItems().add(orderItem);
        }
        
        order.setTotal(cart.getTotalPrice());
        orderRepository.save(order);
        
        // Limpiar el carrito
        cart.getItems().clear();
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
        
        return orderMapper.orderToOrderDto(order);
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido con ID: " + id + " no existe."));
        // Validar que la orden pertenece al usuario autenticado
        User user = userService.getLoggingUser();
        if (!order.getUser().getId_user().equals(user.getId_user()) && !user.getRole().equals(RoleEnumUser.ADMIN)) {
            throw new SecurityException("No tienes permiso para acceder a esta orden.");
        }
        return orderMapper.orderToOrderDto(order);
    }

    @Override
    public List<OrderDto> getOrdersByUserId() {
        // Obtener el usuario autenticado
        User user = userService.getLoggingUser();
        List<Order> orders;
        if (user.getRole().equals(RoleEnumUser.ADMIN)) {
            // Los administradores pueden ver todas las órdenes
            orders = orderRepository.findAll();
        } else {
            // Los usuarios solo ven sus propias órdenes
            orders = orderRepository.findByUserId(user.getId_user());
        }
        return orders.stream()
                .map(orderMapper::orderToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido con ID: " + id + " no existe."));
        // Validar que la orden pertenece al usuario autenticado
        User user = userService.getLoggingUser();
        if (!order.getUser().getId_user().equals(user.getId_user()) && !user.getRole().equals(RoleEnumUser.ADMIN)) {
            throw new SecurityException("No tienes permiso para cancelar esta orden.");
        }
        if (order.getStatus() == StatusEnumOrder.CANCELADO) {
            throw new IllegalStateException("El pedido ya está cancelado.");
        }
        if (order.getStatus() != StatusEnumOrder.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden cancelar pedidos en estado PENDIENTE.");
        }
        for (OrderItem orderItem : order.getItems()) {
            Product product = orderItem.getProduct();
            product.setStock(product.getStock() + orderItem.getQuantity());
            productRepository.save(product);
        }
        order.setStatus(StatusEnumOrder.CANCELADO);
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long id, StatusEnumOrder newStatus) {
        // Solo los administradores pueden actualizar el estado de una orden
        User user = userService.getLoggingUser();
        if (!user.getRole().equals(RoleEnumUser.ADMIN)) {
            throw new SecurityException("Solo los administradores pueden actualizar el estado de las órdenes.");
        }
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido con ID: " + id + " no existe."));
        if (!isValidStatusTransition(order.getStatus(), newStatus)) {
            throw new IllegalStateException(
                    String.format("Transición de estado de %s a %s no permitida.", order.getStatus(), newStatus));
        }
        order.setStatus(newStatus);
        orderRepository.save(order);
    }

    private boolean isValidStatusTransition(StatusEnumOrder currentStatus, StatusEnumOrder newStatus) {
        return switch (currentStatus) {
            case PENDIENTE -> newStatus == StatusEnumOrder.CONFIRMADO || newStatus == StatusEnumOrder.CANCELADO;
            case CONFIRMADO -> newStatus == StatusEnumOrder.ENVIADO || newStatus == StatusEnumOrder.CANCELADO;
            case ENVIADO -> newStatus == StatusEnumOrder.ENTREGADO;
            case ENTREGADO, CANCELADO -> false;
            default -> false;
        };
    }
}