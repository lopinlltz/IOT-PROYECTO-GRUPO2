package com.example.proyecto_final_iot.Superadmin.Data;

public class Supervisor {
    private String hora;
    private String NombreSupervisor;

    public Supervisor(String hora, String nombreSupervisor) {
        this.hora = hora;
        this.NombreSupervisor = nombreSupervisor;
    }

    public String getHora() {
        return hora;
    }

    public String getNombreSupervisor() {
        return NombreSupervisor;
    }
}
