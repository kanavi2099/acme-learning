package com.unosquare.acmelearning.controller;

import com.unosquare.acmelearning.dto.CourseRequest;
import com.unosquare.acmelearning.dto.EnrolledStudents;
import com.unosquare.acmelearning.model.Course;
import com.unosquare.acmelearning.model.User;
import com.unosquare.acmelearning.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/instructor")
public class InstructorController {
    private final InstructorService instructorService;

    @RequestMapping("/course")
    @PostMapping
    public String createCourse(@RequestBody CourseRequest newCourse){
        return instructorService.createCourse(newCourse, getUser().getId());
    }

    @GetMapping
    @RequestMapping("/course")
    public List<Course> listCourses(){
        return instructorService.listCourses(getUser().getId());
    }

    @PutMapping
    @RequestMapping("/course/{id}")
    public String startCourse(@PathVariable(name = "id")  Long courseId ){
        return instructorService.startCourse(courseId, getUser().getId());

    }


    @DeleteMapping
    @RequestMapping("/course/{id}")
    public String cancelCourse(@PathVariable(name = "id")  Long courseId ){
        return instructorService.cancelCourse(courseId, getUser().getId());
    }

    @GetMapping
    @RequestMapping("/course/{id}/enrollment")
    public List<EnrolledStudents> getEnrolledStudents(@PathVariable(name = "id") Long id){

    }



    private User getUser(){
        return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
