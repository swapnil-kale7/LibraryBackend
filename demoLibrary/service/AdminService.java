package com.example.libraryManagement.demoLibrary.service;

import com.example.libraryManagement.demoLibrary.Request.AdminCreateReq;
import com.example.libraryManagement.demoLibrary.model.Admin;
import com.example.libraryManagement.demoLibrary.model.User;
import com.example.libraryManagement.demoLibrary.repository.AdminRepo;
import com.example.libraryManagement.demoLibrary.repository.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AdminService {
    @Autowired
    AdminRepo adminRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${app.security.admin_role}")
    String admin_role;

    @Autowired
    userRepo userRepo;

    public Admin getAdmin(int adminId){
        return adminRepo.findById(adminId).orElse(null);
    }

    @Transactional
    public void saveAdmin(AdminCreateReq adminCreateReq){

        Admin admin=Admin.builder()
                .EmailId(adminCreateReq.getEmail())
                .name(adminCreateReq.getName())
                .build();
        admin=adminRepo.save(admin);

        User user=User.builder().username(admin.getEmailId())
                .password(passwordEncoder.encode(adminCreateReq.getPassword()))
                .UserTypeId(admin.getId())
                .authorities(admin_role)
                .build();

        userRepo.save(user);

    }

}
