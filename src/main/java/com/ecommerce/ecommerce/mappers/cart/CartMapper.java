package com.ecommerce.ecommerce.mappers.cart;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ecommerce.ecommerce.domain.Cart;
import com.ecommerce.ecommerce.domain.CartItem;
import com.ecommerce.ecommerce.dto.cart.CartDto;
import com.ecommerce.ecommerce.dto.cartitem.CartItemCreateDto;
import com.ecommerce.ecommerce.dto.cartitem.CartItemDto;
import com.ecommerce.ecommerce.mappers.product.ProductMapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CartMapper {
    // Mapear Cart a CartDTO sin userId
    CartDto cartToCartDTO(Cart cart);

    // Mapear CartItem a CartItemDTO
    @Mapping(target = "product", source = "product")
    CartItemDto cartItemToCartItemDTO(CartItem cartItem);

    // Mapear CartItemCreateDTO a CartItem
    @Mapping(target = "id_cart_item", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "product", ignore = true)
    CartItem cartItemCreateDtoToCartItem(CartItemCreateDto cartItemCreateDTO);
}
