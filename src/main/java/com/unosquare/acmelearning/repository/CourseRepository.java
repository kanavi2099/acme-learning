package com.unosquare.acmelearning.repository;

import com.unosquare.acmelearning.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Boolean existsByName(String name);
}
