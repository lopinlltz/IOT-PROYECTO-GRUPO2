package com.example.proyecto_final_iot.Administrador.Data;
public class DataClass
{
    private String id_codigodeSitio;
    private String id_departamento;
    private String id_provincia;
    private String id_distrito;
    private String id_ubigeo;
    //private String id_tipo_de_zona;
    //private String id_tipo_de_sitio;
    private String id_latitud_long;

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

    /*public String getId_tipo_de_zona() {
        return id_tipo_de_zona;
    }

    public String getId_tipo_de_sitio() {
        return id_tipo_de_sitio;
    }*/

    public String getId_latitud_long() {
        return id_latitud_long;
    }

    public DataClass(){
    }

    public DataClass(String id_codigodeSitio, String id_departamento, String id_provincia, String id_distrito, String id_ubigeo, String id_latitud_long) {
        this.id_codigodeSitio = id_codigodeSitio;
        this.id_departamento = id_departamento;
        this.id_provincia = id_provincia;
        this.id_distrito = id_distrito;
        this.id_ubigeo = id_ubigeo;
        //this.id_tipo_de_zona = id_tipo_de_zona;
        //this.id_tipo_de_sitio = id_tipo_de_sitio;
        this.id_latitud_long = id_latitud_long;
    }
}
