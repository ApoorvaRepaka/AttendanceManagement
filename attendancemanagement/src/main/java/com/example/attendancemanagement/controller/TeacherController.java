package com.example.attendancemanagement.controller;

import com.example.attendancemanagement.entity.AttendanceStatus;
import com.example.attendancemanagement.service.AttendanceService;
import com.example.attendancemanagement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final StudentService studentService;
    private final AttendanceService attendanceService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalStudents", studentService.findAllStudents().size());
        model.addAttribute("todayAttendance", attendanceService.findByDate(LocalDate.now()).size());
        model.addAttribute("students", studentService.findAllStudents());
        model.addAttribute("currentDate", LocalDate.now());
        return "teacher/dashboard";
    }

    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.findAllStudents());
        return "teacher/students";
    }

    @GetMapping("/mark-attendance")
    public String showMarkAttendanceForm(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                         Model model) {
        if (date == null) {
            date = LocalDate.now();
        }
        model.addAttribute("students", studentService.findAllStudents());
        model.addAttribute("selectedDate", date);
        model.addAttribute("statuses", AttendanceStatus.values());
        return "teacher/mark-attendance";
    }

    @PostMapping("/mark-attendance")
    public String markAttendance(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                 @RequestParam Long[] studentIds,
                                 @RequestParam AttendanceStatus[] statuses,
                                 RedirectAttributes redirectAttributes) {
        try {
            for (int i = 0; i < studentIds.length; i++) {
                attendanceService.markAttendance(studentIds[i], date, statuses[i]);
            }
            redirectAttributes.addFlashAttribute("success", "Attendance marked successfully for " + date);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to mark attendance: " + e.getMessage());
        }
        return "redirect:/teacher/mark-attendance?date=" + date;
    }

    @GetMapping("/attendance")
    public String listAttendance(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                 Model model) {
        if (date == null) {
            date = LocalDate.now();
        }
        model.addAttribute("attendances", attendanceService.findByDate(date));
        model.addAttribute("selectedDate", date);
        model.addAttribute("students", studentService.findAllStudents());
        return "teacher/attendance-history";
    }

    @GetMapping("/reports")
    public String showReports(@RequestParam(required = false) Long studentId,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                              Model model) {
        model.addAttribute("students", studentService.findAllStudents());
        
        if (studentId != null && startDate != null && endDate != null) {
            model.addAttribute("attendances", attendanceService.findByStudentIdAndDateRange(studentId, startDate, endDate));
            model.addAttribute("selectedStudent", studentService.findById(studentId).orElse(null));
            model.addAttribute("attendancePercentage", attendanceService.calculateAttendancePercentage(studentId));
        }
        
        model.addAttribute("selectedStudentId", studentId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        
        return "teacher/reports";
    }
}
