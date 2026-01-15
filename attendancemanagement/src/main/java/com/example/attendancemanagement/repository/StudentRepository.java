package com.example.attendancemanagement.repository;

import com.example.attendancemanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    Optional<Student> findByRollNo(String rollNo);
    
    Optional<Student> findByUserId(Long userId);
    
    boolean existsByRollNo(String rollNo);
}


