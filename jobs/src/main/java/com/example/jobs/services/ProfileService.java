package com.example.jobs.services;


import com.example.jobs.io.ProfileRequest;
import com.example.jobs.io.ProfileResponse;

public interface ProfileService {
     ProfileResponse createProfile(ProfileRequest request) ;
     ProfileResponse getProfile(String email) ;

}
