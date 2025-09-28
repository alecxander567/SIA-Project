package com.example.demo.Users;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "firstName", nullable = false, length = 50)
    private String firstName;

    @Column(name = "lastName", nullable = false, length = 50)
    private String lastName;

    public enum Position {
        ADMIN,
        RIDER,
        HR
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Position position;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "sex", nullable = false, length = 10)
    private String sex;

    @Column(name = "contactNumber", nullable = false, length = 20)
    private String contactNumber;

    public enum AttendanceStatus {
        ON_TIME,
        LATE,
        ABSENT,
        EXCUSED
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "attendanceStatus", length = 20)
    private AttendanceStatus attendanceStatus;

    @Column(name = "timeIn")
    private LocalTime timeIn;

    @Column(name = "timeOut")
    private LocalTime timeOut;

    @Column(name = "attendanceDate")
    private LocalDate attendanceDate;

    public Users() {}

    public Users(String firstName, String lastName, Position position, String email,
                 String password, String contactNumber, String sex,
                 AttendanceStatus attendanceStatus, LocalTime timeIn, LocalTime timeOut, LocalDate attendanceDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.email = email;
        this.password = password;
        this.contactNumber = contactNumber;
        this.sex = sex;
        this.attendanceStatus = attendanceStatus;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.attendanceDate = attendanceDate;
    }

    // Getters and setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public AttendanceStatus getAttendanceStatus() { return attendanceStatus; }
    public void setAttendanceStatus(AttendanceStatus attendanceStatus) { this.attendanceStatus = attendanceStatus; }

    public LocalTime getTimeIn() { return timeIn; }
    public void setTimeIn(LocalTime timeIn) { this.timeIn = timeIn; }

    public LocalTime getTimeOut() { return timeOut; }
    public void setTimeOut(LocalTime timeOut) { this.timeOut = timeOut; }

    public LocalDate getAttendanceDate() { return attendanceDate; }
    public void setAttendanceDate(LocalDate attendanceDate) { this.attendanceDate = attendanceDate; }
}
