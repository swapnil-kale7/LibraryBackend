package com.example.libraryManagement.demoLibrary.service;


import com.example.libraryManagement.demoLibrary.Request.BookCreate;
import com.example.libraryManagement.demoLibrary.Request.BookUpdate;
import com.example.libraryManagement.demoLibrary.model.Author;
import com.example.libraryManagement.demoLibrary.model.Book;

import com.example.libraryManagement.demoLibrary.repository.BookRepo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BookService {

    @Autowired
    AuthorService authorService;

    @Autowired
    BookRepo bookRepo;



    @Transactional
    public void addBook(BookCreate bookCreate){

        //Author object->save it in the db
        //Book object+Link authorId
        //save book object


       Author author= authorService.getAuthorByEmail(bookCreate.getAuthorEmail());

       if (author==null){

           author=Author.builder()
                   .name(bookCreate.getAuthorName())
                   .age(bookCreate.getAuthorAge())
                   .country(bookCreate.getAuthorCountry())
                   .email(bookCreate.getAuthorEmail())
                   .build();

           author=authorService.addAuthor(author);
       }
        Book book=Book.builder()
                .cost(bookCreate.getCost())
                .name(bookCreate.getName())
                .genre(bookCreate.getGenre())
                .author(author)
                .available(true)
                .build();

       bookRepo.save(book);



    }

    public Book getBookById(int id){
       return bookRepo.findById(id).orElse(null);//find by primary key
    }

    public void addOrUpdateBook(Book book){
        bookRepo.save(book);
    }

    public void updateBook(int bookid,BookUpdate bookUpdate){
//        cost - present , baaki don not present
//        now
//        update book b set b.cost=:cost b.genre=:genre b.name=:name where b.id =id
//        this is bad query bcoz genre and name will become NULL
//        so we will only pass cost andd use last used values

        bookRepo.updateBook(bookUpdate.getCost(),bookUpdate.getGenre(),bookUpdate.getName(),bookid);



    }

}
