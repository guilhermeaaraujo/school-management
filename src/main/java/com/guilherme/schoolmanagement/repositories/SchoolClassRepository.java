package com.guilherme.schoolmanagement.repositories;

import com.guilherme.schoolmanagement.domain.entities.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
}
