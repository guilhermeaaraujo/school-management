package com.guilherme.schoolmanagement.services;

import com.guilherme.schoolmanagement.domain.entities.User;
import com.guilherme.schoolmanagement.exceptions.ResourceNotFoundException;
import com.guilherme.schoolmanagement.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    @DisplayName("Should successfully return an user by id")
    void findById() {
        Long id = 1L;
        User user = new User();
        user.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(user));

        User result = service.findById(id);

        assertThat(result.getId()).isEqualTo(id);
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Should throw a RuntimeException when an user does not exists")
    void findByIdShouldThrowException() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id)).isInstanceOf(ResourceNotFoundException.class);
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Should successfully create a new user")
    void insert() {
        User user = new User();
        user.setPassword("123");
        when(repository.save(user)).thenReturn(user);
        when(passwordEncoder.encode("123")).thenReturn("test");

        User result = service.insert(user);

        assertThat(result).isEqualTo(user);
        verify(repository).save(user);
    }

    @Test
    @DisplayName("Should throw a RuntimeException when an user already exists")
    void insertShouldThrowException() {
        String email = "test@email.com";

        User user = new User();
        user.setEmail(email);

        when(repository.findByEmail(email)).thenReturn(Optional.of(user));

        assertThatThrownBy(() -> service.insert(user)).isInstanceOf(RuntimeException.class);
        verify(repository).findByEmail(email);
    }


    @Test
    @DisplayName("Should successfully update a password of an user")
    void updatePassword() {
        Long id = 1L;
        User user = new User();
        user.setId(id);
        user.setPassword("123");

        when(repository.findById(id)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("456")).thenReturn("test456");
        when(repository.save(user)).thenReturn(user);

        service.updatePassword(id, "456");

        assertThat(user.getPassword()).isEqualTo("test456");

        verify(passwordEncoder).encode("456");
        verify(repository).save(user);
    }
}