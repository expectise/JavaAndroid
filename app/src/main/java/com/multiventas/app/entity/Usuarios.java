package com.multiventas.app.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Usuarios")
public class Usuarios {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private final Long idUsuarios;

    private final String nickname;

    private final String nombre;

    private final String appaterno;

    private final String apmaterno;

    private final String telefono;

    private final String email;

    private final Boolean activo;

    private final Boolean confirmado;

    private final Long fecConfirma;

    private final Long creado;

    private final Long actualizado;

    private final Double latitud;

    private final Double longitud;

    public Usuarios(@NonNull Long idUsuarios, String nickname, String nombre, String appaterno, String apmaterno, String telefono, String email, Boolean activo, Boolean confirmado, Long fecConfirma, Long creado, Long actualizado, Double latitud, Double longitud) {
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

    @NonNull
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuarios usuarios = (Usuarios) o;
        return idUsuarios.equals(usuarios.idUsuarios) &&
                Objects.equals(nickname, usuarios.nickname) &&
                Objects.equals(nombre, usuarios.nombre) &&
                Objects.equals(appaterno, usuarios.appaterno) &&
                Objects.equals(apmaterno, usuarios.apmaterno) &&
                Objects.equals(telefono, usuarios.telefono) &&
                Objects.equals(email, usuarios.email) &&
                Objects.equals(activo, usuarios.activo) &&
                Objects.equals(confirmado, usuarios.confirmado) &&
                Objects.equals(fecConfirma, usuarios.fecConfirma) &&
                Objects.equals(creado, usuarios.creado) &&
                Objects.equals(actualizado, usuarios.actualizado) &&
                Objects.equals(latitud, usuarios.latitud) &&
                Objects.equals(longitud, usuarios.longitud);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuarios, nickname, nombre, appaterno, apmaterno, telefono, email, activo, confirmado, fecConfirma, creado, actualizado, latitud, longitud);
    }
}
