package com.example.demo.user.controller;

import com.example.demo.user.dto.ReportRequestDto;
import com.example.demo.user.entity.User;
import com.example.demo.user.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/report-users")
    public ResponseEntity<List<User>> reportUsers(@RequestBody ReportRequestDto reportRequestDto) {

        return ResponseEntity.ok(adminService.reportUsers(reportRequestDto.getUserIds()));
    }
}
