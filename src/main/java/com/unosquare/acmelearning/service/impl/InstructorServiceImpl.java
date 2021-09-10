package com.unosquare.acmelearning.service.impl;

import com.unosquare.acmelearning.dto.CourseRequest;
import com.unosquare.acmelearning.dto.EnrolledStudents;
import com.unosquare.acmelearning.model.Course;
import com.unosquare.acmelearning.model.Instructor;
import com.unosquare.acmelearning.model.Student;
import com.unosquare.acmelearning.repository.CourseRepository;
import com.unosquare.acmelearning.repository.InstructorRepository;
import com.unosquare.acmelearning.repository.StudentRepository;
import com.unosquare.acmelearning.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;


    @Override
    public String createCourse(CourseRequest newCourse, Long userId) {
        Boolean checkCourseExists=courseRepository.existsByName(newCourse.getName());
        if(checkCourseExists){
            return "Course already exists, please choose another name.";
        }
        Instructor instructor=instructorRepository.findByUserId(userId);

        Course courseRegistration=new Course();
        courseRegistration.setInCourse(false);
        courseRegistration.setName(newCourse.getName());
        instructor.getCourses().add(courseRegistration);
        instructorRepository.saveAndFlush(instructor);

        return "Course added successfully.";
    }

    @Override
    public List<Course> listCourses(Long userId) {
        Instructor instructor=instructorRepository.findByUserId(userId);
        return instructor.getCourses();
    }

    @Override
    public String startCourse(Long courseId, Long userId) {
        Boolean courseExistsCheck=courseRepository.existsById(courseId);
        if(!courseExistsCheck){
            return "Course does not exist, please verify your information";
        }
        Instructor instructor=instructorRepository.findByUserId(userId);
        Optional<Course> courseToStart= instructor.getCourses().stream()
                .filter(course -> course.getId().equals(courseId))
                .findFirst();
        if(!courseToStart.isPresent()){
            return "This course does not belong to the user.";
        }
        if(courseToStart.get().getInCourse()){
            return "Course already started.";
        }
        courseToStart.get().setInCourse(true);
        instructorRepository.saveAndFlush(instructor);
        return "Course started successfully.";
    }

    @Override
    public String cancelCourse(Long courseId, Long userId) {
        Boolean courseExistsCheck=courseRepository.existsById(courseId);
        if(!courseExistsCheck){
            return "Course does not exist, please verify your information";
        }
        Instructor instructor=instructorRepository.findByUserId(userId);
        Optional<Course> courseToCancel= instructor.getCourses().stream()
                .filter(course -> course.getId().equals(courseId))
                .findFirst();
        if(!courseToCancel.isPresent()){
            return "This course does not belong to the user.";
        }
        if(courseToCancel.get().getInCourse()){
            return "Course already started, you can't cancel started courses.";
        }
        List<Student> studentsEnrolled=studentRepository.findByCoursesId(courseId);
        List<Student> studentsEnrolledForCourse=studentRepository.findAllById(
                studentsEnrolled.stream().map(student -> student.getId())
                        .collect(Collectors.toList())

        );
        studentsEnrolledForCourse.forEach( student ->  student.getCourses().removeIf(
                course -> course.getId().equals(courseId) ));
        studentRepository.saveAllAndFlush(studentsEnrolledForCourse);
        instructor.getCourses().remove(courseToCancel.get());
        instructorRepository.saveAndFlush(instructor);
        return "Course cancel successful.";
    }

    @Override
    public List<EnrolledStudents> getEnrolledStudentsByCourse(Long courseId, Long userId) {
        Instructor instructor=instructorRepository.findByUserId(userId);
        Optional<Course> courseCheck= instructor.getCourses().stream()
                .filter(course -> course.getId().equals(courseId)).findFirst();
        if(!courseCheck.isPresent()){
            return new ArrayList<>();
        }

        return studentRepository.findByCoursesId(courseId).stream()
                .map(student -> new EnrolledStudents(student))
                .collect(Collectors.toList());
    }
}
