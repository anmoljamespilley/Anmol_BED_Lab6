package com.gl.studentmanagementsystem.data;

import com.gl.studentmanagementsystem.model.UserRole;
import com.gl.studentmanagementsystem.model.Student;
import com.gl.studentmanagementsystem.model.User;
import com.gl.studentmanagementsystem.repository.StudentRepository;
import com.gl.studentmanagementsystem.repository.UserRepository;
import com.gl.studentmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(StudentRepository studentRepository, UserRepository userRepository, UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        loadStudents();
        loadUsers();
    }

    private void loadStudents() {
        // Check if students already exist
        if (studentRepository.count() == 0) {
            // Load 5 sample students
            Student student1 = new Student("Anmol", "Pilley", "MBA", "India");
            Student student2 = new Student("John", "Doe", "B.Tech", "USA");
            Student student3 = new Student("Jane", "Smith", "B.Arch", "Canada");
            Student student4 = new Student("Mike", "Johnson", "B.Tech", "UK");
            Student student5 = new Student("Emily", "Williams", "B.Com", "Australia");

            studentRepository.saveAll(Arrays.asList(student1, student2, student3, student4, student5));
        }
    }

    private void loadUsers() {
        // Check if users already exist
        if (userRepository.count() == 0) {
            // Load admin user
            User adminUser = new User("admin", passwordEncoder.encode("admin"), UserRole.ADMIN);
            userService.saveUser(adminUser);

            // Load regular user
            User regularUser = new User("anmol", passwordEncoder.encode("anmol"), UserRole.USER);
            userService.saveUser(regularUser);
        }
    }
}
