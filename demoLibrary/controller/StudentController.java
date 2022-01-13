package com.example.libraryManagement.demoLibrary.controller;

import com.example.libraryManagement.demoLibrary.Request.StudentCreate;
import com.example.libraryManagement.demoLibrary.model.Student;
import com.example.libraryManagement.demoLibrary.model.User;
import com.example.libraryManagement.demoLibrary.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/student")//student
    public void addStudent(@RequestBody StudentCreate studentCreate){
        studentService.addStudent(studentCreate);
    }

    @GetMapping("/student")
    public Student getStudent(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        if(!user.isStudent()){
            throw new AuthorizationServiceException("Admins can not invoke this API");
        }

        int studentId = user.getUserTypeId(); // 0
        return studentService.getStudent(studentId);
    }

    //by student
    @GetMapping("/get_student")//student
    //public Student getStudent(@RequestParam("id")int id) but now after secur we dnt need student id from param
    //we fetch from login
    public Student getStudent(@RequestParam("id")int id){


        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        User user=(User)authentication.getPrincipal();
        if((user.isStudent() && user.getUserTypeId() == id) || !user.isStudent()){
            return studentService.getStudent(id);
        }

        throw new AuthorizationServiceException("user can not invoke this API");


//        since this api is restricted to students only id dwill be of students's


    }

    //by admin
    @GetMapping("/student/Id/{id}")
    public Student getStudentById(@PathVariable("id")int id){

        //below code is not req its just for sake of understanding if u want to be sure this api is
        //invoked by admin only
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        User user=(User) authentication.getPrincipal();

        if(user.isStudent()){
            throw new AuthenticationCredentialsNotFoundException("STUDENTS CAN NOT INVOKE THIS API");
        }
        //ABOVE CHECK IS NOT REQ

        return studentService.getStudent(id);


    }





    }
