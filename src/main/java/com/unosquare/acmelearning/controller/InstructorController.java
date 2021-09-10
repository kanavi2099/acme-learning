package com.unosquare.acmelearning.controller;

import com.unosquare.acmelearning.dto.CourseRequest;
import com.unosquare.acmelearning.dto.EnrolledStudents;
import com.unosquare.acmelearning.model.Course;
import com.unosquare.acmelearning.model.User;
import com.unosquare.acmelearning.service.InstructorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/instructor")
public class InstructorController {
    private final InstructorService instructorService;

    @Operation(description = "Create a course for an instructor.", security = @SecurityRequirement(name = "basicAuth"))
    @RequestMapping(value = "/course",method = RequestMethod.POST)

    public String createCourse(@RequestBody CourseRequest newCourse){
        return instructorService.createCourse(newCourse, getUser().getId());
    }

    @Operation(description = "list all courses for an instructor.", security = @SecurityRequirement(name = "basicAuth"))
    @RequestMapping(value="/course",method = RequestMethod.GET)
    public List<Course> listCourses(){
        return instructorService.listCourses(getUser().getId());
    }

    @Operation(description = "Starts a course for an instructor.", security = @SecurityRequirement(name = "basicAuth"))
    @RequestMapping(value="/course/{id}",method = RequestMethod.PUT)
    public String startCourse(@Parameter(description = "Id of the course.")
                                  @PathVariable(name = "id")  Long courseId ){
        return instructorService.startCourse(courseId, getUser().getId());

    }

    @Operation(description = "Cancels a course for an instructor.", security = @SecurityRequirement(name = "basicAuth"))
    @RequestMapping(value="/course/{id}",method = RequestMethod.DELETE)
    public String cancelCourse(@Parameter(description = "Id of the course.")
                                   @PathVariable(name = "id")  Long courseId ){
        return instructorService.cancelCourse(courseId, getUser().getId());
    }

    @Operation(description = "Lists enrolled students in a course for an instructor.", security = @SecurityRequirement(name = "basicAuth"))
    @RequestMapping(value="/course/{id}/enrollment",method = RequestMethod.GET)
    public List<EnrolledStudents> getEnrolledStudents(@Parameter(description = "Id of the course.")
                                                          @PathVariable(name = "id") Long id){
        return instructorService.getEnrolledStudentsByCourse(id,getUser().getId());
    }



    private User getUser(){
        return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
