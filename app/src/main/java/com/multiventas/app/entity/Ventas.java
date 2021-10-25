package com.multiventas.app.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


import java.util.Objects;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Ventas", foreignKeys = {
        @ForeignKey(entity = Usuarios.class,
        parentColumns = "idUsuarios",
        childColumns = "idUsuarios",
        onDelete = CASCADE),
        @ForeignKey(entity = Pujas.class,
                parentColumns = "idPujas",
                childColumns = "idPujas",
                onDelete = CASCADE)
}, indices={
        @Index(value="idVentas", unique = true),
        @Index(value="idUsuarios"),
        @Index(value="idPujas")
})
public class Ventas {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private final Long idVentas;

    @NonNull
    private final Long idUsuarios;

    @NonNull
    private final Long idPujas;

    private final String folio;

    private final Double costoEnvio;

    private final Double precioExcento;

    private final Byte enviado;

    private final String guia;

    private final String empresa;

    private final Long creado;

    private final Long actualizado;

    public Ventas(@NonNull Long idVentas, Long idUsuarios, Long idPujas, String folio, Double costoEnvio, Double precioExcento, Byte enviado, String guia, String empresa, Long creado, Long actualizado) {
        this.idVentas = idVentas;
        this.idUsuarios = idUsuarios;
        this.idPujas = idPujas;
        this.folio = folio;
        this.costoEnvio = costoEnvio;
        this.precioExcento = precioExcento;
        this.enviado = enviado;
        this.guia = guia;
        this.empresa = empresa;
        this.creado = creado;
        this.actualizado = actualizado;
    }

    @NonNull
    public Long getIdVentas() {
        return idVentas;
    }

    public Long getIdUsuarios() {
        return idUsuarios;
    }

    public Long getIdPujas() {
        return idPujas;
    }

    public String getFolio() {
        return folio;
    }

    public Double getCostoEnvio() {
        return costoEnvio;
    }

    public Double getPrecioExcento() {
        return precioExcento;
    }

    public Byte getEnviado() {
        return enviado;
    }

    public String getGuia() {
        return guia;
    }

    public String getEmpresa() {
        return empresa;
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
        Ventas ventas = (Ventas) o;
        return idVentas.equals(ventas.idVentas) &&
                Objects.equals(idUsuarios, ventas.idUsuarios) &&
                Objects.equals(idPujas, ventas.idPujas) &&
                Objects.equals(folio, ventas.folio) &&
                Objects.equals(costoEnvio, ventas.costoEnvio) &&
                Objects.equals(precioExcento, ventas.precioExcento) &&
                Objects.equals(enviado, ventas.enviado) &&
                Objects.equals(guia, ventas.guia) &&
                Objects.equals(empresa, ventas.empresa) &&
                Objects.equals(creado, ventas.creado) &&
                Objects.equals(actualizado, ventas.actualizado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVentas, idUsuarios, idPujas, folio, costoEnvio, precioExcento, enviado, guia, empresa, creado, actualizado);
    }
}
