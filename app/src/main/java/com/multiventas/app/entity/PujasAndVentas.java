package com.multiventas.app.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;
import java.util.Objects;

public class PujasAndVentas {
    @Embedded
    private final Pujas pujas;

    @Relation(
            parentColumn = "idPujas",
            entityColumn = "idPuja"
    )
    private final List<Ventas> ventas;

    public PujasAndVentas(Pujas pujas, List<Ventas> ventas) {
        this.pujas = pujas;
        this.ventas = ventas;
    }

    public Pujas getPujas() {
        return pujas;
    }

    public List<Ventas> getVentas() {
        return ventas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PujasAndVentas that = (PujasAndVentas) o;
        return Objects.equals(pujas, that.pujas) &&
                Objects.equals(ventas, that.ventas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pujas, ventas);
    }
}
