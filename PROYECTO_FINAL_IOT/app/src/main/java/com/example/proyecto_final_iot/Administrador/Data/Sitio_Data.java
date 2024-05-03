package com.example.proyecto_final_iot.Administrador.Data;

public class Sitio_Data {
    private String nombreSitio;
    private int buttom_supervisor;
    private int buttom_info;



    public String getNombreSitio() {
        return nombreSitio;
    }

    public int getButtom_supervisor() {
        return buttom_supervisor;
    }

    public int getButtom_info() {
        return buttom_info;
    }

    public Sitio_Data(String nombreSitio) {
        this.nombreSitio = nombreSitio;
    }
}
