package com.example.attendancemanagement.controller;

import com.example.attendancemanagement.entity.Attendance;
import com.example.attendancemanagement.entity.AttendanceStatus;
import com.example.attendancemanagement.entity.Student;
import com.example.attendancemanagement.security.CustomUserDetails;
import com.example.attendancemanagement.service.AttendanceService;
import com.example.attendancemanagement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentDashboardController {

    private final StudentService studentService;
    private final AttendanceService attendanceService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Initialize defaults
        model.addAttribute("student", null);
        model.addAttribute("attendances", new ArrayList<>());
        model.addAttribute("attendancePercentage", 0.0);
        model.addAttribute("totalClasses", 0);
        model.addAttribute("presentCount", 0L);
        model.addAttribute("absentCount", 0L);
        model.addAttribute("lateCount", 0L);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            Long userId = userDetails.getId();
            
            Optional<Student> studentOpt = studentService.findByUserId(userId);
            
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                List<Attendance> attendances = attendanceService.findByStudentId(student.getId());
                double percentage = attendanceService.calculateAttendancePercentage(student.getId());
                
                // Count by status
                long presentCount = attendances.stream().filter(a -> a.getStatus() == AttendanceStatus.PRESENT).count();
                long absentCount = attendances.stream().filter(a -> a.getStatus() == AttendanceStatus.ABSENT).count();
                long lateCount = attendances.stream().filter(a -> a.getStatus() == AttendanceStatus.LATE).count();
                
                model.addAttribute("student", student);
                model.addAttribute("attendances", attendances);
                model.addAttribute("attendancePercentage", percentage);
                model.addAttribute("totalClasses", attendances.size());
                model.addAttribute("presentCount", presentCount);
                model.addAttribute("absentCount", absentCount);
                model.addAttribute("lateCount", lateCount);
            }
        }
        
        return "student/dashboard";
    }

    @GetMapping("/attendance")
    public String viewAttendance(Model model) {
        // Initialize defaults
        model.addAttribute("student", null);
        model.addAttribute("attendances", new ArrayList<>());
        model.addAttribute("attendancePercentage", 0.0);
        model.addAttribute("totalClasses", 0);
        model.addAttribute("presentCount", 0L);
        model.addAttribute("absentCount", 0L);
        model.addAttribute("lateCount", 0L);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            Long userId = userDetails.getId();
            
            Optional<Student> studentOpt = studentService.findByUserId(userId);
            
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                List<Attendance> attendances = attendanceService.findByStudentId(student.getId());
                double percentage = attendanceService.calculateAttendancePercentage(student.getId());
                
                // Count by status
                long presentCount = attendances.stream().filter(a -> a.getStatus() == AttendanceStatus.PRESENT).count();
                long absentCount = attendances.stream().filter(a -> a.getStatus() == AttendanceStatus.ABSENT).count();
                long lateCount = attendances.stream().filter(a -> a.getStatus() == AttendanceStatus.LATE).count();
                
                model.addAttribute("student", student);
                model.addAttribute("attendances", attendances);
                model.addAttribute("attendancePercentage", percentage);
                model.addAttribute("totalClasses", attendances.size());
                model.addAttribute("presentCount", presentCount);
                model.addAttribute("absentCount", absentCount);
                model.addAttribute("lateCount", lateCount);
            }
        }
        
        return "student/attendance";
    }
}
