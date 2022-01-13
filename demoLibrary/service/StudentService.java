package com.example.libraryManagement.demoLibrary.service;

import com.example.libraryManagement.demoLibrary.Request.StudentCreate;
import com.example.libraryManagement.demoLibrary.model.Student;
import com.example.libraryManagement.demoLibrary.model.User;
import com.example.libraryManagement.demoLibrary.repository.StudentRepo;
import com.example.libraryManagement.demoLibrary.repository.userRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

//AutoWire is not req above constructor unless u have 2 constructors with parameters bcause spring does not
//know which one to call

@Service
public class StudentService {

   // @Autowired
    StudentRepo studentRepo;
   // @Autowired
    userRepo userRepo;

   // @Value("${app.security.user_role}")
    private String STUDENT_ROLE;
    PasswordEncoder passwordEncoder;

    public StudentService(StudentRepo studentRepo, com.example.libraryManagement.demoLibrary.repository.userRepo userRepo,
                          @Value("${app.security.user_role}")String STUDENT_ROLE,PasswordEncoder passwordEncoder) {
        this.studentRepo = studentRepo;
        this.userRepo = userRepo;
        this.STUDENT_ROLE = STUDENT_ROLE;
        this.passwordEncoder=passwordEncoder;
    }

    @Transactional
    public void addStudent(StudentCreate studentCreate){
        Student student=studentCreate.toStudent();
        student=studentRepo.save(student);

        User user=User.builder().username(studentCreate.getEmail())
                .password(passwordEncoder.encode(studentCreate.getPassword()))
                .UserTypeId(student.getId())
                .authorities(STUDENT_ROLE)
                .isStudent(true)
                .build();

        userRepo.save(user);
    }

    public Student getStudent(int id){
        return studentRepo.findById(id).orElse(null);

    }

    public void addOrUpdateStudent(Student student) {
        studentRepo.save(student);

    }
}
