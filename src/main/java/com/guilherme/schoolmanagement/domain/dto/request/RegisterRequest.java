package com.guilherme.schoolmanagement.domain.dto.request;

import com.guilherme.schoolmanagement.domain.enums.UserRole;

public record RegisterRequest(String email, String password, UserRole role) {
}
