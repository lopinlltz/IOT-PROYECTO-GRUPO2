package com.example.proyecto_final_iot.Supervisor.Entity;

public class SitioData {
    private String id_codigodeSitio;
    private int buttom_supervisor;
    private int buttom_info;

    private String id_departamento;
    private String id_provincia;
    private String id_distrito;
    private String id_ubigeo;
    private String id_tipo_de_zona;
    private String id_tipo_de_sitio;
    private String id_latitud_long;

    private String id_latitud_latitud;

    public String getId_latitud_latitud() {
        return id_latitud_latitud;
    }

    public void setId_latitud_latitud(String id_latitud_latitud) {
        this.id_latitud_latitud = id_latitud_latitud;
    }


    /*public SitioData(String id_codigodeSitio, String id_departamento, String id_provincia, String id_distrito, String id_ubigeo, String id_tipo_de_zona, String id_tipo_de_sitio, String id_latitud_long) {
        this.id_codigodeSitio = id_codigodeSitio;
        this.id_departamento = id_departamento;
        this.id_provincia = id_provincia;
        this.id_distrito = id_distrito;
        this.id_ubigeo = id_ubigeo;
        this.id_tipo_de_zona = id_tipo_de_zona;
        this.id_tipo_de_sitio = id_tipo_de_sitio;
        this.id_latitud_long = id_latitud_long;
    }

    public SitioData(String id_codigodeSitio, String id_departamento, String id_provincia, String id_distrito, String id_ubigeo, String id_latitud_long) {
        this.id_codigodeSitio = id_codigodeSitio;
        this.id_departamento = id_departamento;
        this.id_provincia = id_provincia;
        this.id_distrito = id_distrito;
        this.id_ubigeo = id_ubigeo;
        this.id_latitud_long = id_latitud_long;
    }*/

    // Getter y Setter para id_codigodeSitio


    public String getId_codigodeSitio() {
        return id_codigodeSitio;
    }

    public void setId_codigodeSitio(String id_codigodeSitio) {
        this.id_codigodeSitio = id_codigodeSitio;
    }

    // Getter y Setter para buttom_supervisor
    public int getButtom_supervisor() {
        return buttom_supervisor;
    }

    public void setButtom_supervisor(int buttom_supervisor) {
        this.buttom_supervisor = buttom_supervisor;
    }

    // Getter y Setter para buttom_info
    public int getButtom_info() {
        return buttom_info;
    }

    public void setButtom_info(int buttom_info) {
        this.buttom_info = buttom_info;
    }

    // Getter y Setter para id_departamento
    public String getId_departamento() {
        return id_departamento;
    }

    public void setId_departamento(String id_departamento) {
        this.id_departamento = id_departamento;
    }

    // Getter y Setter para id_provincia
    public String getId_provincia() {
        return id_provincia;
    }

    public void setId_provincia(String id_provincia) {
        this.id_provincia = id_provincia;
    }

    // Getter y Setter para id_distrito
    public String getId_distrito() {
        return id_distrito;
    }

    public void setId_distrito(String id_distrito) {
        this.id_distrito = id_distrito;
    }

    // Getter y Setter para id_ubigeo
    public String getId_ubigeo() {
        return id_ubigeo;
    }

    public void setId_ubigeo(String id_ubigeo) {
        this.id_ubigeo = id_ubigeo;
    }

    // Getter y Setter para id_tipo_de_zona
    public String getId_tipo_de_zona() {
        return id_tipo_de_zona;
    }

    public void setId_tipo_de_zona(String id_tipo_de_zona) {
        this.id_tipo_de_zona = id_tipo_de_zona;
    }

    // Getter y Setter para id_tipo_de_sitio
    public String getId_tipo_de_sitio() {
        return id_tipo_de_sitio;
    }

    public void setId_tipo_de_sitio(String id_tipo_de_sitio) {
        this.id_tipo_de_sitio = id_tipo_de_sitio;
    }

    // Getter y Setter para id_latitud_long
    public String getId_latitud_long() {
        return id_latitud_long;
    }

    public void setId_latitud_long(String id_latitud_long) {
        this.id_latitud_long = id_latitud_long;
    }

}
