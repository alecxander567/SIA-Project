package com.example.demo.Attendance;

import com.example.demo.Users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByAttendanceDate(LocalDate date);

    List<Attendance> findByUserId(Long userId);

    Optional<Attendance> findByUserIdAndAttendanceDate(Long userId, LocalDate date);

    boolean existsByUserIdAndAttendanceDate(Long userId, LocalDate date);

    List<Attendance> findByUser(Users user);

    List<Attendance> findByUserIdAndAttendanceDateBetween(
            Long userId, LocalDate startDate, LocalDate endDate
    );

    List<Attendance> findByStatus(Attendance.Status status);

    List<Attendance> findByUserIdAndStatus(Long userId, Attendance.Status status);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.user.id = :userId AND a.status = :status")
    long countByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Attendance.Status status);
}