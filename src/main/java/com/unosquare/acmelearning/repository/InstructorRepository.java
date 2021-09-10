package com.unosquare.acmelearning.repository;

import com.unosquare.acmelearning.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}
