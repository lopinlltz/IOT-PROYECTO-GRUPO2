package com.example.proyecto_final_iot.Superadmin.Data;

public class Admin {
    private String id;
    private String nombreUser;
    private String apellidoUser;
    private int dniUser;
    private String correoUser;
    private int telefonoUser;
    private String domicilioUser;
    private String hora;

    public Admin() {
        // Constructor vacío requerido por Firebase Firestore
    }

    public Admin(String id, String nombreUser, String apellidoUser, int dniUser, String correoUser, int telefonoUser, String domicilioUser, String hora) {
        this.id = id;
        this.nombreUser = nombreUser;
        this.apellidoUser = apellidoUser;
        this.dniUser = dniUser;
        this.correoUser = correoUser;
        this.telefonoUser = telefonoUser;
        this.domicilioUser = domicilioUser;
        this.hora = hora;
    }

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

    public int getDniUser() {
        return dniUser;
    }

    public void setDniUser(int dniUser) {
        this.dniUser = dniUser;
    }

    public String getCorreoUser() {
        return correoUser;
    }

    public void setCorreoUser(String correoUser) {
        this.correoUser = correoUser;
    }

    public int getTelefonoUser() {
        return telefonoUser;
    }

    public void setTelefonoUser(int telefonoUser) {
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
