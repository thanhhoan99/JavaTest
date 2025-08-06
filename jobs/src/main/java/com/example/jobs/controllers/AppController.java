//package com.example.jobs.controllers;
//
//import com.example.jobs.dtos.AppResponseDto;
//import com.example.jobs.dtos.CreateApplicationRequestDto;
//import com.example.jobs.dtos.PaginatedAppResponseDto;
//import com.example.jobs.dtos.UpdateAppRequestDto;
//import com.example.jobs.services.AppService;
//import jakarta.validation.Valid;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("api/apps")
//public class AppController {
//    private final AppService appService;
//
//    public AppController(AppService appService) {
//        this.appService = appService;
//    }
////    @GetMapping("")
////    public List<AppResponseDto> getAllApps() {
////        return appService.getAllApps();
////    }
//    @GetMapping("")
//    public PaginatedAppResponseDto getAllAppsByPage(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "5") int size) {
//        System.out.println("page: " + page);
//        System.out.println("size: " + size);
//        return this.appService.getAllAppsByPage(page, size);
//    }
//
//    @PostMapping()
//    public AppResponseDto createApplication(@RequestBody @Valid CreateApplicationRequestDto createApplicationRequestDto) {
//        return this.appService.createApplication(createApplicationRequestDto);
//    }
//
//    @GetMapping("/{id}")
//    public AppResponseDto getAppById(@PathVariable("id") Integer id) {
//        return this.appService.getAppById(id);
//    }
//    @PatchMapping("/{id}")
//    public AppResponseDto updateStudent(@PathVariable("id") Integer id, @RequestBody UpdateAppRequestDto application) {
//        return this.appService.updateApplication(id, application);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteApplication(@PathVariable("id") Integer id) {
//        this.appService.deleteApplication(id);
//    }
//
//}
