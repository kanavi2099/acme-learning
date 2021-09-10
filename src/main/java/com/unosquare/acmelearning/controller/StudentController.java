package com.unosquare.acmelearning.controller;

import com.unosquare.acmelearning.dto.CourseEnrollment;
import com.unosquare.acmelearning.model.Course;
import com.unosquare.acmelearning.model.User;
import com.unosquare.acmelearning.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    @RequestMapping("/course")
    public List<Course> getAllCourses(){
        return studentService.getAllCourses();
    }

    @PostMapping
    @RequestMapping("/course/enrollment/")
    public String enrollToCourse(@RequestBody CourseEnrollment newCourseToEnroll){
        return studentService.enrollToCourse(newCourseToEnroll.getCourseId(), getUser().getId() );

    }

    @DeleteMapping
    @RequestMapping("/course/enrollment/{id}")
    public String dropFromCourse(@PathVariable(name = "id") Long courseId){
        return studentService.dropFromCourse(courseId, getUser().getId());
    }

    @GetMapping
    @RequestMapping("/course/enrollment")
    public List<Course> listEnrolledCourses(){
        return studentService.getEnrolledCourses(getUser().getId());
    }


    private User getUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
