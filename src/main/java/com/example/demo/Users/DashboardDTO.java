package com.example.demo.Users;

public class DashboardDTO {
    private long totalEmployees;
    private long onTime;
    private long late;
    private long absent;

    public DashboardDTO(long totalEmployees, long onTime, long late, long absent) {
        this.totalEmployees = totalEmployees;
        this.onTime = onTime;
        this.late = late;
        this.absent = absent;
    }

    public long getTotalEmployees() {
        return totalEmployees;
    }

    public long getOnTime() {
        return onTime;
    }

    public long getLate() {
        return late;
    }

    public long getAbsent() {
        return absent;
    }
}
