//package com.example.jobs.entities;
//
//import com.example.jobs.enums.JobType;
//import com.example.jobs.enums.Status;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.Date;
//import java.util.List;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//
//@Entity
//@Table(name = "job_postings")
//public class JobPosting {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Column(name = "employer_id")
//    private Integer employerId;
//
//    private String title;
//    private String description;
//    private String location;
//
//    @Column(name = "salary_range")
//    private String salaryRange;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "job_type")
//    private JobType jobType;
//
//    private String category;
//
//    private Date deadline;
//
//    @Enumerated(EnumType.STRING)
//    private Status status;
//
//    @OneToMany(mappedBy = "jobPosting")
//    private List<Application> applications;
//}
