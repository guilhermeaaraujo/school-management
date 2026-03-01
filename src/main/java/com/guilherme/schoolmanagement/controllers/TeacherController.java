package com.guilherme.schoolmanagement.controllers;

import com.guilherme.schoolmanagement.config.SecurityConfig;
import com.guilherme.schoolmanagement.domain.dto.request.EntityRequestDTO;
import com.guilherme.schoolmanagement.domain.entities.Student;
import com.guilherme.schoolmanagement.domain.entities.Teacher;
import com.guilherme.schoolmanagement.domain.dto.response.TeacherDTO;
import com.guilherme.schoolmanagement.domain.entities.User;
import com.guilherme.schoolmanagement.domain.enums.UserRole;
import com.guilherme.schoolmanagement.services.TeacherService;
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
@RequestMapping("/teachers")
@Tag(name = "teachers", description = "Controlador para recuperar/alterar dados de professores")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class TeacherController {

    @Autowired
    private TeacherService service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Listar professores", description = "Lista todos os professores do banco de dados")
    @ApiResponse(responseCode = "200", description = "Professores Retornados com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário nao tem acesso à operação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<List<TeacherDTO>> findAll() {
        List<Teacher> teachers = service.findAll();
        List<TeacherDTO> teachersDTO = teachers.stream().map(x -> new TeacherDTO(x)).toList();

        return ResponseEntity.ok().body(teachersDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Listar professor por id", description = "Retorna um professor por id")
    @ApiResponse(responseCode = "200", description = "Professor retornado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário nao tem acesso à operação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<TeacherDTO> findById(@PathVariable Long id) {
        Teacher teacher = service.findById(id);
        return ResponseEntity.ok().body(new TeacherDTO(teacher));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Criar professor", description = "Insere um novo professor no banco de dados")
    @ApiResponse(responseCode = "201", description = "Professor criado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário nao tem acesso à operação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<TeacherDTO> insert(@RequestBody EntityRequestDTO requestDTO) {
        Teacher teacher = new Teacher(requestDTO.firstName(), requestDTO.lastName(), requestDTO.birthDate(), new User(requestDTO.email(), null, UserRole.TEACHER));
        teacher = service.insert(teacher);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TeacherDTO(teacher));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar professor", description = "Deleta um professor do banco de dados")
    @ApiResponse(responseCode = "200", description = "Professor deletado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário nao tem acesso à operação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar professor", description = "Deleta um professor do banco de dados")
    @ApiResponse(responseCode = "200", description = "Professor atualizado com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário nao tem acesso à operação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<TeacherDTO> update(@PathVariable Long id, @RequestBody EntityRequestDTO requestDTO) {
        Teacher teacher = new Teacher(requestDTO.firstName(), requestDTO.lastName(), requestDTO.birthDate(), new User(requestDTO.email(), UserRole.TEACHER));
        teacher = service.update(id, teacher);
        return ResponseEntity.ok().body(new TeacherDTO(teacher));
    }
}
