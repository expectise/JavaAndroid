package com.multiventas.app.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;
import java.util.Objects;

public class UsuariosAndVentas {
    @Embedded
    public final Usuarios usuarios;

    @Relation(
            parentColumn = "idUsuarios",
            entityColumn = "idUsuarios"
    )
    private final List<Ventas> ventas;

    public UsuariosAndVentas(Usuarios usuarios, List<Ventas> ventas) {
        this.usuarios = usuarios;
        this.ventas = ventas;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public List<Ventas> getVentas() {
        return ventas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuariosAndVentas that = (UsuariosAndVentas) o;
        return Objects.equals(usuarios, that.usuarios) &&
                Objects.equals(ventas, that.ventas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarios, ventas);
    }
}
