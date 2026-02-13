package com.guilherme.schoolmanagement.domain.dto;

import com.guilherme.schoolmanagement.domain.entities.Student;

public record StudentDTO(Long id, String firstName, String lastName) {

    public StudentDTO(Student student) {
        this(student.getId(), student.getFirstName(), student.getLastName());
    }
}
