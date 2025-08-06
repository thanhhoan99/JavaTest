package com.example.movie.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
    private String username;
    private String password;
    private String confirmPassword;
    private List<String> roleNames; // Ví dụ: ["Users"], ["Administrators", "Managers"]
}

