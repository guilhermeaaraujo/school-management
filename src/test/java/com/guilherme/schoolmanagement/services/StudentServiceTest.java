package com.guilherme.schoolmanagement.services;

import com.guilherme.schoolmanagement.domain.entities.Student;
import com.guilherme.schoolmanagement.domain.entities.User;
import com.guilherme.schoolmanagement.exceptions.ResourceNotFoundException;
import com.guilherme.schoolmanagement.repositories.StudentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private StudentRepository repository;

    @InjectMocks
    private StudentService service;

    @Test
    @DisplayName("Should return an list of students")
    void findAll() {
        Student t1 = new Student();
        Student t2 = new Student();

        when(repository.findAll()).thenReturn(List.of(t1, t2));

        List<Student> result = service.findAll();

        assertThat(result).hasSize(2);
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Should return a student by id")
    void findById() {
        Long id = 1L;
        Student student = new Student();
        student.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(student));

        Student result = service.findById(id);

        assertThat(result).isEqualTo(student);
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Should throw exception when a student does not exist")
    void findByIdShouldThrowException() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id)).isInstanceOf(ResourceNotFoundException.class);
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Should successfully create a new student")
    void insert() {
        User user = new User();
        Student student = new Student();
        student.setUser(user);
        student.setBirthDate(LocalDate.now());

        when(repository.save(any(Student.class))).thenReturn(student);
        when(userService.insert(any(User.class))).thenReturn(user);

        Student result = service.insert(student);

        assertThat(result).isEqualTo(student);
        verify(repository).save(student);
    }

    @Test
    @DisplayName("Should successfully delete an student by id")
    void deleteById() {
        Long id = 1L;

        service.deleteById(id);

        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Should successfully update an student")
    void update() {
        Long id = 1L;
        Student student = new Student();

        when(repository.getReferenceById(id)).thenReturn(student);
        when(repository.save(any(Student.class))).thenReturn(student);

        User user = new User();
        Student newStudent = new Student();

        Student result = service.update(id, newStudent);

        assertThat(result).isEqualTo(newStudent);
        verify(repository).save(newStudent);
    }
}