package com.guilherme.schoolmanagement.controllers;

import com.guilherme.schoolmanagement.config.SecurityConfig;
import com.guilherme.schoolmanagement.domain.entities.Subject;
import com.guilherme.schoolmanagement.domain.dto.response.SubjectDTO;
import com.guilherme.schoolmanagement.services.SubjectService;
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
@RequestMapping("/subjects")
@Tag(name = "subjects", description = "Controlador para recuperar/alterar dados de disciplinas")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class SubjectController {

    @Autowired
    private SubjectService service;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Listar disciplinas", description = "Lista todas as disciplinas do banco de dados")
    @ApiResponse(responseCode = "200", description = "Disciplinas retornadas com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário nao tem acesso à operação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<List<SubjectDTO>> findAll() {
        List<Subject> subjects = service.findAll();
        List<SubjectDTO> subjectsDTO = subjects.stream().map(x -> new SubjectDTO(x)).toList();

        return ResponseEntity.ok().body(subjectsDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Listar disciplina por id", description = "Retorna uma disciplina por id")
    @ApiResponse(responseCode = "200", description = "Disciplina retornada com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário nao tem acesso à operação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<SubjectDTO> findById(@PathVariable Long id) {
        Subject subject = service.findById(id);
        return ResponseEntity.ok().body(new SubjectDTO(subject));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Criar disciplina", description = "Insere uma nova disciplina no banco de dados")
    @ApiResponse(responseCode = "201", description = "Disciplina criada com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário nao tem acesso à operação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<SubjectDTO> insert(@RequestBody Subject subject) {
        service.insert(subject);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SubjectDTO(subject));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar disciplina", description = "Deleta uma disciplina do banco de dados")
    @ApiResponse(responseCode = "200", description = "Disciplina deletada com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário nao tem acesso à operação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar disciplina", description = "Deleta uma disciplina do banco de dados")
    @ApiResponse(responseCode = "200", description = "Disciplina atualizada com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário nao tem acesso à operação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<SubjectDTO> update(@PathVariable Long id, @RequestBody Subject subject) {
        subject = service.update(id, subject);
        return ResponseEntity.ok().body(new SubjectDTO(subject));
    }
}
