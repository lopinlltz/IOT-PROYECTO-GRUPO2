package com.example.proyecto_final_iot.Administrador.Data;

import java.io.Serializable;

public class Supervisor_Data implements Serializable {
    private String id_nombreUser;
    private String id_apellidoUser;
    private String id_dniUSer;
    private String id_correoUser;
    private String id_telefonoUser;
    private String id_domicilioUser;
    private String dataImage;
    private String status_admin;
    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Supervisor_Data(String idNombreUser, String imageUrl) {
    }
    public Supervisor_Data() {
    }
    public Supervisor_Data(String id_nombreUser, String id_apellidoUser, String id_dniUSer, String id_correoUser, String id_telefonoUser, String id_domicilioUser, String dataImage, String status_admin) {

        this.id_nombreUser = id_nombreUser;
        this.id_apellidoUser = id_apellidoUser;
        this.id_dniUSer = id_dniUSer;
        this.id_correoUser = id_correoUser;
        this.id_telefonoUser = id_telefonoUser;
        this.id_domicilioUser = id_domicilioUser;
        this.dataImage = dataImage;
        this.status_admin = status_admin;
    }



    public String getId_nombreUser() {
        return id_nombreUser;
    }

    public void setId_nombreUser(String id_nombreUser) {
        this.id_nombreUser = id_nombreUser;
    }

    public String getId_apellidoUser() {
        return id_apellidoUser;
    }

    public void setId_apellidoUser(String id_apellidoUser) {
        this.id_apellidoUser = id_apellidoUser;
    }

    public String getId_dniUSer() {
        return id_dniUSer;
    }

    public void setId_dniUSer(String id_dniUSer) {
        this.id_dniUSer = id_dniUSer;
    }

    public String getId_correoUser() {
        return id_correoUser;
    }

    public void setId_correoUser(String id_correoUser) {
        this.id_correoUser = id_correoUser;
    }

    public String getId_telefonoUser() {
        return id_telefonoUser;
    }

    public void setId_telefonoUser(String id_telefonoUser) {
        this.id_telefonoUser = id_telefonoUser;
    }

    public String getId_domicilioUser() {
        return id_domicilioUser;
    }

    public void setId_domicilioUser(String id_domicilioUser) {
        this.id_domicilioUser = id_domicilioUser;
    }

    public String getDataImage() {
        return dataImage;
    }

    public void setDataImage(String dataImage) {
        this.dataImage = dataImage;
    }

    public String getStatus_admin() {
        return status_admin;
    }

    public void setStatus_admin(String status_admin) {
        this.status_admin = status_admin;
    }


}
