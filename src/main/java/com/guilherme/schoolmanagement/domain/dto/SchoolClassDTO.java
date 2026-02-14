package com.guilherme.schoolmanagement.domain.dto;

import com.guilherme.schoolmanagement.domain.entities.SchoolClass;
import com.guilherme.schoolmanagement.domain.entities.Subject;
import com.guilherme.schoolmanagement.domain.entities.Teacher;

public record SchoolClassDTO(Long id, Subject subject, Teacher teacher) {

    public SchoolClassDTO(SchoolClass schoolClass) {
        this(schoolClass.getId(), schoolClass.getSubject(), schoolClass.getTeacher());
    }
}
