package com.guilherme.schoolmanagement.domain.dto.request;

import java.time.LocalDate;

public record EntityRequestDTO(String firstName, String lastName, LocalDate birthDate, String email) {
}
