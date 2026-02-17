package com.guilherme.schoolmanagement.domain.dto.response;

import com.guilherme.schoolmanagement.domain.entities.SchoolClass;

import java.util.List;

public record SchoolClassDTO(Long id, SubjectDTO subject, TeacherDTO teacher, List<StudentDTO> students) {

    public SchoolClassDTO(SchoolClass schoolClass) {
        this(schoolClass.getId(),
                new SubjectDTO(schoolClass.getSubject()),
                new TeacherDTO(schoolClass.getTeacher()),
                schoolClass.getStudents().stream().map(student -> new StudentDTO(student)).toList());
    }
}
