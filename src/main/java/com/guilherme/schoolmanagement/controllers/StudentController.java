package com.guilherme.schoolmanagement.controllers;

import com.guilherme.schoolmanagement.config.SecurityConfig;
import com.guilherme.schoolmanagement.domain.dto.request.EntityRequestDTO;
import com.guilherme.schoolmanagement.domain.entities.Student;
import com.guilherme.schoolmanagement.domain.dto.response.StudentDTO;
import com.guilherme.schoolmanagement.domain.entities.User;
import com.guilherme.schoolmanagement.domain.enums.UserRole;
import com.guilherme.schoolmanagement.services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
@Tag(name = "students", description = "Controlador para recuperar/alterar dados de estudantes")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class StudentController {

    @Autowired
    private StudentService service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Listar estudantes", description = "Lista todos os estudantes do banco de dados")
    @ApiResponse(responseCode = "200", description = "Estudantes Retornados com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário nao tem acesso à operação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<List<StudentDTO>> findAll() {
        List<Student> students = service.findAll();
        List<StudentDTO> studentsDTO = students.stream().map(x -> new StudentDTO(x)).toList();

        return ResponseEntity.ok().body(studentsDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Listar estudante por id", description = "Retorna um estudante por id")
    @ApiResponse(responseCode = "200", description = "Estudante retornado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário nao tem acesso à operação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<StudentDTO> findById(@PathVariable Long id) {
        Student student = service.findById(id);
        return ResponseEntity.ok().body(new StudentDTO(student));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Criar estudante", description = "Insere um novo estudante no banco de dados")
    @ApiResponse(responseCode = "201", description = "Estudante criado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário nao tem acesso à operação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<StudentDTO> insert(@RequestBody EntityRequestDTO requestDTO) {
        Student student = new Student(requestDTO.firstName(), requestDTO.lastName(), requestDTO.birthDate(), new User(requestDTO.email(), null, UserRole.STUDENT));
        student = service.insert(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(new StudentDTO(student));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar estudante", description = "Deleta um estudante do banco de dados")
    @ApiResponse(responseCode = "200", description = "Estudante deletado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário nao tem acesso à operação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar estudante", description = "Deleta um estudante do banco de dados")
    @ApiResponse(responseCode = "200", description = "Estudante atualizado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário nao tem acesso à operação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<StudentDTO> update(@PathVariable Long id, @RequestBody EntityRequestDTO requestDTO) {
        Student student = new Student(requestDTO.firstName(), requestDTO.lastName(), requestDTO.birthDate(), new User(requestDTO.email(), UserRole.STUDENT));
        student = service.update(id, student);
        return ResponseEntity.ok().body(new StudentDTO(student));
    }
}
