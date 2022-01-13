package com.example.libraryManagement.demoLibrary.controller;

import com.example.libraryManagement.demoLibrary.Request.BookCreate;
import com.example.libraryManagement.demoLibrary.Request.BookUpdate;
import com.example.libraryManagement.demoLibrary.model.Book;
import com.example.libraryManagement.demoLibrary.model.User;
import com.example.libraryManagement.demoLibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/book")//new book
    public void createBook(@RequestBody BookCreate bookCreate){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=(User)authentication.getPrincipal();
        if (user.isStudent()){
            throw new AuthenticationCredentialsNotFoundException("STUDents cannot invoke this api");
        }
        bookService.addBook(bookCreate);
    }

    @GetMapping("/book")//getBook
    public Book getBook(@RequestParam("id")int id){
        return bookService.getBookById(id);

    }

    @PutMapping("/book")//update
    public void update(@RequestParam("id")int bookid,
                       @RequestBody BookUpdate bookUpdate){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user=(User)authentication.getPrincipal();
        if (user.isStudent()){
            throw new AuthenticationCredentialsNotFoundException("STUDents cannot invoke this api");
        }

        bookService.updateBook(bookid,bookUpdate);
    }



}
