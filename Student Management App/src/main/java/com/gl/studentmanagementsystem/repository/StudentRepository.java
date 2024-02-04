package com.gl.studentmanagementsystem.repository;

import com.gl.studentmanagementsystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByFirstNameContainingOrLastNameContainingOrCourseContainingOrCountryContaining(
            String firstName, String lastName, String course, String country);
}
