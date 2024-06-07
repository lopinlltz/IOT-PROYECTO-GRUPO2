package com.example.proyecto_final_iot.Superadmin.Data;

public class Admin {
    private String id;
    private String nombreUser;
    private String apellidoUser;
    private String dniUser;  // Cambiado a String
    private String correoUser;
    private String telefonoUser;  // Cambiado a String
    private String domicilioUser;
    private String hora;

    public Admin() {
        // Constructor vacío requerido por Firebase Firestore
    }

    public Admin(String id, String nombreUser, String apellidoUser, String dniUser, String correoUser, String telefonoUser, String domicilioUser, String hora) {
        this.id = id;
        this.nombreUser = nombreUser;
        this.apellidoUser = apellidoUser;
        this.dniUser = dniUser;
        this.correoUser = correoUser;
        this.telefonoUser = telefonoUser;
        this.domicilioUser = domicilioUser;
        this.hora = hora;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getApellidoUser() {
        return apellidoUser;
    }

    public void setApellidoUser(String apellidoUser) {
        this.apellidoUser = apellidoUser;
    }

    public String getDniUser() {
        return dniUser;
    }

    public void setDniUser(String dniUser) {
        this.dniUser = dniUser;
    }

    public String getCorreoUser() {
        return correoUser;
    }

    public void setCorreoUser(String correoUser) {
        this.correoUser = correoUser;
    }

    public String getTelefonoUser() {
        return telefonoUser;
    }

    public void setTelefonoUser(String telefonoUser) {
        this.telefonoUser = telefonoUser;
    }

    public String getDomicilioUser() {
        return domicilioUser;
    }

    public void setDomicilioUser(String domicilioUser) {
        this.domicilioUser = domicilioUser;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNombreCompleto() {
        return nombreUser + " " + apellidoUser;
    }
}
