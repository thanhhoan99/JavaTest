//package com.example.jobs.services;
//
//import com.example.jobs.config.JwtTokenUtil;
//import com.example.jobs.dtos.LoginResponseDto;
//import com.example.jobs.dtos.RegisterRequestDto;
//import com.example.jobs.dtos.RoleDto;
//import com.example.jobs.dtos.UserDto;
//import com.example.jobs.entities.Role;
//import com.example.jobs.entities.User;
//import com.example.jobs.repositories.RoleRepository;
//import com.example.jobs.repositories.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtTokenUtil jwtTokenUtil;
//
//    public LoginResponseDto login(String username, String password) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new RuntimeException("Invalid credentials");
//        }
//
//        return buildLoginResponse(user);
//    }
//
//    public LoginResponseDto register(RegisterRequestDto dto) {
//        if (userRepository.existsByUsername(dto.getUsername())) {
//            throw new RuntimeException("Username already taken");
//        }
//        List<Role> roles = roleRepository.findAllById(dto.getRoleIds());
//        User user = User.builder()
//                .fullName(dto.getFullName())
//                .username(dto.getUsername())
//                .password(passwordEncoder.encode(dto.getPassword()))
//                .status("active")
//                .roles(roles)
//                .build();
//        userRepository.save(user);
//        return buildLoginResponse(user);
//    }
//
//    private LoginResponseDto buildLoginResponse(User user) {
//        List<RoleDto> roleDtos = user.getRoles().stream().map(role ->
//                RoleDto.builder()
//                        .id(role.getId())
//                        .code(role.getCode())
//                        .name(role.getName())
//                        .description(role.getDescription())
//                        .build()
//        ).toList();
//
//        UserDto userDto = UserDto.builder()
//                .id(user.getId())
//                .fullName(user.getFullName())
//                .username(user.getUsername())
//                .status(user.getStatus())
//                .roles(roleDtos)
//                .build();
//
//        return LoginResponseDto.builder()
//                .loggedInUser(userDto)
//                .access_token(jwtTokenUtil.generateAccessToken(user))
//                .refresh_token(jwtTokenUtil.generateRefreshToken(user))
//                .build();
//    }
//}
