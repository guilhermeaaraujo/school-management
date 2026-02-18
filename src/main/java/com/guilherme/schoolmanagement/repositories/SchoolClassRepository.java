package com.guilherme.schoolmanagement.repositories;

import com.guilherme.schoolmanagement.domain.entities.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {

    List<SchoolClass> findAllByStudentsId(Long studentId);

    List<SchoolClass> findAllByTeacherId(Long teacherId);
}
