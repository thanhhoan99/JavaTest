package com.example.movie.dtos;

import lombok.Data;

import java.util.List;

// UpdateUserRolesRequest.java
@Data
public class UpdateUserRolesRequest {
    private List<String> roleNames;
}
