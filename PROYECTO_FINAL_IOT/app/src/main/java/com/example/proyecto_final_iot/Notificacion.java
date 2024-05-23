package com.example.proyecto_final_iot;

public class Notificacion {
    private String titulo;
    private String mensaje;

    public Notificacion(String titulo, String mensaje) {
        this.titulo = titulo;
        this.mensaje = mensaje;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensaje() {
        return mensaje;
    }
}