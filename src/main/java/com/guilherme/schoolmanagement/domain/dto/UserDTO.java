package com.guilherme.schoolmanagement.domain.dto;

import com.guilherme.schoolmanagement.domain.User;
import com.guilherme.schoolmanagement.domain.enums.UserRole;

public record UserDTO(Long id, String email, UserRole role) {

    public UserDTO(User user) {
        this(user.getId(), user.getEmail(), user.getRole());
    }
}
