package com.multiventas.app.dto;

import java.util.Objects;

public class UsuariosIn {

    private Long idUsuarios;

    private String nickname;

    private String nombre;

    private String appaterno;

    private String apmaterno;

    private String telefono;

    private String email;

    private Boolean activo;

    private Boolean confirmado;

    private Long fecConfirma;

    private Long creado;

    private Long actualizado;

    private Double latitud;

    private Double longitud;

    public UsuariosIn(Long idUsuarios, String nickname, String nombre, String appaterno, String apmaterno, String telefono, String email, Boolean activo, Boolean confirmado, Long fecConfirma, Long creado, Long actualizado, Double latitud, Double longitud) {
        this.idUsuarios = idUsuarios;
        this.nickname = nickname;
        this.nombre = nombre;
        this.appaterno = appaterno;
        this.apmaterno = apmaterno;
        this.telefono = telefono;
        this.email = email;
        this.activo = activo;
        this.confirmado = confirmado;
        this.fecConfirma = fecConfirma;
        this.creado = creado;
        this.actualizado = actualizado;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Long getIdUsuarios() {
        return idUsuarios;
    }

    public String getNickname() {
        return nickname;
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

    public String getEmail() {
        return email;
    }

    public Boolean getActivo() {
        return activo;
    }

    public Boolean getConfirmado() {
        return confirmado;
    }

    public Long getFecConfirma() {
        return fecConfirma;
    }

    public Long getCreado() {
        return creado;
    }

    public Long getActualizado() {
        return actualizado;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }
}
