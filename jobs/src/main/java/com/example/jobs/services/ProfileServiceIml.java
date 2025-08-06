package com.example.jobs.services;

import com.example.jobs.entities.UserEntity;
import com.example.jobs.io.ProfileRequest;
import com.example.jobs.io.ProfileResponse;
import com.example.jobs.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class ProfileServiceIml implements ProfileService {

    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileServiceIml(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ProfileResponse createProfile(ProfileRequest request){
        UserEntity newProfile= convertToUser(request);
        if(!userRepository.existsByEmail(request.getEmail())) {
            newProfile = userRepository.save(newProfile);
            return covertToProfileResponse(newProfile);
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
    }

    @Override
    public ProfileResponse getProfile(String email) {
        UserEntity existingUser =userRepository.findByEmail(email)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found" +email));
        return covertToProfileResponse(existingUser);
    }

    private ProfileResponse covertToProfileResponse(UserEntity newProfile){
        return ProfileResponse.builder()
                .name(newProfile.getName())
                .email(newProfile.getEmail())
                .userId(newProfile.getUserId())
                .isAccountVerified(String.valueOf(newProfile.getIsAccountVerified()))
                .build();
    }

    private UserEntity convertToUser(ProfileRequest request){
        return UserEntity.builder()
                .email(request.getEmail())
                .userId(UUID.randomUUID().toString())
                .name(request.getName())
                .password(passwordEncoder.encode( request.getPassword()))
                .isAccountVerified(false)
                .resetOtpExpireAt(0L)
                .verifyOtpExpireAt(0L)
                .verifyOtp(null)
                .resetOtp(null)
                .build();
    }
}
