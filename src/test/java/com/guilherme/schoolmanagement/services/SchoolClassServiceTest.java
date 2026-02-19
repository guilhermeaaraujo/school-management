package com.guilherme.schoolmanagement.services;

import com.guilherme.schoolmanagement.domain.entities.SchoolClass;
import com.guilherme.schoolmanagement.domain.entities.Student;
import com.guilherme.schoolmanagement.domain.entities.Teacher;
import com.guilherme.schoolmanagement.domain.entities.User;
import com.guilherme.schoolmanagement.domain.enums.UserRole;
import com.guilherme.schoolmanagement.exceptions.ResourceNotFoundException;
import com.guilherme.schoolmanagement.repositories.SchoolClassRepository;
import com.guilherme.schoolmanagement.repositories.StudentRepository;
import com.guilherme.schoolmanagement.repositories.TeacherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SchoolClassServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private SchoolClassRepository repository;

    @InjectMocks
    private SchoolClassService service;

    @Test
    @DisplayName("Should return an list of school classes")
    void findAll() {
        List<SchoolClass> classes = List.of(new SchoolClass(), new SchoolClass());
        when(repository.findAll()).thenReturn(classes);

        List<SchoolClass> result = service.findAll();

        assertThat(result).hasSize(2);
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Should return a school class by Id")
    void findById() {
        Long id = 1L;
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(schoolClass));

        SchoolClass result = service.findById(id);

        assertThat(result.getId()).isEqualTo(id);
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Should throw exception when an school class does not exist")
    void findByIdShouldThrowException() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id)).isInstanceOf(ResourceNotFoundException.class);
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Should create a new class")
    void insert() {
        SchoolClass schoolClass = new SchoolClass();
        when(repository.save(any(SchoolClass.class))).thenReturn(schoolClass);

        SchoolClass result = service.insert(schoolClass);

        assertThat(result).isEqualTo(schoolClass);
        verify(repository).save(schoolClass);
    }

    @Test
    @DisplayName("Should successfully delete an class by Id")
    void deleteById() {
        Long id = 1L;

        service.deleteById(id);

        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Should successfully add a student to a class")
    void addStudentToClass() {
        Student student = new Student();
        Long studentId = 1L;
        student.setId(studentId);
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        SchoolClass schoolClass = new SchoolClass();
        Long classId = 1L;
        schoolClass.setId(classId);
        when(repository.findById(classId)).thenReturn(Optional.of(schoolClass));

        service.addStudentToClass(studentId, classId);

        assertThat(schoolClass.getStudents()).hasSize(1);
        verify(repository).save(schoolClass);
    }

    @Test
    @DisplayName("Should successfully remove a student from a class")
    void removeStudentFromClass() {
        Student student = new Student();
        Long studentId = 1L;
        student.setId(studentId);
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        SchoolClass schoolClass = new SchoolClass();
        Long classId = 1L;
        schoolClass.setId(classId);
        when(repository.findById(classId)).thenReturn(Optional.of(schoolClass));

        service.removeStudentFromClass(studentId, classId);

        assertThat(schoolClass.getStudents()).hasSize(0);
        verify(repository).save(schoolClass);
    }

    @Test
    @DisplayName("Should return all the classes the user is enrolled to if its a Student")
    void findAllByUserIdIfItsAStudent() {
        User user = new User();
        Long userId = 1L;
        user.setId(1L);
        UserRole role = UserRole.STUDENT;
        user.setRole(role);

        Student student = new Student();
        Long studentId = 1L;
        student.setId(studentId);

        when(studentRepository.findByUserId(userId)).thenReturn(Optional.of(student));

        List<SchoolClass> classes = List.of(new SchoolClass(), new SchoolClass());

        when(repository.findAllByStudentsId(studentId)).thenReturn(classes);

        List<SchoolClass> result = service.findAllByUserId(userId, role);

        assertThat(result).hasSize(2);
        verify(repository).findAllByStudentsId(studentId);
    }

    @DisplayName("Should return all the classes the user is enrolled to if its a Teacher")
    void findAllByUserIdIfItsATeacher() {
        User user = new User();
        Long userId = 1L;
        user.setId(1L);
        UserRole role = UserRole.STUDENT;
        user.setRole(role);

        Teacher teacher = new Teacher();
        Long teacherId = 1L;
        teacher.setId(teacherId);

        when(teacherRepository.findByUserId(userId)).thenReturn(Optional.of(teacher));

        List<SchoolClass> classes = List.of(new SchoolClass(), new SchoolClass());

        when(repository.findAllByStudentsId(teacherId)).thenReturn(classes);

        List<SchoolClass> result = service.findAllByUserId(userId, role);

        assertThat(result).hasSize(2);
        verify(repository).findAllByStudentsId(teacherId);
    }
}