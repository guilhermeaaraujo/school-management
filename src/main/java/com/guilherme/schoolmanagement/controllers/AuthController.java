package com.guilherme.schoolmanagement.controllers;

import com.guilherme.schoolmanagement.config.TokenService;
import com.guilherme.schoolmanagement.domain.dto.StudentDTO;
import com.guilherme.schoolmanagement.domain.dto.TeacherDTO;
import com.guilherme.schoolmanagement.domain.dto.UserDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var authentication = authenticationManager.authenticate(usernamePassword);

        User user = (User) authentication.getPrincipal();
        String token = tokenService.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("/me/updatepassword")
    public ResponseEntity<Void> updatePassword(@RequestBody UpdatePasswordRequest request) {
        User authenticatedUser = userService.findAuthenticatedUserDetails();
        String email = authenticatedUser.getEmail();

        User user = userService.findById(authenticatedUser.getId());
        if (!user.getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        userService.updatePassword(authenticatedUser.getId(), request.password());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/me")
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
