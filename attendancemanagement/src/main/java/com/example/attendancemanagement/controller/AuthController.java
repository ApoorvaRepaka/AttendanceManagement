package com.example.attendancemanagement.controller;

import com.example.attendancemanagement.entity.Role;
import com.example.attendancemanagement.entity.Student;
import com.example.attendancemanagement.entity.User;
import com.example.attendancemanagement.repository.StudentRepository;
import com.example.attendancemanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("roles", new Role[]{Role.STUDENT, Role.TEACHER});
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(@RequestParam String name,
                                @RequestParam String email,
                                @RequestParam String password,
                                @RequestParam Role role,
                                @RequestParam(required = false) String rollNo,
                                RedirectAttributes redirectAttributes) {
        try {
            // Check if email already exists
            if (userRepository.existsByEmail(email)) {
                redirectAttributes.addFlashAttribute("error", "Email already registered!");
                return "redirect:/signup";
            }

            // Create user with BCrypt encoded password
            User user = User.builder()
                    .name(name)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .role(role)
                    .build();

            user = userRepository.save(user);

            // If student role, create student record
            if (role == Role.STUDENT && rollNo != null && !rollNo.isEmpty()) {
                if (studentRepository.existsByRollNo(rollNo)) {
                    // Rollback user creation
                    userRepository.delete(user);
                    redirectAttributes.addFlashAttribute("error", "Roll number already exists!");
                    return "redirect:/signup";
                }

                Student student = Student.builder()
                        .rollNo(rollNo)
                        .user(user)
                        .build();
                studentRepository.save(student);
            }

            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/login";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed: " + e.getMessage());
            return "redirect:/signup";
        }
    }

    @GetMapping("/register")
    public String redirectToSignup() {
        return "redirect:/signup";
    }
}


