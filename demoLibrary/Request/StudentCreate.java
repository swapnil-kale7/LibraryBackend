package com.example.libraryManagement.demoLibrary.Request;

import com.example.libraryManagement.demoLibrary.model.Student;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentCreate {


    private String name;
    private int age;
    private String country;
    private String contact;
    private String email;
    private String rollNo;

    private String password;
//    private String Username;//some unique ident so we can conisder email instead of username variable for username


    public Student toStudent(){

        return Student.builder()
                .name(name)
                .age(age)
                .country(country)
                .email(email)
                .rollNo(rollNo)
                .contact(contact)
                .build();
    }



}
