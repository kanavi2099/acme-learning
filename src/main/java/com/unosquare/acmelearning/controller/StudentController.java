package com.unosquare.acmelearning.controller;

import com.unosquare.acmelearning.dto.CourseEnrollment;
import com.unosquare.acmelearning.model.Course;
import com.unosquare.acmelearning.model.User;
import com.unosquare.acmelearning.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class StudentController {
    private final StudentService studentService;

    @Operation(description = "Lists all courses for a student.", security = @SecurityRequirement(name = "basicAuth"))

    @RequestMapping(value="/course", method = RequestMethod.GET)
    public List<Course> getAllCourses(){
        return studentService.getAllCourses();
    }

    @Operation(description = "Enrolls a student in a course.", security = @SecurityRequirement(name = "basicAuth"))

    @RequestMapping(value="/course/enrollment/",method = RequestMethod.POST)
    public String enrollToCourse(@RequestBody CourseEnrollment newCourseToEnroll){
        return studentService.enrollToCourse(newCourseToEnroll.getCourseId(), getUser().getId() );

    }

    @Operation(description = "Drops a student from a course.", security = @SecurityRequirement(name = "basicAuth"))
    @RequestMapping(value="/course/enrollment/{id}",method = RequestMethod.GET)
    public String dropFromCourse(
            @Parameter(description = "Id of the course.")
            @PathVariable(name = "id") Long courseId){
        return studentService.dropFromCourse(courseId, getUser().getId());
    }

    @Operation(description = "Lists all the course a student has enrolled into.", security = @SecurityRequirement(name = "basicAuth"))
    @RequestMapping(value="/course/enrollment",method = RequestMethod.GET)
    public List<Course> listEnrolledCourses(){
        return studentService.getEnrolledCourses(getUser().getId());
    }


    private User getUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
