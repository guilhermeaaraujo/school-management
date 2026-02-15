package com.guilherme.schoolmanagement.services;

import com.guilherme.schoolmanagement.domain.entities.User;
import com.guilherme.schoolmanagement.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Resource not Found")
        );
    }

    public User insert(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) throw new RuntimeException("This email is already taken");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void updatePassword(Long id, String newPassword) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
