package com.guilherme.schoolmanagement.domain.dto;

import com.guilherme.schoolmanagement.domain.entities.Student;
import com.guilherme.schoolmanagement.domain.entities.Teacher;

import java.time.LocalDate;

public record UserDataDTO(String firstName, String lastName, LocalDate birthDate) {

    public UserDataDTO(Student student) {
        this(student.getFirstName(), student.getLastName(), student.getBirthDate());
    }

    public UserDataDTO(Teacher teacher) {
        this(teacher.getFirstName(), teacher.getLastName(), teacher.getBirthDate());
    }
}
