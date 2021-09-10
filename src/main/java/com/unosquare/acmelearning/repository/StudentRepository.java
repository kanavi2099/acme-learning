package com.unosquare.acmelearning.repository;

import com.unosquare.acmelearning.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
