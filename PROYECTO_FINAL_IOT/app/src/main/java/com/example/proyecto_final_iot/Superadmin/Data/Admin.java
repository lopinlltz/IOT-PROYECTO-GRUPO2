package com.example.proyecto_final_iot.Superadmin.Data;

public class Admin {
    private String nombreUser;
    private String apellidoUser;
    private String id;  // Este será el ID del documento en Firestore
    private String hora;

    public Admin() {
        // Constructor vacío requerido por Firebase Firestore
    }

    public Admin(String id, String nombreUser, String apellidoUser, String hora) {
        this.id = id;
        this.nombreUser = nombreUser;
        this.apellidoUser = apellidoUser;
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
