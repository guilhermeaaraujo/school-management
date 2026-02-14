package com.guilherme.schoolmanagement.controllers;

import com.guilherme.schoolmanagement.domain.dto.SchoolClassDTO;
import com.guilherme.schoolmanagement.domain.entities.SchoolClass;
import com.guilherme.schoolmanagement.services.SchoolClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/classes")
public class SchoolClassController {

    @Autowired
    private SchoolClassService service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<SchoolClassDTO>> findAll() {
        List<SchoolClass> schoolClasss = service.findAll();
        List<SchoolClassDTO> schoolClasssDTO = new ArrayList<>();

        for (SchoolClass x : schoolClasss) {
            schoolClasssDTO.add(new SchoolClassDTO(x));
        }
        return ResponseEntity.ok().body(schoolClasssDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<SchoolClassDTO> findById(@PathVariable Long id) {
        SchoolClass schoolClass = service.findById(id);
        return ResponseEntity.ok().body(new SchoolClassDTO(schoolClass));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SchoolClassDTO> insert(@RequestBody SchoolClass schoolClass) {
        service.insert(schoolClass);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SchoolClassDTO(schoolClass));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
