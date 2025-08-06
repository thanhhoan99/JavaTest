package com.example.movie.dtos;//package com.example.jobs.dtos;

import com.example.movie.entities.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {
    private LoggedInUser loggedInUser;
    private String access_token;
    private String refresh_token;
}