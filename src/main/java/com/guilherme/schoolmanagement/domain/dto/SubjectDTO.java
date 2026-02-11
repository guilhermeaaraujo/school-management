package com.guilherme.schoolmanagement.domain.dto;

import com.guilherme.schoolmanagement.domain.Subject;

public record SubjectDTO(Long id, String name) {

    public SubjectDTO(Subject subject) {
        this(subject.getId(), subject.getName());
    }
}
