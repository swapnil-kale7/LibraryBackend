package com.example.libraryManagement.demoLibrary.service;

import com.example.libraryManagement.demoLibrary.model.*;
import com.example.libraryManagement.demoLibrary.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    BookService bookService;

    @Autowired
    StudentService studentService;

    @Autowired
    TransactionRepo transactionRepo;

    @Value("${books.max-allotment}")
    int maxAllotment;

    @Value("${books.fine-per-day}")
    int fine;

    @Value("${book.max-day}")
    int daysAllotd;






    public String issueBook(int bookId,int studentID){
//        1.get book by id + get5 student by id
//        2.check for availablity
//        3.check student's threshold(max 3 books,4th book cannot be issused)
//        4.create transaction
//        5.make book unavailable+decrease students limit of book issue

        Book book =bookService.getBookById(bookId);

        if (book==null || !book.isAvailable()){
            return "Failed transaction for Book";
        }
        Student student=studentService.getStudent(studentID);

        if (student==null || student.getBooks().size()>=maxAllotment){
            return "FAILED DUE TO STUDENT ERROR";
        }

        Transaction transaction=Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .transactionType(TransactionType.ISSUE)
                .student(student)
                .book(book)
               .transactionStatus(TransactionStatus.PENDING).build();

        try {
            transaction=transactionRepo.save(transaction);

            book.setStudent(student);
            book.setAvailable(false);

            bookService.addOrUpdateBook(book);

            transaction.setTransactionStatus(TransactionStatus.SUCCESS);

            transaction=transactionRepo.save(transaction);
        }catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepo.save(transaction);
        }
        return transaction.getTransactionId();


    }

    public Integer getFine(int bookId,int studentId){

        /*
        1.Get issue DATE from transaction table for studentId and book id and transaction type shoud
        be ISSUE sort by transaction date desc limit 1
        and status should be SUccESS
        2.calculate no of days extra-fine per day
         */
        List<Transaction>transactions=transactionRepo.getTransaction(studentId,bookId,TransactionStatus.SUCCESS,TransactionType.ISSUE);
        Transaction transaction=transactions.get(transactions.size()-1);

        //logic to find no of days passed

        Date transactionDate=transaction.getTransactionTime();
        long timeInMillis=System.currentTimeMillis()-transactionDate.getTime();
        Long days= TimeUnit.MILLISECONDS.toDays(timeInMillis);

        if(days>daysAllotd){
            return (days.intValue()-daysAllotd)*fine;


        }

        return 0;

    }

    public String returnBook(int bookId,int studentId,int fine){

        /*
        1.if fine is greater than 0 then increment the total fine in student recored
        2.make book as available
        3.transaction
         */
//        precondition Book and book should be available and book should be assigned to that student opnly



        Book book =bookService.getBookById(bookId);

        if (book==null || book.isAvailable()){
            return "Requested book could not be found or its already available";

        }
        Student student=studentService.getStudent(studentId);

        if (student==null || book.getStudent().getId()!=studentId){
            return "Book not found or not assigned to given student";
        }
        Transaction transaction=Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .transactionType(TransactionType.RETURN)
                .student(student)
                .book(book)
                .fine(fine)
                .transactionStatus(TransactionStatus.PENDING).build();



        try {
            transaction = transactionRepo.save(transaction);

            book.setAvailable(true);
            book.setStudent(null);

            bookService.addOrUpdateBook(book);

            if (fine > 0) {
                student.setTotalFine(student.getTotalFine() + fine);
                studentService.addOrUpdateStudent(student);
            }
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            transactionRepo.save(transaction);
        }catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepo.save(transaction);
        }
        return transaction.getTransactionId();
    }


}
