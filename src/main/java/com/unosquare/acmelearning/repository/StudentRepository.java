package com.unosquare.acmelearning.repository;

import com.unosquare.acmelearning.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUserId(Long userId);
    List<Student> findByCoursesId(Long id);
}
