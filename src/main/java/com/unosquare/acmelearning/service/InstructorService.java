package com.unosquare.acmelearning.service;

import com.unosquare.acmelearning.dto.CourseRequest;
import com.unosquare.acmelearning.dto.EnrolledStudents;
import com.unosquare.acmelearning.model.Course;

import java.util.List;

public interface InstructorService {
    String createCourse(CourseRequest newCourse, Long userId);
    List<Course> listCourses(Long userId);
    String startCourse(Long courseId, Long userId);
    String cancelCourse(Long courseId, Long userId);
    List<EnrolledStudents> getEnrolledStudentsByCourse(Long courseId, Long userId);
}
