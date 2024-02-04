package com.gl.studentmanagementsystem.serviceimpl;

import com.gl.studentmanagementsystem.exception.StudentNotFoundException;
import com.gl.studentmanagementsystem.model.Student;
import com.gl.studentmanagementsystem.repository.StudentRepository;
import com.gl.studentmanagementsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> searchStudents(String query) {
        // Implement the search logic based on your requirements
        // For example, you can use the studentRepository to perform a search query
        // This is a placeholder and needs to be adapted based on your data structure
        return studentRepository.findByFirstNameContainingOrLastNameContainingOrCourseContainingOrCountryContaining(query, query, query, query);
    }

    @Override
    public void updateStudent(Long id, Student updatedStudent) {
        // Retrieve the existing student from the database
        Optional<Student> existingStudentOptional = studentRepository.findById(id);

        if (existingStudentOptional.isPresent()) {
            Student existingStudent = existingStudentOptional.get();

            // Update the fields of the existing student with the new values
            existingStudent.setFirstName(updatedStudent.getFirstName());
            existingStudent.setLastName(updatedStudent.getLastName());
            existingStudent.setCourse(updatedStudent.getCourse());
            existingStudent.setCountry(updatedStudent.getCountry());

            // Save the updated student
            studentRepository.save(existingStudent);
        } else {
            // Handle the case where the student with the given id is not found
            throw new StudentNotFoundException(STR."Student not found with id: \{id}");
        }
    }
}
