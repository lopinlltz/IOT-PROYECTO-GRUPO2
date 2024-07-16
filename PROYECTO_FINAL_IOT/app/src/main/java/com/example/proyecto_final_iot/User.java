package com.example.proyecto_final_iot;

/*public class User {
    private String id;
    private String name;
    private String role;

    public User() {}

    public User(String id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}*/

public class User {
    private String id;
    private String nombreUser;
    private String apellidoUser;
    private String rol;
    private String dataImage; // URL de la imagen de perfil

    // Constructor vacío requerido por Firebase
    public User() {
    }

    public User(String id, String nombreUser, String apellidoUser, String rol, String dataImage) {
        this.id = id;
        this.nombreUser = nombreUser;
        this.apellidoUser = apellidoUser;
        this.rol = rol;
        this.dataImage = dataImage;
    }

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getApellidoUser() {
        return apellidoUser;
    }

    public void setApellidoUser(String apellidoUser) {
        this.apellidoUser = apellidoUser;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getDataImage() {
        return dataImage;
    }

    public void setDataImage(String dataImage) {
        this.dataImage = dataImage;
    }
}

