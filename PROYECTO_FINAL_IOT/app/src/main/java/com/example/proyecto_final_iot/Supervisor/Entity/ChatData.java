package com.example.proyecto_final_iot.Supervisor.Entity;

public class ChatData {

    private String usuario;
    private String mensaje;
    private String hour;

    public ChatData(String usuario, String mensaje, String hour) {
        this.usuario = usuario;
        this.mensaje = mensaje;
        this.hour = hour;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getHour() {
        return hour;
    }
}

