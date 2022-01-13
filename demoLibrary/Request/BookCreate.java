package com.example.libraryManagement.demoLibrary.Request;

import com.example.libraryManagement.demoLibrary.model.Genre;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCreate {

    //these are books parameters

    private String name;
    private Genre genre;
//    private int age;
    private int cost;

    //these are needed for author creation if needed
    private String authorName;
    private String authorCountry;
    private int authorAge;
    private String authorEmail;



}
