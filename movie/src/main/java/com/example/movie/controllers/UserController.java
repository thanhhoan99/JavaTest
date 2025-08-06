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


@PreAuthorize("hasRole('Administrators')")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final RoleJpaRepository roleJpaRepository;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @PutMapping("/users/{id}/roles")
    public ResponseEntity<String> updateUserRoles(
            @PathVariable("id") Long userId,
            @RequestBody UpdateUserRolesRequest request,
            @RequestHeader("Authorization") String authHeader
    ) {
        // Extract username from token
        String token = authHeader.replace("Bearer ", "");
        String username = jwtService.extractUsername(token);

        // Gọi service để kiểm tra quyền và cập nhật
        userService.updateUserRoles(username, userId, request.getRoleNames());

        return ResponseEntity.ok("User roles updated successfully.");
    }
    @GetMapping("/roles")
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return ResponseEntity.ok(userService.getAllRoles());
    }

}
