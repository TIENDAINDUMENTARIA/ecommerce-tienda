package com.ecommerce.ecommerce.dto.user;

import com.ecommerce.ecommerce.domain.enums.RoleEnumUser;

public record UserDto(
    Long id_user,
    String username,
    String email,
    RoleEnumUser role) {

}