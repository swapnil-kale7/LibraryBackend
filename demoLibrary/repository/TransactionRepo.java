package com.example.libraryManagement.demoLibrary.repository;

import com.example.libraryManagement.demoLibrary.model.Transaction;
import com.example.libraryManagement.demoLibrary.model.TransactionStatus;
import com.example.libraryManagement.demoLibrary.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction,Integer> {

    @Query("select t from Transaction t where t.student.id = :studentid and t.book.id = :bookId and " +
            "t.transactionStatus = :transactionStatus and t.transactionType = :transactionType order by t.transactionTime")
    List<Transaction>getTransaction(int studentid, int bookId, TransactionStatus transactionStatus,
                                    TransactionType transactionType);







}
