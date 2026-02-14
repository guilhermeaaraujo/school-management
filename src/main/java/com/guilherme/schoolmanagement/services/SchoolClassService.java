package com.guilherme.schoolmanagement.services;

import com.guilherme.schoolmanagement.domain.entities.SchoolClass;
import com.guilherme.schoolmanagement.repositories.SchoolClassRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolClassService {

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    public List<SchoolClass> findAll() {
        return schoolClassRepository.findAll();
    }

    public SchoolClass findById(Long id) {
        return schoolClassRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Resource not Found")
        );
    }

    public SchoolClass insert(SchoolClass schoolClass) {
        return schoolClassRepository.save(schoolClass);
    }

    public void deleteById(Long id) {
        try {
            schoolClassRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Resource not found");
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete this schoolClass");
        }
    }
}
