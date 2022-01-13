package com.example.libraryManagement.demoLibrary.service;

import com.example.libraryManagement.demoLibrary.model.Author;
import com.example.libraryManagement.demoLibrary.repository.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    AuthorRepo authorRepo;


    public Author getAuthorByEmail(String email){
        return authorRepo.findByEmail(email);
    }

    public Author addAuthor(Author author){
        return authorRepo.save(author);
    }

}
