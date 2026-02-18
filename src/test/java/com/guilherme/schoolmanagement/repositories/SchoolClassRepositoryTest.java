package com.guilherme.schoolmanagement.repositories;

import com.guilherme.schoolmanagement.domain.entities.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class SchoolClassRepositoryTest {

    @Autowired
    private SchoolClassRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Should return a list of school classes by a Student id")
    void findAllByStudentsId() {
        Student student = new Student();
        entityManager.persist(student);

        SchoolClass schoolClass = new SchoolClass();
        entityManager.persist(schoolClass);

        SchoolClass schoolClass2 = new SchoolClass();
        entityManager.persist(schoolClass2);

        student.getClasses().add(schoolClass);
        student.getClasses().add(schoolClass2);
        schoolClass.getStudents().add(student);
        schoolClass2.getStudents().add(student);

        List<SchoolClass> result = repository.findAllByStudentsId(student.getId());
        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("Should return a list of school classes by a Teacher id")
    void findAllByTeacherId() {
        Teacher teacher = new Teacher();
        entityManager.persist(teacher);

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setTeacher(teacher);
        entityManager.persist(schoolClass);

        SchoolClass schoolClass2 = new SchoolClass();
        schoolClass2.setTeacher(teacher);
        entityManager.persist(schoolClass2);

        teacher.getClasses().add(schoolClass);
        teacher.getClasses().add(schoolClass2);

        List<SchoolClass> result = repository.findAllByTeacherId(teacher.getId());
        assertThat(result).hasSize(2);
    }
}