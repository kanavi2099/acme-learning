package com.unosquare.acmelearning.service;

import com.unosquare.acmelearning.model.Course;

import java.util.List;

public interface StudentService {
    List<Course> getAllCourses();
    String enrollToCourse(Long courseId, Long userId);
    String dropFromCourse(Long courseId, Long userId);
    List<Course> getEnrolledCourses(Long userId);
}
