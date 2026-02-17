package com.guilherme.schoolmanagement.domain.dto.response;

import com.guilherme.schoolmanagement.domain.entities.Student;
import com.guilherme.schoolmanagement.domain.entities.Teacher;
import com.guilherme.schoolmanagement.domain.entities.User;
import com.guilherme.schoolmanagement.domain.enums.UserRole;

public record UserDTO(Long id, String email, UserRole role, UserDataDTO data) {

    public UserDTO(User user, Student student) {
        this(user.getId(), user.getEmail(), user.getRole(), new UserDataDTO(student));
    }

    public UserDTO(User user, Teacher teacher) {
        this(user.getId(), user.getEmail(), user.getRole(), new UserDataDTO(teacher));
    }
}
