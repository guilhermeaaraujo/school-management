package com.guilherme.schoolmanagement.controllers;

import com.guilherme.schoolmanagement.domain.entities.Student;
import com.guilherme.schoolmanagement.domain.dto.StudentDTO;
import com.guilherme.schoolmanagement.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<StudentDTO>> findAll() {
        List<Student> students = service.findAll();
        List<StudentDTO> studentsDTO = new ArrayList<>();

        for (Student x : students) {
            studentsDTO.add(new StudentDTO(x));
        }
        return ResponseEntity.ok().body(studentsDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> findById(@PathVariable Long id) {
        Student student = service.findById(id);
        return ResponseEntity.ok().body(new StudentDTO(student));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<StudentDTO> insert(@RequestBody Student student) {
        service.insert(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(new StudentDTO(student));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> update(@PathVariable Long id, @RequestBody Student student) {
        student = service.update(id, student);
        return ResponseEntity.ok().body(new StudentDTO(student));
    }
}
