package com.unosquare.acmelearning.service.impl;

import com.unosquare.acmelearning.model.Course;
import com.unosquare.acmelearning.model.Student;
import com.unosquare.acmelearning.repository.CourseRepository;
import com.unosquare.acmelearning.repository.StudentRepository;
import com.unosquare.acmelearning.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public String enrollToCourse(Long courseId, Long userId) {
        Boolean checkCourseExists=courseRepository.existsById(courseId);
        if(!checkCourseExists){
            return "Course does not exists, please verify your information.";
        }
        Student student=studentRepository.findByUserId(userId);
        Optional<Course> verifyEnroll = student.getCourses().stream()
                .filter(course -> course.getId().equals(courseId)).findFirst();

        if(verifyEnroll.isPresent()){
            return "You are already enrolled to this course.";
        }
        Optional<Course> courseToEnroll=courseRepository.findById(courseId);
        if(courseToEnroll.get().getInCourse()){
            return "This course already started, you can't enroll to started courses.";
        }
        student.getCourses().add(courseToEnroll.get());
        studentRepository.saveAndFlush(student);
        return "Enrolled to course successfully.";
    }

    @Override
    public String dropFromCourse(Long courseId, Long userId) {
        Boolean checkCourseExists=courseRepository.existsById(courseId);
        if(!checkCourseExists){
            return "Course does not exists, please verify your information.";
        }
        Student student=studentRepository.findByUserId(userId);
        Optional<Course> verifyEnroll = student.getCourses().stream()
                .filter(course -> course.getId().equals(courseId)).findFirst();

        if(!verifyEnroll.isPresent()){
            return "You are not enrolled to this course.";
        }
        student.getCourses().remove(verifyEnroll.get());
        studentRepository.saveAndFlush(student);
        return "Dropped from course successfully";
    }

    @Override
    public List<Course> getEnrolledCourses(Long userId) {
        return studentRepository.findByUserId(userId).getCourses();
    }
}
