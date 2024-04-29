package com.example.proyecto_final_iot.Superadmin.Data;

public class Admin {
    private String hora;
    private String NombreAdmin;

    public Admin(String hora, String nombreAdmin) {
        this.hora = hora;
        this.NombreAdmin = nombreAdmin;
    }

    public String getHora() {
        return hora;
    }

    public String getNombreAdmin() {
        return NombreAdmin;
    }
}
