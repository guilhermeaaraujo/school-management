package com.guilherme.schoolmanagement.repositories;

import com.guilherme.schoolmanagement.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
