package com.multiventas.app.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;
import java.util.Objects;

public class VentasAndVentadetallada {
    @Embedded
    private final Ventas ventas;

    @Relation(
            parentColumn = "idVentas",
            entityColumn = "idVentas"
    )
    private final List<Ventadetallada> ventadetallada;

    public VentasAndVentadetallada(Ventas ventas, List<Ventadetallada> ventadetallada) {
        this.ventas = ventas;
        this.ventadetallada = ventadetallada;
    }

    public Ventas getVentas() {
        return ventas;
    }

    public List<Ventadetallada> getVentadetallada() {
        return ventadetallada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VentasAndVentadetallada that = (VentasAndVentadetallada) o;
        return Objects.equals(ventas, that.ventas) &&
                Objects.equals(ventadetallada, that.ventadetallada);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ventas, ventadetallada);
    }
}
