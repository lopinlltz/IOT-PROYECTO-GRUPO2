package com.example.proyecto_final_iot.Administrador.Data;

public class Sitio_Data {
    private String id_codigodeSitio;
    private String id_departamento;
    private String id_provincia;
    private String id_distrito;
    private String id_ubigeo;
    private String id_tipo_de_zona;
    private String id_tipo_de_sitio;
    private String id_latitud_long;
    private String id_latitud_latitud;
    private String documentoID;

    public String getId_codigodeSitio() {
        return id_codigodeSitio;
    }

    public String getId_departamento() {
        return id_departamento;
    }

    public String getId_provincia() {
        return id_provincia;
    }

    public String getId_distrito() {
        return id_distrito;
    }

    public String getId_ubigeo() {
        return id_ubigeo;
    }

    public String getId_tipo_de_zona() {
        return id_tipo_de_zona;
    }

    public String getId_tipo_de_sitio() {
        return id_tipo_de_sitio;
    }

    public String getId_latitud_long() {
        return id_latitud_long;
    }

    public String getDocumentoID() {
        return documentoID;
    }

    public void setDocumentoID(String documentoID) {
        this.documentoID = documentoID;
    }

    public String getId_latitud_latitud() {
        return id_latitud_latitud;
    }
}
