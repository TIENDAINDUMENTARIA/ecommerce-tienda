package com.ecommerce.ecommerce.service.user;

import com.ecommerce.ecommerce.domain.User;
import com.ecommerce.ecommerce.dto.user.UserRegisterDto;

public interface UserService {
    User createUser(UserRegisterDto userRegisterDto);
    User getLoggingUser();
    User getUserById(Long id_user);
}