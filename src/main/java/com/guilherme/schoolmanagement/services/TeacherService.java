package com.guilherme.schoolmanagement.services;

import com.guilherme.schoolmanagement.domain.entities.Teacher;
import com.guilherme.schoolmanagement.domain.entities.User;
import com.guilherme.schoolmanagement.repositories.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserService userService;

    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    public Teacher findById(Long id) {
        return teacherRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Resource not Found")
        );
    }

    public Teacher insert(Teacher teacher) {
        userService.insert(teacher.getUser());
        return teacherRepository.save(teacher);
    }

    public void deleteById(Long id) {
        try {
            teacherRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Resource not found");
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete this teacher");
        }
    }

    public Teacher update(Long id, Teacher newTeacher) {
        try {
            Teacher teacher = teacherRepository.getReferenceById(id);
            teacher.setFirstName(newTeacher.getFirstName());
            teacher.setLastName(newTeacher.getLastName());
            teacher.setBirthDate(newTeacher.getBirthDate());
            return teacherRepository.save(teacher);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Resource not found");
        }
    }
}
