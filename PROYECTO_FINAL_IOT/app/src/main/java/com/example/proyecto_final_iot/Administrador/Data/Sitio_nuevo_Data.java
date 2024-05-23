package com.example.proyecto_final_iot.Administrador.Data;

public class Sitio_nuevo_Data {
    String codigo;
    String departamento;
    String geolocalizacion;
    String provincia;
    String tipoDeSitio;
    String tipoDeZona;
    String ubigeo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getGeolocalizacion() {
        return geolocalizacion;
    }

    public void setGeolocalizacion(String geolocalizacion) {
        this.geolocalizacion = geolocalizacion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getTipoDeSitio() {
        return tipoDeSitio;
    }

    public void setTipoDeSitio(String tipoDeSitio) {
        this.tipoDeSitio = tipoDeSitio;
    }

    public String getTipoDeZona() {
        return tipoDeZona;
    }

    public void setTipoDeZona(String tipoDeZona) {
        this.tipoDeZona = tipoDeZona;
    }

    public String getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }

    @Override
    public String toString() {
        return tipoDeSitio ;
    }
}
