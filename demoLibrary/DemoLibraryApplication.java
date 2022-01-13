package com.example.libraryManagement.demoLibrary;

import com.example.libraryManagement.demoLibrary.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//public class DemoLibraryApplication implements CommandLineRunner {
public class DemoLibraryApplication{
	@Autowired
	AdminService adminService;
	public static void main(String[] args) {
		SpringApplication.run(DemoLibraryApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		AdminCreateReq admin= AdminCreateReq.builder().name("Admin2").email("Admin@gmail.vom")
//				.password("abc")
//				.build();
//
//		adminService.saveAdmin(admin);
	}

