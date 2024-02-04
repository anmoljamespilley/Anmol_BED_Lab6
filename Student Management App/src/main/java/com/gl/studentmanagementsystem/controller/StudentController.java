package com.gl.studentmanagementsystem.controller;

import com.gl.studentmanagementsystem.model.Student;
import com.gl.studentmanagementsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping({"/", "/list"})
    public String listStudents(@RequestParam(name = "query", defaultValue = "") String query, Model model) {
        try {
            List<Student> students;
            if (query != null && !query.isEmpty()) {
                students = studentService.searchStudents(query);
            } else {
                students = studentService.getAllStudents();
            }
            model.addAttribute("students", students);
            model.addAttribute("searchQuery", query);
            return "student/list";
        } catch (Exception e) {
            model.addAttribute("error", "Error fetching Students: " + e.getLocalizedMessage());
            return "student/error";
        }
    }

    @GetMapping("/create")
    public String createStudentForm(Model model) {
        try {
            model.addAttribute("formHeading", "Add a New Student");
            model.addAttribute("student", new Student());
            return "student/create";
        } catch (Exception e) {
            model.addAttribute("error", "Error adding Student: " + e.getLocalizedMessage());
            return "student/error";
        }
    }

    @PostMapping("/create")
    public String createStudent(@ModelAttribute("student") @Valid Student student, BindingResult result, Model model) {
        try {
            if (result.hasErrors()) {
                return "student/create";
            }
            studentService.saveStudent(student);
            return "redirect:/students/list";
        } catch (Exception e) {
            model.addAttribute("error", "Error creating Student: " + e.getLocalizedMessage());
            return "student/error";
        }
    }

    @GetMapping("/{id}/edit")
    public String editStudentForm(@PathVariable Long id, Model model) {
        try {
            Optional<Student> student = studentService.getStudentById(id);
            student.ifPresent(value -> model.addAttribute("student", value));
            model.addAttribute("formHeading", "Edit Student");
            return "student/edit";
        } catch (Exception e) {
            model.addAttribute("error", "Error loading Student details for editing: " + e.getLocalizedMessage());
            return "student/error";
        }
    }

    @PostMapping("/edit")
    public String updateStudent(@ModelAttribute("student") @Valid Student student, BindingResult result, Model model) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("formHeading", "Edit Student");
                return "student/edit";
            }
            studentService.saveStudent(student);
            model.addAttribute("successMessage", "Student details updated successfully");
            return "redirect:/students/list";
        } catch (Exception e) {
            model.addAttribute("error", "Error updating Student details: " + e.getLocalizedMessage());
            return "student/error";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, Model model) {
        try {
            studentService.deleteStudent(id);
            return "redirect:/students/list";
        } catch (Exception e) {
            model.addAttribute("error", "Error deleting Student: " + e.getLocalizedMessage());
            return "student/error";
        }
    }

    @GetMapping("/search")
    public String search(@RequestParam String query, Model model) {
        try {
            List<Student> students = studentService.searchStudents(query);
            model.addAttribute("students", students);
            model.addAttribute("searchQuery", query);
            return "student/list";
        } catch (Exception e) {
            model.addAttribute("error", "Error searching for students: " + e.getLocalizedMessage());
            return "student/error";
        }
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "student/error";
    }
}
