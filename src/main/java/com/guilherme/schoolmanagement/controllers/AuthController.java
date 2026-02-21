package com.guilherme.schoolmanagement.controllers;

import com.guilherme.schoolmanagement.config.SecurityConfig;
import com.guilherme.schoolmanagement.config.TokenService;
import com.guilherme.schoolmanagement.domain.dto.response.UserDTO;
import com.guilherme.schoolmanagement.domain.dto.request.UpdatePasswordRequest;
import com.guilherme.schoolmanagement.domain.entities.Student;
import com.guilherme.schoolmanagement.domain.entities.Teacher;
import com.guilherme.schoolmanagement.domain.entities.User;
import com.guilherme.schoolmanagement.domain.dto.request.LoginRequest;
import com.guilherme.schoolmanagement.domain.dto.response.LoginResponse;
import com.guilherme.schoolmanagement.domain.enums.UserRole;
import com.guilherme.schoolmanagement.services.StudentService;
import com.guilherme.schoolmanagement.services.TeacherService;
import com.guilherme.schoolmanagement.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "authentication", description = "Controlador para autenticação e recuperar/alterar dados do usuário autenticado")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/login")
    @Operation(summary = "Loga o usuário na API", description = "Metódo de login")
    @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso")
    @ApiResponse(responseCode = "400", description = "Email ou senha incorretos")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var authentication = authenticationManager.authenticate(usernamePassword);

        User user = (User) authentication.getPrincipal();
        String token = tokenService.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("/me/updatepassword")
    @Operation(summary = "Atualiza a senha do usuário", description = "Metódo para alterar a senha do usuário autenticado")
    @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Email ou senha incorretos")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<Void> updatePassword(@RequestBody UpdatePasswordRequest request) {
        User authenticatedUser = userService.findAuthenticatedUserDetails();

        userService.updatePassword(authenticatedUser.getId(), request.password());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/me")
    @Operation(summary = "Busca dados do usuário autenticado", description = "Metódo para recuperar os dados do usuário autenticado")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<UserDTO> findAuthenticatedUserDetails() {
        User authenticatedUser = userService.findAuthenticatedUserDetails();

        UserDTO userDTO;

        if (authenticatedUser.getRole() == UserRole.STUDENT) {
            Student student = studentService.findByUserId(authenticatedUser.getId());

            userDTO = new UserDTO(authenticatedUser, student);
        } else if (authenticatedUser.getRole() == UserRole.TEACHER) {
            Teacher teacher = teacherService.findByUserId(authenticatedUser.getId());

            userDTO = new UserDTO(authenticatedUser, teacher);
        } else {
            userDTO = new UserDTO(authenticatedUser.getId(), authenticatedUser.getEmail(), authenticatedUser.getRole(), null);
        }

        return ResponseEntity.ok().body(userDTO);
    }

}
