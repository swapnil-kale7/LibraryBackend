package com.example.libraryManagement.demoLibrary.repository;

import com.example.libraryManagement.demoLibrary.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student,Integer> {



}
