package com.example.libraryManagement.demoLibrary.controller;

import com.example.libraryManagement.demoLibrary.model.Admin;
import com.example.libraryManagement.demoLibrary.model.User;
import com.example.libraryManagement.demoLibrary.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/admin")
    public Admin getAdmin(){
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.isStudent()){
            throw new AuthenticationCredentialsNotFoundException("not for students");
        }
        int adminID=user.getUserTypeId();
        return adminService.getAdmin(adminID);
    }



}
