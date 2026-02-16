package com.guilherme.schoolmanagement.services;

import com.guilherme.schoolmanagement.domain.entities.Subject;
import com.guilherme.schoolmanagement.domain.entities.User;
import com.guilherme.schoolmanagement.exceptions.ResourceNotFoundException;
import com.guilherme.schoolmanagement.repositories.SubjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubjectServiceTest {

    @Mock
    private SubjectRepository repository;

    @InjectMocks
    private SubjectService service;

    @Test
    @DisplayName("Should return an list of subjects")
    void findAll() {
        Subject t1 = new Subject();
        Subject t2 = new Subject();

        when(repository.findAll()).thenReturn(List.of(t1, t2));

        List<Subject> result = service.findAll();

        assertThat(result).hasSize(2);
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Should return a subject by id")
    void findById() {
        Long id = 1L;
        Subject subject = new Subject();
        subject.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(subject));

        Subject result = service.findById(id);

        assertThat(result).isEqualTo(subject);
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Should throw exception when a subject does not exist")
    void findByIdShouldThrowException() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(id)).isInstanceOf(ResourceNotFoundException.class);
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Should successfully create a new subject")
    void insert() {
        Subject subject = new Subject();

        when(repository.save(any(Subject.class))).thenReturn(subject);

        Subject result = service.insert(subject);

        assertThat(result).isEqualTo(subject);
        verify(repository).save(subject);
    }

    @Test
    @DisplayName("Should successfully delete an subject by id")
    void deleteById() {
        Long id = 1L;

        service.deleteById(id);

        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Should successfully update an subject")
    void update() {
        Long id = 1L;
        Subject subject = new Subject();

        when(repository.getReferenceById(id)).thenReturn(subject);
        when(repository.save(any(Subject.class))).thenReturn(subject);

        Subject newSubject = new Subject();

        Subject result = service.update(id, newSubject);

        assertThat(result).isEqualTo(newSubject);
        verify(repository).save(newSubject);
    }
}