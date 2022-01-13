package com.example.libraryManagement.demoLibrary.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List; 

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int age;
    private String country;

    @Column(unique = true)
    private String contact;

    @Column(unique = true)
    private String email;
    
    @Column(unique = true)
    private String rollNo;

    @CreationTimestamp
    private Date registerationOn;

    @OneToMany(mappedBy = "student")
    @JsonIgnoreProperties("student")
    private List<Book>books;

    @OneToMany(mappedBy = "student")
    @JsonIgnoreProperties("student")
    private List<Transaction>transactions;

    private int totalFine;

    @OneToOne
    private User user;

}
