package com.guilherme.schoolmanagement.controllers;

import com.guilherme.schoolmanagement.domain.entities.Teacher;
import com.guilherme.schoolmanagement.domain.dto.TeacherDTO;
import com.guilherme.schoolmanagement.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<TeacherDTO>> findAll() {
        List<Teacher> teachers = service.findAll();
        List<TeacherDTO> teachersDTO = new ArrayList<>();

        for (Teacher x : teachers) {
            teachersDTO.add(new TeacherDTO(x));
        }
        return ResponseEntity.ok().body(teachersDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> findById(@PathVariable Long id) {
        Teacher teacher = service.findById(id);
        return ResponseEntity.ok().body(new TeacherDTO(teacher));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TeacherDTO> insert(@RequestBody Teacher teacher) {
        service.insert(teacher);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TeacherDTO(teacher));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TeacherDTO> update(@PathVariable Long id, @RequestBody Teacher teacher) {
        teacher = service.update(id, teacher);
        return ResponseEntity.ok().body(new TeacherDTO(teacher));
    }
}
