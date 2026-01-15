package com.example.attendancemanagement.service;

import com.example.attendancemanagement.entity.Role;
import com.example.attendancemanagement.entity.Student;
import com.example.attendancemanagement.entity.User;
import com.example.attendancemanagement.repository.StudentRepository;
import com.example.attendancemanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Student createStudent(String name, String email, String password, String rollNo) {
        if (studentRepository.existsByRollNo(rollNo)) {
            throw new IllegalArgumentException("Roll number already exists: " + rollNo);
        }
        
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }

        User user = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.STUDENT)
                .build();
        
        user = userRepository.save(user);

        Student student = Student.builder()
                .rollNo(rollNo)
                .user(user)
                .build();
        
        return studentRepository.save(student);
    }

    @Transactional(readOnly = true)
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Student> findByRollNo(String rollNo) {
        return studentRepository.findByRollNo(rollNo);
    }

    @Transactional(readOnly = true)
    public Optional<Student> findByUserId(Long userId) {
        return studentRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    public Student updateStudent(Long id, String rollNo) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + id));
        
        if (!student.getRollNo().equals(rollNo) && studentRepository.existsByRollNo(rollNo)) {
            throw new IllegalArgumentException("Roll number already exists: " + rollNo);
        }
        
        student.setRollNo(rollNo);
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + id));
        
        studentRepository.delete(student);
        userRepository.delete(student.getUser());
    }
}


