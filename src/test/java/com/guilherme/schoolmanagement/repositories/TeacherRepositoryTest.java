package com.guilherme.schoolmanagement.repositories;

import com.guilherme.schoolmanagement.domain.entities.Teacher;
import com.guilherme.schoolmanagement.domain.entities.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TeacherRepositoryTest {

    @Autowired
    TeacherRepository repository;

    @Autowired
    EntityManager entityManager;

    @Test
    void findByUserId() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("test");

        this.entityManager.persist(user);

        Teacher teacher = new Teacher();
        teacher.setUser(user);

        this.entityManager.persist(teacher);

        Teacher result = this.repository.findByUserId(user.getId()).get();

        assertThat(result.getUser()).isEqualTo(user);
    }
}