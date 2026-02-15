package com.guilherme.schoolmanagement.controllers;

import com.guilherme.schoolmanagement.config.TokenService;
import com.guilherme.schoolmanagement.domain.dto.request.UpdatePasswordRequest;
import com.guilherme.schoolmanagement.domain.entities.User;
import com.guilherme.schoolmanagement.domain.dto.request.LoginRequest;
import com.guilherme.schoolmanagement.domain.dto.response.LoginResponse;
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var authentication = authenticationManager.authenticate(usernamePassword);

        User user = (User) authentication.getPrincipal();
        String token = tokenService.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("/{userId}/updatepassword")
    public ResponseEntity<Void> updatePassword(@PathVariable Long userId, @RequestBody UpdatePasswordRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userService.findById(userId);
        if (!user.getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        userService.updatePassword(userId, request.password());
        return ResponseEntity.noContent().build();
    }
}
