package com.guilherme.schoolmanagement.repositories;

import com.guilherme.schoolmanagement.domain.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "SELECT * FROM students WHERE user_id = :id", nativeQuery = true)
    Optional<Student> findByUserId(@Param("id") Long Id);
}
