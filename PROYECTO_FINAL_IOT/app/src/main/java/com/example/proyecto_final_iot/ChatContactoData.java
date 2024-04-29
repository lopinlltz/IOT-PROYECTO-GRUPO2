package com.example.proyecto_final_iot;

public class ChatContactoData {
    private String nombreAdmin;
    private String shortMsg;

    public ChatContactoData(String nombreAdmin, String shortMsg) {
        this.nombreAdmin = nombreAdmin;
        this.shortMsg = shortMsg;
    }

    public String getNombreAdmin() {
        return nombreAdmin;
    }

    public String getShortMsg() {
        return shortMsg;
    }
}
