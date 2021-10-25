package com.multiventas.app.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Ventadetallada", foreignKeys = {@ForeignKey(entity = Ventas.class,
        parentColumns = "idVentas",
        childColumns = "idVentas",
        onDelete = CASCADE)}, indices={
        @Index(value="idVentaDetallada", unique = true),
        @Index(value="idVentas")
})
public class Ventadetallada {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private final Long idVentaDetallada;

    @NonNull
    private final Long idVentas;

    private final Double precio;

    private final String titulo;

    private final String descripcion;

    private final String codigoProducto;

    private final Long creado;

    private final Long actualizado;


    public Ventadetallada(@NonNull Long idVentaDetallada, Long idVentas, Double precio, String titulo, String descripcion, String codigoProducto, Long creado, Long actualizado) {
        this.idVentaDetallada = idVentaDetallada;
        this.idVentas = idVentas;
        this.precio = precio;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.codigoProducto = codigoProducto;
        this.creado = creado;
        this.actualizado = actualizado;
    }

    @NonNull
    public Long getIdVentaDetallada() {
        return idVentaDetallada;
    }

    public Long getIdVentas() {
        return idVentas;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public Long getCreado() {
        return creado;
    }

    public Long getActualizado() {
        return actualizado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ventadetallada that = (Ventadetallada) o;
        return idVentaDetallada.equals(that.idVentaDetallada) &&
                Objects.equals(idVentas, that.idVentas) &&
                Objects.equals(precio, that.precio) &&
                Objects.equals(titulo, that.titulo) &&
                Objects.equals(descripcion, that.descripcion) &&
                Objects.equals(codigoProducto, that.codigoProducto) &&
                Objects.equals(creado, that.creado) &&
                Objects.equals(actualizado, that.actualizado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVentaDetallada, idVentas, precio, titulo, descripcion, codigoProducto, creado, actualizado);
    }
}
