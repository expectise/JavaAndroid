package com.multiventas.app.dto;

public class Register {
    private String nickname;
    private String email;
    private String contrasena;
    private String confcontrasena;
    private String nombre;
    private String appaterno;
    private String apmaterno;
    private String telefono;
    private String captchaResponse;
    private double latitud;
    private double longitud;

    public Register(String nickname, String email, String contrasena, String confcontrasena, String nombre, String appaterno, String apmaterno, String telefono, String captchaResponse, double latitud, double longitud) {
        this.nickname = nickname;
        this.email = email;
        this.contrasena = contrasena;
        this.confcontrasena = confcontrasena;
        this.nombre = nombre;
        this.appaterno = appaterno;
        this.apmaterno = apmaterno;
        this.telefono = telefono;
        this.captchaResponse = captchaResponse;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getConfcontrasena() {
        return confcontrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAppaterno() {
        return appaterno;
    }

    public String getApmaterno() {
        return apmaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCaptchaResponse() {
        return captchaResponse;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
}
