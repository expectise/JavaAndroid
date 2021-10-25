package com.multiventas.app.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "Usuario")
public class Usuario {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "id", index = true)
    private final double id;

    private final String email;

    private final String contrasena;

    public Usuario(double id, String email, String contrasena) {
        this.id = id;
        this.email = email;
        this.contrasena = contrasena;
    }

    public double getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasena() {
        return contrasena;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Double.compare(usuario.id, id) == 0 &&
                Objects.equals(email, usuario.email) &&
                Objects.equals(contrasena, usuario.contrasena);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, contrasena);
    }
}