package com.guilherme.schoolmanagement.services;

import com.guilherme.schoolmanagement.domain.entities.Student;
import com.guilherme.schoolmanagement.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findById(Long id) {
        return studentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Resource not Found")
        );
    }

    public Student insert(Student student) {
        return studentRepository.save(student);
    }

    public void deleteById(Long id) {
        try {
            studentRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Resource not found");
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
            throw new RuntimeException("Resource not found");
        }
    }
}
