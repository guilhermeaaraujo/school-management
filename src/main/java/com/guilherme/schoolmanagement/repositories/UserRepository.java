package com.guilherme.schoolmanagement.repositories;

import com.guilherme.schoolmanagement.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
