package com.example.attendancemanagement.service;

import com.example.attendancemanagement.entity.Attendance;
import com.example.attendancemanagement.entity.AttendanceStatus;
import com.example.attendancemanagement.entity.Student;
import com.example.attendancemanagement.repository.AttendanceRepository;
import com.example.attendancemanagement.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;

    public Attendance markAttendance(Long studentId, LocalDate date, AttendanceStatus status) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id: " + studentId));

        Optional<Attendance> existingAttendance = attendanceRepository.findByStudentIdAndDate(studentId, date);
        
        if (existingAttendance.isPresent()) {
            Attendance attendance = existingAttendance.get();
            attendance.setStatus(status);
            return attendanceRepository.save(attendance);
        }

        Attendance attendance = Attendance.builder()
                .date(date)
                .status(status)
                .student(student)
                .build();
        
        return attendanceRepository.save(attendance);
    }

    @Transactional(readOnly = true)
    public Optional<Attendance> findById(Long id) {
        return attendanceRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Attendance> findByStudentId(Long studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }

    @Transactional(readOnly = true)
    public List<Attendance> findByDate(LocalDate date) {
        return attendanceRepository.findByDate(date);
    }

    @Transactional(readOnly = true)
    public List<Attendance> findByStudentIdAndDateRange(Long studentId, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByStudentIdAndDateBetween(studentId, startDate, endDate);
    }

    @Transactional(readOnly = true)
    public Optional<Attendance> findByStudentIdAndDate(Long studentId, LocalDate date) {
        return attendanceRepository.findByStudentIdAndDate(studentId, date);
    }

    @Transactional(readOnly = true)
    public List<Attendance> findAll() {
        return attendanceRepository.findAll();
    }

    public Attendance updateAttendance(Long id, AttendanceStatus status) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Attendance not found with id: " + id));
        
        attendance.setStatus(status);
        return attendanceRepository.save(attendance);
    }

    public void deleteAttendance(Long id) {
        if (!attendanceRepository.existsById(id)) {
            throw new IllegalArgumentException("Attendance not found with id: " + id);
        }
        attendanceRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long countByStudentIdAndStatus(Long studentId, AttendanceStatus status) {
        return attendanceRepository.countByStudentIdAndStatus(studentId, status);
    }

    @Transactional(readOnly = true)
    public double calculateAttendancePercentage(Long studentId) {
        List<Attendance> attendances = attendanceRepository.findByStudentId(studentId);
        if (attendances.isEmpty()) {
            return 0.0;
        }
        
        long presentCount = attendances.stream()
                .filter(a -> a.getStatus() == AttendanceStatus.PRESENT)
                .count();
        
        return (double) presentCount / attendances.size() * 100;
    }
}


