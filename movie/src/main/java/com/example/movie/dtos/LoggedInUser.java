package com.example.movie.dtos;

import com.example.movie.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoggedInUser {
    private Long id;
    private String username;
    private Boolean isActive;
    private List<Role> roles;
}