package com.guilherme.schoolmanagement.services;

import com.guilherme.schoolmanagement.domain.entities.Subject;
import com.guilherme.schoolmanagement.repositories.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public Subject findById(Long id) {
        return subjectRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Resource not Found")
        );
    }

    public Subject insert(Subject subject) {
        return subjectRepository.save(subject);
    }

    public void deleteById(Long id) {
        try {
            subjectRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Resource not found");
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete this subject");
        }
    }

    public Subject update(Long id, Subject newSubject) {
        try {
            Subject subject = subjectRepository.getReferenceById(id);
            subject.setName(newSubject.getName());
            return subjectRepository.save(subject);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Resource not found");
        }
    }
}
