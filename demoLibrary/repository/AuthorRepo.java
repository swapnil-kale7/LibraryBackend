package com.example.libraryManagement.demoLibrary.repository;

import com.example.libraryManagement.demoLibrary.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author,Integer> {

    Author findByEmail(String email);

}
