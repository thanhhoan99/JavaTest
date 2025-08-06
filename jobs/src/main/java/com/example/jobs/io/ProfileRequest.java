package com.example.jobs.io;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileRequest {
    @NotBlank(message = "Name should be not empty")
    private String name;

    @Email
    @NotNull(message = "email should be not empty")
    private String email;

    @Size(min = 6,message = "password should be not empty")
    private String password;
}
