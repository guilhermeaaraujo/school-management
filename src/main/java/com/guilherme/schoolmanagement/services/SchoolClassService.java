package com.guilherme.schoolmanagement.services;

import com.guilherme.schoolmanagement.domain.entities.SchoolClass;
import com.guilherme.schoolmanagement.domain.entities.Student;
import com.guilherme.schoolmanagement.exceptions.ResourceNotFoundException;
import com.guilherme.schoolmanagement.repositories.SchoolClassRepository;
import com.guilherme.schoolmanagement.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolClassService {

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private StudentRepository studentRepository;

    public List<SchoolClass> findAll() {
        return schoolClassRepository.findAll();
    }

    public SchoolClass findById(Long id) {
        return schoolClassRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("School class not found, id: " + id)
        );
    }

    public SchoolClass insert(SchoolClass schoolClass) {
        return schoolClassRepository.save(schoolClass);
    }

    public void deleteById(Long id) {
        try {
            schoolClassRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("School class not found, id: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete this schoolClass");
        }
    }

    @Transactional
    public void addStudentToClass(Long studentId, Long classId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student not found, id: " + studentId)
        );

        SchoolClass schoolClass = schoolClassRepository.findById(classId).orElseThrow(
                () -> new ResourceNotFoundException("School class not found, id: " + classId)
        );

        schoolClass.getStudents().add(student);
        schoolClassRepository.save(schoolClass);
    }

    @Transactional
    public void removeStudentFromClass(Long studentId, Long classId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ResourceNotFoundException("Student not found, id: " + studentId)
        );

        SchoolClass schoolClass = schoolClassRepository.findById(classId).orElseThrow(
                () -> new ResourceNotFoundException("School class not found, id: " + classId)
        );

        schoolClass.getStudents().remove(student);
        schoolClassRepository.save(schoolClass);
    }
}
