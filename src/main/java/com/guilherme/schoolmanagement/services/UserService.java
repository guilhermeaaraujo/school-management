package com.guilherme.schoolmanagement.services;

import com.guilherme.schoolmanagement.domain.User;
import com.guilherme.schoolmanagement.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Resource not Found")
        );
    }

    public User insert(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) throw new RuntimeException("This email is already taken");
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Resource not found");
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete this user");
        }
    }

    public User update(Long id, User newUser) {
        try {
            User user = userRepository.getReferenceById(id);
            user.setEmail(newUser.getEmail());
            user.setPassword(newUser.getPassword());
            user.setRole(newUser.getRole());
            return userRepository.save(user);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Resource not found");
        }
    }
}
