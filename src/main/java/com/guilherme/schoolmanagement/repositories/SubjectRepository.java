package com.guilherme.schoolmanagement.repositories;

import com.guilherme.schoolmanagement.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
