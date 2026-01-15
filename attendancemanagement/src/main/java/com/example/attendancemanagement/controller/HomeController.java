package com.example.attendancemanagement.controller;

import com.example.attendancemanagement.entity.Role;
import com.example.attendancemanagement.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            Role role = userDetails.getUser().getRole();
            
            // Redirect based on role
            switch (role) {
                case ADMIN:
                    return "redirect:/admin/dashboard";
                case TEACHER:
                    return "redirect:/teacher/dashboard";
                case STUDENT:
                    return "redirect:/student/dashboard";
                default:
                    return "dashboard";
            }
        }
        return "dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
