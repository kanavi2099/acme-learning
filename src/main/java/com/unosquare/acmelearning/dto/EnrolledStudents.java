package com.unosquare.acmelearning.dto;

import com.unosquare.acmelearning.model.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrolledStudents {
    private Long id;
    private String name;

    public EnrolledStudents(Student student){
        this.id=student.getId();
        this.name=student.getName();
    }
}
