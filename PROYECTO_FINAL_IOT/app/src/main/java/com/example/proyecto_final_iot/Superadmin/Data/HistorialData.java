package com.example.proyecto_final_iot.Superadmin.Data;
public class HistorialData {
    private String activityName;
    private String userName;
    private String userRole;
    private String date;
    private String hour;

    public HistorialData() {}

    public HistorialData(String activityName, String userName, String userRole, String date, String hour) {
        this.activityName = activityName;
        this.userName = userName;
        this.userRole = userRole;
        this.date = date;
        this.hour = hour;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}

