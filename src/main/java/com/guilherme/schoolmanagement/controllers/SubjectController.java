package com.guilherme.schoolmanagement.controllers;

import com.guilherme.schoolmanagement.domain.Subject;
import com.guilherme.schoolmanagement.domain.dto.SubjectDTO;
import com.guilherme.schoolmanagement.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    @Autowired
    private SubjectService service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<SubjectDTO>> findAll() {
        List<Subject> subjects = service.findAll();
        List<SubjectDTO> subjectsDTO = new ArrayList<>();

        for (Subject x : subjects) {
            subjectsDTO.add(new SubjectDTO(x));
        }
        return ResponseEntity.ok().body(subjectsDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> findById(@PathVariable Long id) {
        Subject subject = service.findById(id);
        return ResponseEntity.ok().body(new SubjectDTO(subject));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SubjectDTO> insert(@RequestBody Subject subject) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new SubjectDTO(subject));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<SubjectDTO> update(@PathVariable Long id, @RequestBody Subject subject) {
        subject = service.update(id, subject);
        return ResponseEntity.ok().body(new SubjectDTO(subject));
    }
}
