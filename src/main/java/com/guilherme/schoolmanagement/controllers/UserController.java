package com.guilherme.schoolmanagement.controllers;

import com.guilherme.schoolmanagement.services.UserService;
import com.guilherme.schoolmanagement.domain.User;
import com.guilherme.schoolmanagement.domain.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<User> users = service.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();

        for (User x : users) {
            usersDTO.add(new UserDTO(x));
        }
        return ResponseEntity.ok().body(usersDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        User user = service.findById(id);
        return ResponseEntity.ok().body(new UserDTO(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDTO> insert(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userResponse = service.insert(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = service.update(id, user);
        return ResponseEntity.ok().body(new UserDTO(user));
    }
}
