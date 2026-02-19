package com.guilherme.schoolmanagement.controllers;

import com.guilherme.schoolmanagement.domain.dto.request.EntityRequestDTO;
import com.guilherme.schoolmanagement.domain.entities.Student;
import com.guilherme.schoolmanagement.domain.dto.response.StudentDTO;
import com.guilherme.schoolmanagement.domain.entities.User;
import com.guilherme.schoolmanagement.domain.enums.UserRole;
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
        List<StudentDTO> studentsDTO = students.stream().map(x -> new StudentDTO(x)).toList();

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
    public ResponseEntity<StudentDTO> insert(@RequestBody EntityRequestDTO requestDTO) {
        Student student = new Student(requestDTO.firstName(), requestDTO.lastName(), requestDTO.birthDate(), new User(requestDTO.email(), null, UserRole.STUDENT));
        student = service.insert(student);
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
    public ResponseEntity<StudentDTO> update(@PathVariable Long id, @RequestBody EntityRequestDTO requestDTO) {
        Student student = new Student(requestDTO.firstName(), requestDTO.lastName(), requestDTO.birthDate(), new User(requestDTO.email(), UserRole.STUDENT));
        student = service.update(id, student);
        return ResponseEntity.ok().body(new StudentDTO(student));
    }
}
