//package com.example.jobs.controllers;
//
//import com.example.jobs.entities.Role;
//import com.example.jobs.repositories.RoleRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/roles")
//@RequiredArgsConstructor
//public class RoleController {
//    private final RoleRepository roleRepository;
//
//    @GetMapping
//    public List<Role> getAllRoles() {
//        return roleRepository.findAll();
//    }
//}