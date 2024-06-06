package com.example.proyecto_final_iot.Supervisor.Entity;

public class EquipoData {
    private String sku;
    private String serie;
    private String marca;
    private String modelo;
    private  String descripcion;
    private String fechaRegistro;

    private String id_codigodeSitio;

    // Getter
    public String getId_codigodeSitio() {
        return id_codigodeSitio;
    }

    // Setter
    public void setId_codigodeSitio(String id_codigodeSitio) {
        this.id_codigodeSitio = id_codigodeSitio;
    }

    /*public EquipoData(String sku, String serie, String marca, String modelo, String descripcion, String fechaRegistro) {
        this.sku = sku;
        this.serie = serie;
        this.marca = marca;
        this.modelo = modelo;
        this.descripcion = descripcion;
        this.fechaRegistro = fechaRegistro;
    }*/

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
