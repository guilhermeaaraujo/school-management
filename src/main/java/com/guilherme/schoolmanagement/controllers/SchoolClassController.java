package com.guilherme.schoolmanagement.controllers;

import com.guilherme.schoolmanagement.config.SecurityConfig;
import com.guilherme.schoolmanagement.domain.dto.response.SchoolClassDTO;
import com.guilherme.schoolmanagement.domain.entities.SchoolClass;
import com.guilherme.schoolmanagement.domain.entities.User;
import com.guilherme.schoolmanagement.services.SchoolClassService;
import com.guilherme.schoolmanagement.services.UserService;
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
@RequestMapping("/classes")
@Tag(name = "classes", description = "Controlador para recuperar/alterar dados de salas de aula")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class SchoolClassController {

    @Autowired
    private SchoolClassService service;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    @Operation(summary = "Listar sala de aulas", description = "Lista todas as sala de aulas do banco de dados")
    @ApiResponse(responseCode = "200", description = "Salas de aulas retornadas com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário nao tem acesso à operação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<List<SchoolClassDTO>> findAll() {
        List<SchoolClass> schoolClass = service.findAll();
        List<SchoolClassDTO> schoolClassDTO = schoolClass.stream().map(x -> new SchoolClassDTO(x)).toList();

        return ResponseEntity.ok().body(schoolClassDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    @Operation(summary = "Listar sala de aula por id", description = "Retorna uma sala de aula por id")
    @ApiResponse(responseCode = "200", description = "Sala de aula retornada com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário nao tem acesso à operação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<SchoolClassDTO> findById(@PathVariable Long id) {
        SchoolClass schoolClass = service.findById(id);
        return ResponseEntity.ok().body(new SchoolClassDTO(schoolClass));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Criar sala de aula", description = "Insere uma nova sala de aula no banco de dados")
    @ApiResponse(responseCode = "201", description = "Sala de aula criada com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário nao tem acesso à operação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<SchoolClassDTO> insert(@RequestBody SchoolClass schoolClass) {
        service.insert(schoolClass);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SchoolClassDTO(schoolClass));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar sala de aula", description = "Deleta uma sala de aula do banco de dados")
    @ApiResponse(responseCode = "200", description = "Sala de aula deletada com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário nao tem acesso à operação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/{classId}/students/{studentId}")
    @Operation(summary = "Adicionar estudante", description = "Adiciona um estudante a uma sala de aula")
    @ApiResponse(responseCode = "200", description = "Sala de aula atualizada com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário nao tem acesso à operação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<Void> addStudentToClass(@PathVariable Long studentId, @PathVariable Long classId) {
        service.addStudentToClass(studentId, classId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/{classId}/students/{studentId}")
    @Operation(summary = "Remover estudante", description = "Remover um estudante a uma sala de aula")
    @ApiResponse(responseCode = "200", description = "Sala de aula atualizada com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário nao tem acesso à operação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<Void> removeStudentFromClass(@PathVariable Long studentId, @PathVariable Long classId) {
        service.removeStudentFromClass(studentId, classId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    @Operation(summary = "Listar minhas salas de aulas", description = "Retorna as salas de aula em que o usuário está matriculado")
    @ApiResponse(responseCode = "200", description = "Salas de aulas retornadas com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário nao tem acesso à operação")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<List<SchoolClassDTO>> findAuthenticatedUserClasses() {
        User user = userService.findAuthenticatedUserDetails();
        List<SchoolClass> classes = service.findAllByUserId(user.getId(), user.getRole());
        List<SchoolClassDTO> schoolClassDTO = classes.stream().map(x -> new SchoolClassDTO(x)).toList();

        return ResponseEntity.ok().body(schoolClassDTO);
    }
}
