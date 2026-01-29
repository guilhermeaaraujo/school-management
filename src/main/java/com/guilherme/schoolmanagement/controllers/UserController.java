package com.guilherme.schoolmanagement.controllers;

import com.guilherme.schoolmanagement.services.UserService;
import com.guilherme.schoolmanagement.domain.User;
import com.guilherme.schoolmanagement.domain.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<User> users = service.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();

        for (User x : users) {
            usersDTO.add(new UserDTO(x));
        }
        return ResponseEntity.ok().body(usersDTO);
    }
}
