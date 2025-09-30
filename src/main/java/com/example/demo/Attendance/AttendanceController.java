package com.example.demo.Attendance;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/attendance")
    public ResponseEntity<List<Map<String, Object>>> getAllAttendance(
            @RequestParam(required = false) String date) {

        LocalDate queryDate = (date != null && !date.isEmpty())
                ? LocalDate.parse(date)
                : LocalDate.now();

        List<Map<String, Object>> attendanceData = attendanceService.getAllEmployeesWithAttendance(queryDate);
        return ResponseEntity.ok(attendanceData);
    }

    @GetMapping("/attendance/{date}")
    public ResponseEntity<List<Attendance>> getAttendanceByDate(@PathVariable String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        List<Attendance> attendances = attendanceService.getAttendanceByDate(parsedDate);
        return ResponseEntity.ok(attendances);
    }

    @GetMapping("/users/{userId}/attendance")
    public ResponseEntity<List<Attendance>> getUserAttendance(@PathVariable Long userId) {
        List<Attendance> attendances = attendanceService.getUserAttendance(userId);
        return ResponseEntity.ok(attendances);
    }

    @PostMapping("/attendance/mark")
    public ResponseEntity<Attendance> markAttendance(
            @RequestParam Long userId,
            @RequestParam String date,
            @RequestParam Attendance.Status status) {
        LocalDate parsedDate = LocalDate.parse(date);
        Attendance attendance = attendanceService.markAttendance(userId, parsedDate, status);
        return ResponseEntity.ok(attendance);
    }

    @PostMapping("/users/timein/{userId}")
    public ResponseEntity<Map<String, Object>> timeIn(@PathVariable Long userId) {
        try {
            Attendance attendance = attendanceService.markTimeIn(userId, LocalDate.now(), LocalTime.now());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Time In recorded successfully");
            response.put("timeIn", attendance.getTimeIn().toString());
            response.put("status", attendance.getStatus().toString());
            response.put("date", attendance.getAttendanceDate().toString());

            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "User not found");
            return ResponseEntity.status(404).body(errorResponse);
        }
    }

    @PostMapping("/users/timeout/{userId}")
    public ResponseEntity<Map<String, Object>> timeOut(@PathVariable Long userId) {
        try {
            Attendance attendance = attendanceService.markTimeOut(userId, LocalDate.now(), LocalTime.now());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Time Out recorded successfully");
            response.put("timeOut", attendance.getTimeOut().toString());
            response.put("timeIn", attendance.getTimeIn().toString());
            response.put("date", attendance.getAttendanceDate().toString());

            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "User not found or no time in record");
            return ResponseEntity.status(404).body(errorResponse);
        }
    }

    @PostMapping("/users/{userId}/excuse")
    public ResponseEntity<Map<String, Object>> excuseEmployee(@PathVariable Long userId) {
        try {
            Attendance attendance = attendanceService.excuseEmployee(userId, LocalDate.now());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Employee excused successfully");
            response.put("status", attendance.getStatus().toString());
            response.put("date", attendance.getAttendanceDate().toString());

            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "User not found");
            return ResponseEntity.status(404).body(errorResponse);
        }
    }
}