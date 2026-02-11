package com.guilherme.schoolmanagement.domain.dto;

import com.guilherme.schoolmanagement.domain.Teacher;

public record TeacherDTO(Long id, String name) {

    public TeacherDTO(Teacher teacher) {
        this(teacher.getId(), teacher.getName());
    }
}
