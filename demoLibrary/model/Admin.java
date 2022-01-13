package com.example.libraryManagement.demoLibrary.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @Column(unique = true,nullable = false)
    private String EmailId;
    @CreationTimestamp
    private Date createdOn;

    @OneToOne
    private User user;


}
