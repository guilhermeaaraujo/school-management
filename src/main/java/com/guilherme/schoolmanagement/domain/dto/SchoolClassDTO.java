package com.guilherme.schoolmanagement.domain.dto;

import com.guilherme.schoolmanagement.domain.entities.SchoolClass;
import com.guilherme.schoolmanagement.domain.entities.Subject;
import com.guilherme.schoolmanagement.domain.entities.Teacher;

import java.util.ArrayList;
import java.util.List;

public record SchoolClassDTO(Long id, SubjectDTO subject, TeacherDTO teacher, List<StudentDTO> students) {

    public SchoolClassDTO(SchoolClass schoolClass) {
        this(schoolClass.getId(),
                new SubjectDTO(schoolClass.getSubject()),
                new TeacherDTO(schoolClass.getTeacher()),
                schoolClass.getStudents().stream().map(student -> new StudentDTO(student)).toList());
    }
}
