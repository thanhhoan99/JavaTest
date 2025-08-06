//package com.example.jobs.entities;
//
//import jakarta.persistence.*;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "candidates")
//public class Candidate {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Column(name = "user_id")
//    private Integer userId;
//
//    private String phone;
//    private String address;
//    private String bio;
//    private String experience;
//    private String education;
//    private String skills;
//
//
//    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
//    private List<Application> applications;
//}
