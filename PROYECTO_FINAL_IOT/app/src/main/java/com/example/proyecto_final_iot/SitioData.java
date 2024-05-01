package com.example.proyecto_final_iot;

public class SitioData {
    private String siteName;
    private String location;

    public SitioData(String siteName, String location) {
        this.siteName = siteName;
        this.location = location;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getLocation() {
        return location;
    }
}
