package com.example.demo.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import java.time.LocalDate;
import java.time.LocalTime;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UserService userService;

    private final UserRepository userRepository;

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody SignupRequestDTO request) {
        String result = userService.registerEmployee(request);
        Map<String, String> response = new HashMap<>();
        response.put("message", result);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO request) {
        Users user = userService.loginEmployee(request);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid email or password"));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("firstName", user.getFirstName());
        response.put("lastName", user.getLastName());
        response.put("email", user.getEmail());
        response.put("position", user.getPosition());

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/{id}/attendance")
    public ResponseEntity<?> recordTimeIn(@PathVariable("id") Integer id) {
        Users user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        LocalTime now = LocalTime.now();
        LocalTime cutoffTime = LocalTime.of(9, 0);

        Users.AttendanceStatus status = now.isBefore(cutoffTime) || now.equals(cutoffTime)
                ? Users.AttendanceStatus.ON_TIME
                : Users.AttendanceStatus.LATE;

        if (user.getAttendanceDate() != null && user.getAttendanceDate().equals(LocalDate.now())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Time In already recorded for today"));
        }

        user.setTimeIn(now);
        user.setAttendanceStatus(status);
        user.setAttendanceDate(LocalDate.now());

        userRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Time In recorded successfully");
        response.put("status", status.name());
        response.put("timeIn", now.toString());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/timeout")
    public ResponseEntity<Map<String, String>> recordTimeOut(@PathVariable Integer id) {
        Users user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        LocalTime now = LocalTime.now();
        LocalTime standardTimeOut = LocalTime.of(17, 0);

        String message = now.isBefore(standardTimeOut)
                ? "Time Out recorded early"
                : "Time Out recorded successfully";

        if (user.getTimeIn() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Cannot Time Out before Time In"));
        }
        if (user.getTimeOut() != null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Time Out already recorded for today"));
        }

        user.setTimeOut(now);
        userRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        response.put("timeOut", now.toString());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/attendance")
    public ResponseEntity<Map<String, Object>> getAttendance(@PathVariable Integer id) {
        Users user = userRepository.findById(id).orElse(null);
        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found"));

        Map<String, Object> response = new HashMap<>();
        response.put("timeIn", user.getTimeIn());
        response.put("timeOut", user.getTimeOut());
        response.put("status", user.getAttendanceStatus());
        response.put("attendanceDate", user.getAttendanceDate());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats() {
        List<Users> allUsers = userRepository.findAll();
        long totalUsers = allUsers.size();

        long onTime = allUsers.stream()
                .filter(u -> u.getAttendanceStatus() != null && u.getAttendanceStatus() == Users.AttendanceStatus.ON_TIME)
                .count();

        long late = allUsers.stream()
                .filter(u -> u.getAttendanceStatus() != null && u.getAttendanceStatus() == Users.AttendanceStatus.LATE)
                .count();

        long absent = allUsers.stream()
                .filter(u -> u.getAttendanceStatus() == null)
                .count();

        Map<String, Long> response = new HashMap<>();
        response.put("totalUsers", totalUsers);
        response.put("onTime", onTime);
        response.put("late", late);
        response.put("absent", absent);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/excuse")
    public ResponseEntity<Map<String, String>> excuseEmployee(@PathVariable Integer id) {
        Users user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        user.setAttendanceStatus(Users.AttendanceStatus.EXCUSED);
        userRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Employee excused successfully");
        response.put("attendanceStatus", user.getAttendanceStatus().name());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/attendance")
    public ResponseEntity<List<Users>> getAttendanceByDate(@RequestParam String date) {
        LocalDate targetDate;
        try {
            targetDate = LocalDate.parse(date);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        List<Users> usersByDate = userRepository.findAll().stream()
                .filter(u -> targetDate.equals(u.getAttendanceDate()))
                .toList();

        return ResponseEntity.ok(usersByDate);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logout successful");
        return ResponseEntity.ok(response);
    }
}