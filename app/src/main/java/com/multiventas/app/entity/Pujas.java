package com.multiventas.app.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.Relation;


import java.util.List;
import java.util.Objects;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "Pujas", indices={
        @Index(value="idPujas", unique = true)
})
public class Pujas {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private final Long idPujas;

    private final String titulo;

    private final Long fechaInicio;

    private final Long fechaFinal;

    private final Long fecPriAbo;

    public Pujas(@NonNull Long idPujas, String titulo, Long fechaInicio, Long fechaFinal, Long fecPriAbo) {
        this.idPujas = idPujas;
        this.titulo = titulo;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.fecPriAbo = fecPriAbo;
    }

    @NonNull
    public Long getIdPujas() {
        return idPujas;
    }

    public String getTitulo() {
        return titulo;
    }

    public Long getFechaInicio() {
        return fechaInicio;
    }

    public Long getFechaFinal() {
        return fechaFinal;
    }

    public Long getFecPriAbo() {
        return fecPriAbo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pujas pujas = (Pujas) o;
        return idPujas.equals(pujas.idPujas) &&
                Objects.equals(titulo, pujas.titulo) &&
                Objects.equals(fechaInicio, pujas.fechaInicio) &&
                Objects.equals(fechaFinal, pujas.fechaFinal) &&
                Objects.equals(fecPriAbo, pujas.fecPriAbo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPujas, titulo, fechaInicio, fechaFinal, fecPriAbo);
    }
}
