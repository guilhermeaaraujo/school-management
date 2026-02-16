package com.guilherme.schoolmanagement.services;

import com.guilherme.schoolmanagement.domain.entities.Teacher;
import com.guilherme.schoolmanagement.domain.entities.User;
import com.guilherme.schoolmanagement.exceptions.ResourceNotFoundException;
import com.guilherme.schoolmanagement.repositories.TeacherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TeacherRepository repository;

    @InjectMocks
    private TeacherService service;

    @Test
    @DisplayName("Should return an list of teachers")
    void findAll() {
        Teacher t1 = new Teacher();
        Teacher t2 = new Teacher();

        when(repository.findAll()).thenReturn(List.of(t1, t2));

        List<Teacher> result = service.findAll();

        assertThat(result).hasSize(2);
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Should return a teacher by id")
    void findById() {
        Long id = 1L;
        Teacher teacher = new Teacher();
        teacher.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(teacher));

        Teacher result = service.findById(id);

        assertThat(result).isEqualTo(teacher);
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Should throw exception when a teacher does not exist")
    void findByIdShouldThrowException() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id)).isInstanceOf(ResourceNotFoundException.class);
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Should successfully create a new teacher")
    void insert() {
        User user = new User();
        Teacher teacher = new Teacher();
        teacher.setUser(user);
        teacher.setBirthDate(LocalDate.now());

        when(repository.save(any(Teacher.class))).thenReturn(teacher);
        when(userService.insert(any(User.class))).thenReturn(user);

        Teacher result = service.insert(teacher);

        assertThat(result).isEqualTo(teacher);
        verify(repository).save(teacher);
    }

    @Test
    @DisplayName("Should successfully delete an teacher by id")
    void deleteById() {
        Long id = 1L;

        service.deleteById(id);

        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Should successfully update an teacher")
    void update() {
        Long id = 1L;
        Teacher teacher = new Teacher();

        when(repository.getReferenceById(id)).thenReturn(teacher);
        when(repository.save(any(Teacher.class))).thenReturn(teacher);

        User user = new User();
        Teacher newTeacher = new Teacher();

        Teacher result = service.update(id, newTeacher);

        assertThat(result).isEqualTo(newTeacher);
        verify(repository).save(newTeacher);
    }

    @Test
    @DisplayName("Should successfully get an teacher by its user Id")
    void findByUserId() {
        Long id = 1L;
        Teacher teacher = new Teacher();

        when(repository.findByUserId(id)).thenReturn(Optional.of(teacher));

        Teacher result = service.findByUserId(id);

        assertThat(result).isEqualTo(teacher);
        verify(repository).findByUserId(id);
    }

    @Test
    @DisplayName("Should throw an exception when teacher does not exists")
    void findByUserIdShouldThrowException() {
        Long id = 1L;

        when(repository.findByUserId(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findByUserId(id)).isInstanceOf(ResourceNotFoundException.class);
        verify(repository).findByUserId(id);
    }
}