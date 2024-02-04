package com.gl.studentmanagementsystem.service;

import com.gl.studentmanagementsystem.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> getAllStudents();

    Optional<Student> getStudentById(Long id);

    void saveStudent(Student student);

    void deleteStudent(Long id);

    List<Student> searchStudents(String query);

    void updateStudent(Long id, Student updatedStudent);
}
