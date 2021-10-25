package com.multiventas.app.dto;

public class Login {
    private String email;
    private String contrasena;

    public Login(String email, String contrasena) {
        this.email = email;
        this.contrasena = contrasena;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasena() {
        return contrasena;
    }
}
