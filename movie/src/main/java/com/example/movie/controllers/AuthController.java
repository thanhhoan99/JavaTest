package com.example.movie.controllers;

import com.example.movie.dtos.*;
import com.example.movie.entities.Role;
import com.example.movie.entities.User;
import com.example.movie.exceptions.HttpException;
import com.example.movie.repositories.RoleJpaRepository;
import com.example.movie.services.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.movie.services.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) throws Exception {
        LoginResponseDto result = this.userService.login(request);
        System.out.println("LOGIN: " + request.getUsername() + " - " + request.getPassword());

        return ResponseEntity.ok(result);
    }
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto request) {
        RegisterResponseDto result = userService.register(request);
        return ResponseEntity.ok(result);
    }

    // @PostMapping("/google-login")
    // public ResponseEntity<LoginResponseDto> googleLogin(@RequestBody GoogleLoginRequestDto request) throws Exception {
    //     LoginResponseDto result = this.userService.googleLogin(request);
    //     return ResponseEntity.ok(result);
    // }

    // @PostMapping("/google-login-with-credential")
    // public ResponseEntity<LoginResponseDto> googleLoginWithCredential(@RequestBody GoogleLoginWithCredentialRequestDto request) throws Exception {
    //     LoginResponseDto result = this.userService.googleLoginWithCredential(request);
    //     return ResponseEntity.ok(result);
    // }



}