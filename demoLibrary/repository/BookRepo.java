package com.example.libraryManagement.demoLibrary.repository;

import com.example.libraryManagement.demoLibrary.model.Book;
import com.example.libraryManagement.demoLibrary.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface BookRepo extends JpaRepository<Book,Integer> {


    @Modifying
    @Transactional
    @Query("update Book b set b.cost=:cost,b.genre=:genre,b.name=:name where b.id =:id")
    void updateBook(int cost, Genre genre,String name,int id);
}

