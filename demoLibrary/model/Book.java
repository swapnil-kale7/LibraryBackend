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
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    private int cost;

    private boolean available;

    @CreationTimestamp
    private Date bookAddedOn;

    @JoinColumn
    @ManyToOne
    @JsonIgnoreProperties(value = "books")
    private Author author;//foreign key

    @OneToMany(mappedBy = "book")
    @JsonIgnoreProperties(value = "book")
    private List<Transaction> transactions;

    @JoinColumn
    @ManyToOne
    @JsonIgnoreProperties("books")
    private  Student student;

}
