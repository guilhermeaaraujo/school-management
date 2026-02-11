package com.guilherme.schoolmanagement.repositories;

import com.guilherme.schoolmanagement.domain.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
