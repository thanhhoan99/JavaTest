package com.example.movie.services;
import com.example.movie.dtos.*;
import com.example.movie.entities.Role;
import com.example.movie.repositories.RoleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.movie.entities.User;
import com.example.movie.exceptions.HttpException;
import com.example.movie.repositories.UserJpaRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final JwtService jwtService;
    private final UserJpaRepository userJpaRepository;

    private final RoleJpaRepository roleJpaRepository;
    private final PasswordEncoder passwordEncoder;


    public LoginResponseDto login(LoginRequestDto request) throws Exception {
        User user = this.userJpaRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new HttpException("Invalid username or password", HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new HttpException("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }

        // Generate a new access token (with full data + roles)
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

//        return LoginResponseDto.builder()
//                .id(user.getId())
//                .username(user.getUsername())
//                .accessToken(accessToken)
//                .build();
        List<Role> roleDtos = user.getRoles().stream()
                .map(role -> Role.builder().id(role.getId()).name(role.getName()).build())
                .toList();

        LoggedInUser loggedInUser = LoggedInUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .isActive(user.getIsActive())     // thêm nếu có cột status
                .roles(roleDtos)
                .build();

        return LoginResponseDto.builder()
                .loggedInUser(loggedInUser)
                .access_token(accessToken)
                .refresh_token(refreshToken)
                .build();
//        return LoginResponseDto.builder()
//                .id(user.getId())
//                .username(user.getUsername())
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .roles(roleDtos)
//                .build();

    }



    public RegisterResponseDto register(RegisterRequestDto request) {
        if (userJpaRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new HttpException("Username already exists", HttpStatus.BAD_REQUEST);
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new HttpException("Passwords do not match", HttpStatus.BAD_REQUEST);
        }
//
        // Tìm Role mặc định (ví dụ: "Users")
        Role defaultRole = roleJpaRepository.findByName("Users")
                .orElseThrow(() -> new HttpException("Default role not found", HttpStatus.INTERNAL_SERVER_ERROR));
        // Load danh sách Role từ DB theo tên
        List<Role> roles = request.getRoleNames().stream()
                .map(roleName -> roleJpaRepository.findByName(roleName)
                        .orElseThrow(() -> new HttpException("Role not found: " + roleName, HttpStatus.BAD_REQUEST)))
                .toList();
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRoles(List.of(defaultRole)); // Gán role mac dinh user
//        newUser.setRoles(roles); //tu chon role

        User savedUser = userJpaRepository.save(newUser);

        return RegisterResponseDto.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .message("Register successfully")
                .build();
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userJpaRepository.findAll();

        return users.stream().map(user -> UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .isActive(user.getIsActive())
                .roles(user.getRoles().stream().map(Role::getName).toList())
                .build()
        ).toList();
    }

    public void updateUserRoles(String adminUsername, Long targetUserId, List<String> roleNames) {
        // Lấy thông tin user thực hiện thao tác
        User adminUser = userJpaRepository.findByUsername(adminUsername)
                .orElseThrow(() -> new HttpException("Admin not found", HttpStatus.UNAUTHORIZED));

        // Kiểm tra quyền Administrators
        boolean isAdmin = adminUser.getRoles().stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase("Administrators"));

        if (!isAdmin) {
            throw new HttpException("Access denied. Only administrators can update user roles.", HttpStatus.FORBIDDEN);
        }

        // Lấy user cần cập nhật
        User targetUser = userJpaRepository.findById(targetUserId)
                .orElseThrow(() -> new HttpException("Target user not found", HttpStatus.NOT_FOUND));

        // Tìm các role mới
        List<Role> newRoles = roleNames.stream()
                .map(roleName -> roleJpaRepository.findByName(roleName)
                        .orElseThrow(() -> new HttpException("Role not found: " + roleName, HttpStatus.BAD_REQUEST)))
                .toList();

//        targetUser.setRoles(newRoles);
        targetUser.setRoles(new java.util.ArrayList<>(newRoles));

        userJpaRepository.save(targetUser);
    }

    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleJpaRepository.findAll();
        return  roles.stream()
                .map(role -> new RoleDto(role.getId(), role.getName()))
                .collect(Collectors.toList());

    }

//    public LoginResponseDto googleLogin(GoogleLoginRequestDto request) {
//        // Find the user by email
//        Optional<User> user = this.userJpaRepository.findByEmail(request.getEmail());
//        // Create new user if not found
//        User newUser = new User();
//        String accessToken;
//        if (user.isEmpty()) {
//
//            newUser.setUsername(request.getEmail());
//            newUser.setPassword(""); // Password not used for Google login
//
//            Role role = new Role();
//            role.setId(3L);
//            newUser.setRoles(List.of(role)); // Assuming role ID 3 for Google users
//            // Save the new user
//            this.userJpaRepository.save(newUser);
//        } else {
//            newUser = user.get();
//        }
//
//        // Generate a new access token (with full data + roles)
//        accessToken = jwtService.generateAccessToken(newUser);
//
//        return LoginResponseDto.builder()
//                .id(newUser.getId())
//                .username(newUser.getUsername())
//                .accessToken(accessToken)
//                .build();
//    }
//
//    public LoginResponseDto googleLoginWithCredential(GoogleLoginWithCredentialRequestDto request) {
//        // Call google API to verify the token
//        String url = "https://oauth2.googleapis.com/tokeninfo?id_token=" + request.getCredential();
//        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
//        Map<String, Object> payload = response.getBody();
//
//        if (response.getStatusCode() != HttpStatus.OK) {
//            throw new HttpException("Invalid Google token", HttpStatus.UNAUTHORIZED);
//        }
//
//        // Parse the response to get the email
//        String email;
//        if (payload != null && payload.containsKey("email")) {
//            email = payload.get("email").toString();
//        } else {
//            throw new HttpException("Email not found in token", HttpStatus.UNAUTHORIZED);
//        }
//
//
//        // Nên kiểm tra aud = Client ID của ứng dụng để đảm bảo token hợp lệ ( // Có thể code sau ...)
////        String aud = payload.get("aud").toString();
////        if (!aud.equals("YOUR_CLIENT_ID")) {
////            throw new HttpException("Invalid Google token audience", HttpStatus.UNAUTHORIZED);
////        }
//
//        // Kiểm tra thêm: exp so với thời gian hiện tại để đảm bảo token chưa hết hạn.
//        String iss = payload.get("iss").toString();
//        if (!iss.equals("https://accounts.google.com") && !iss.equals("accounts.google.com")) {
//            throw new HttpException("Invalid Google token issuer", HttpStatus.UNAUTHORIZED);
//        }
//
//        // Kiểm tra thêm: exp so với thời gian hiện tại để đảm bảo token chưa hết hạn.
//        long exp = Long.parseLong(payload.get("exp").toString());
//        if (exp < System.currentTimeMillis() / 1000) {
//            throw new HttpException("Google token has expired", HttpStatus.UNAUTHORIZED);
//        }
//
//        // Find the user by email
//        // If not found, create a new user with the email.
//        Optional<User> user = this.userJpaRepository.findByEmail(email);
//
//        // Create new user if not found
//        User newUser = user.orElseGet(() -> {
//            User u = new User();
//            u.setUsername(email);
//            u.setPassword("");
//            Role role = new Role();
//            role.setId(3L);
//            u.setRoles(List.of(role));
//            return userJpaRepository.save(u);
//        });
//
//
//        // Generate a new access token (with full data + roles)
//        String accessToken = jwtService.generateAccessToken(newUser);
//
//        return LoginResponseDto.builder()
//                .id(newUser.getId())
//                .username(newUser.getUsername())
//                .accessToken(accessToken)
//                .build();
//    }

}