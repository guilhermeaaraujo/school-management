package com.guilherme.schoolmanagement.domain.dto;

import com.guilherme.schoolmanagement.domain.entities.Teacher;

public record TeacherDTO(Long id, String firstName, String lastName) {

    public TeacherDTO(Teacher teacher) {
        this(teacher.getId(), teacher.getFirstName(), teacher.getLastName());
    }
}
