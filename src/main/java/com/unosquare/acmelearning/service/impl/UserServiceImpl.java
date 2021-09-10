package com.unosquare.acmelearning.service.impl;

import com.unosquare.acmelearning.dto.UserRegistration;
import com.unosquare.acmelearning.model.Instructor;
import com.unosquare.acmelearning.model.Student;
import com.unosquare.acmelearning.model.User;
import com.unosquare.acmelearning.repository.InstructorRepository;
import com.unosquare.acmelearning.repository.StudentRepository;
import com.unosquare.acmelearning.repository.UserRepository;
import com.unosquare.acmelearning.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;
    private final StudentRepository studentRepository;

    @Override
    public String registerUSer(UserRegistration newUser) {
        Boolean checkExists=userRepository.existsByUsername(newUser.getUsername());
        if(checkExists)
            return "Username already in use, please choose another.";

        User userRegistration=new User();
        userRegistration.setUsername(newUser.getUsername());
        userRegistration.setPassword(newUser.getPassword());
        userRegistration.setType(newUser.getType());
        userRepository.saveAndFlush(userRegistration);
        switch (newUser.getType()){
            case INSTRUCTOR:
                Instructor instructor=new Instructor();
                instructor.setName(newUser.getName());
                instructor.setUserId(userRegistration.getId());
                instructor.setCourses(new ArrayList<>());
                instructorRepository.saveAndFlush(instructor);
                break;
            case STUDENT:
                Student student=new Student();
                student.setName(newUser.getName());
                student.setUserId(userRegistration.getId());
                student.setCourses(new ArrayList<>());
                studentRepository.saveAndFlush(student);
                break;
        }

        return "User Registration successful.";
    }
}
