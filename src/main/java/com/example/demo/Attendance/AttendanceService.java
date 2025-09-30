package com.example.demo.Attendance;

import com.example.demo.Users.UserRepository;
import com.example.demo.Users.Users;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, UserRepository userRepository) {
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
    }

    public List<Map<String, Object>> getAllEmployeesWithAttendance(LocalDate date) {
        List<Users> allUsers = userRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Users user : allUsers) {
            Map<String, Object> employeeData = new HashMap<>();

            employeeData.put("id", user.getId());
            employeeData.put("firstName", user.getFirstName());
            employeeData.put("lastName", user.getLastName());
            employeeData.put("position", user.getPosition().toString());
            employeeData.put("email", user.getEmail());

            Optional<Attendance> attendanceOpt = attendanceRepository
                    .findByUserIdAndAttendanceDate(user.getId(), date);

            if (attendanceOpt.isPresent()) {
                Attendance attendance = attendanceOpt.get();
                employeeData.put("attendanceStatus", attendance.getStatus().toString());
                employeeData.put("timeIn", attendance.getTimeIn() != null ? attendance.getTimeIn().toString() : null);
                employeeData.put("timeOut", attendance.getTimeOut() != null ? attendance.getTimeOut().toString() : null);
                employeeData.put("attendanceDate", attendance.getAttendanceDate().toString());
            } else {
                employeeData.put("attendanceStatus", "ABSENT");
                employeeData.put("timeIn", null);
                employeeData.put("timeOut", null);
                employeeData.put("attendanceDate", date.toString());
            }

            result.add(employeeData);
        }

        return result;
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByAttendanceDate(date);
    }

    public List<Attendance> getUserAttendance(Long userId) {
        return attendanceRepository.findByUserId(userId);
    }

    @Transactional
    public Attendance markTimeIn(Long userId, LocalDate date, LocalTime timeIn) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Optional<Attendance> existingAttendance = attendanceRepository
                .findByUserIdAndAttendanceDate(userId, date);

        if (existingAttendance.isPresent()) {
            throw new IllegalStateException("You have already clocked in today");
        }

        Attendance.Status status = timeIn.isAfter(LocalTime.of(9, 0))
                ? Attendance.Status.LATE
                : Attendance.Status.ON_TIME;

        Attendance attendance = new Attendance(date, status, timeIn, user);
        return attendanceRepository.save(attendance);
    }

    @Transactional
    public Attendance markTimeOut(Long userId, LocalDate date, LocalTime timeOut) {
        Attendance attendance = attendanceRepository
                .findByUserIdAndAttendanceDate(userId, date)
                .orElseThrow(() -> new RuntimeException("No time in record found for today. Please clock in first."));

        if (attendance.getTimeOut() != null) {
            throw new IllegalStateException("You have already clocked out today");
        }

        attendance.setTimeOut(timeOut);
        return attendanceRepository.save(attendance);
    }

    @Transactional
    public Attendance excuseEmployee(Long userId, LocalDate date) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Optional<Attendance> existingAttendance = attendanceRepository
                .findByUserIdAndAttendanceDate(userId, date);

        if (existingAttendance.isPresent()) {
            Attendance attendance = existingAttendance.get();
            attendance.setStatus(Attendance.Status.EXCUSED);
            attendance.setTimeIn(null);
            attendance.setTimeOut(null);
            return attendanceRepository.save(attendance);
        } else {
            Attendance attendance = new Attendance(date, Attendance.Status.EXCUSED, user);
            return attendanceRepository.save(attendance);
        }
    }

    public Attendance markAttendance(Long userId, LocalDate date, Attendance.Status status) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (attendanceRepository.existsByUserIdAndAttendanceDate(userId, date)) {
            throw new IllegalStateException("Attendance already recorded for this date");
        }

        Attendance attendance = new Attendance(date, status, user);
        return attendanceRepository.save(attendance);
    }
}