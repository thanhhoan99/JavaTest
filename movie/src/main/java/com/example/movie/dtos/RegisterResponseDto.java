package com.example.movie.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponseDto {
    private Long id;
    private String username;
    private String message;
}
