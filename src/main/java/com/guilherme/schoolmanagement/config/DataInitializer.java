package com.guilherme.schoolmanagement.config;

import com.guilherme.schoolmanagement.domain.entities.User;
import com.guilherme.schoolmanagement.domain.enums.UserRole;
import com.guilherme.schoolmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        String email = "admin@school.com";
        if (userRepository.findByEmail(email).isEmpty()) {
            User admin = new User(
                    email, passwordEncoder.encode("admin"), UserRole.ADMIN
            );
            userRepository.save(admin);
        }
    }
}
