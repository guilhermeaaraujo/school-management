package com.guilherme.schoolmanagement.domain.dto;

import com.guilherme.schoolmanagement.domain.Student;

public record StudentDTO(Long id, String name) {

    public StudentDTO(Student student) {
        this(student.getId(), student.getName());
    }
}
