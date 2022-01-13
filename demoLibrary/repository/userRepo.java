package com.example.libraryManagement.demoLibrary.repository;

import com.example.libraryManagement.demoLibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepo extends JpaRepository<User,Integer> {

    User findByUsername(String user);
}
