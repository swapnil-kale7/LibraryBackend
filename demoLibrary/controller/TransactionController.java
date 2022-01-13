package com.example.libraryManagement.demoLibrary.controller;

import com.example.libraryManagement.demoLibrary.model.User;
import com.example.libraryManagement.demoLibrary.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction/issue_book")
    public String issueBook(@RequestParam("book_id")int BookId){//@RequestParam("student_id")int studentId

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=(User)authentication.getPrincipal();
        if (!user.isStudent()){
            throw new AuthenticationCredentialsNotFoundException("ADmins cannot invoke this api");
        }

        int studentId=user.getUserTypeId();
        return transactionService.issueBook(BookId, studentId);
    }

    //student only
    @GetMapping("/transaction/fine")
    public Integer fine(@RequestParam("Book_id")int bookid){


        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=(User)authentication.getPrincipal();
        if (!user.isStudent()){
            throw new AuthenticationCredentialsNotFoundException("ADmins cannot invoke this api");
        }

        int studentid=user.getUserTypeId();
        return transactionService.getFine(bookid,studentid);
    }

    //admin only
    @GetMapping("/transaction/fine/student_id/{id}")
    public Integer fine(@RequestParam("Book_id")int bookid,
                        @PathVariable("id")int studentid){


        return transactionService.getFine(bookid,studentid);
    }

    @PostMapping("/transaction/return_book")
    public String returnBook(@RequestParam("Book_id")int bookid,
                             @RequestParam("fine")int fine){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=(User)authentication.getPrincipal();
        if (!user.isStudent()){
            throw new AuthenticationCredentialsNotFoundException("ADmins cannot invoke this api");
        }

        int studentId=user.getUserTypeId();
        return transactionService.returnBook(bookid, studentId, fine);
    }



}
