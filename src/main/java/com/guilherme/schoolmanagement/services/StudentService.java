package com.guilherme.schoolmanagement.services;

import com.guilherme.schoolmanagement.domain.entities.Student;
import com.guilherme.schoolmanagement.exceptions.ResourceNotFoundException;
import com.guilherme.schoolmanagement.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserService userService;

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findById(Long id) {
        return studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Student not found, id: " + id)
        );
    }

    public Student insert(Student student) {
        student.getUser().setPassword(
                student.getBirthDate().format(
                        DateTimeFormatter.ofPattern("ddMMyyyy")
                )
        );

        userService.insert(student.getUser());
        return studentRepository.save(student);
    }

    public void deleteById(Long id) {
        try {
            studentRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Student not found, id: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete this student");
        }
    }

    public Student update(Long id, Student newStudent) {
        try {
            Student student = studentRepository.getReferenceById(id);
            student.setFirstName(newStudent.getFirstName());
            student.setLastName(newStudent.getLastName());
            student.setBirthDate(newStudent.getBirthDate());
            return studentRepository.save(student);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Student not found, id: " + id);
        }
    }
}
