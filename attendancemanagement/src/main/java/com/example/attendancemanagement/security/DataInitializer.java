package com.example.attendancemanagement.security;

import com.example.attendancemanagement.entity.Role;
import com.example.attendancemanagement.entity.User;
import com.example.attendancemanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            User admin = User.builder()
                    .name("Admin")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build();
            
            userRepository.save(admin);
            log.info("Default admin user created: admin@example.com / admin123");

            User teacher = User.builder()
                    .name("Teacher")
                    .email("teacher@example.com")
                    .password(passwordEncoder.encode("teacher123"))
                    .role(Role.TEACHER)
                    .build();
            
            userRepository.save(teacher);
            log.info("Default teacher user created: teacher@example.com / teacher123");
        }
    }
}


