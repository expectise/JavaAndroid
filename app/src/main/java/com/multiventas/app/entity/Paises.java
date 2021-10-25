package com.multiventas.app.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "Paises")
public class Paises implements Parcelable {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private Long idPais;

    private String pais;

    private double valorEnvio;

    private double precioExcento;

    private Long creado;

    private Long actualizado;

    public Paises(@NonNull Long idPais, String pais, double valorEnvio, double precioExcento, Long creado, Long actualizado) {
        this.idPais = idPais;
        this.pais = pais;
        this.valorEnvio = valorEnvio;
        this.precioExcento = precioExcento;
        this.creado = creado;
        this.actualizado = actualizado;
    }

    protected Paises(Parcel in) {
        if (in.readByte() == 0) {
            idPais = null;
        } else {
            idPais = in.readLong();
        }
        pais = in.readString();
        valorEnvio = in.readDouble();
        precioExcento = in.readDouble();
        if (in.readByte() == 0) {
            creado = null;
        } else {
            creado = in.readLong();
        }
        if (in.readByte() == 0) {
            actualizado = null;
        } else {
            actualizado = in.readLong();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (idPais == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(idPais);
        }
        dest.writeString(pais);
        dest.writeDouble(valorEnvio);
        dest.writeDouble(precioExcento);
        if (creado == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(creado);
        }
        if (actualizado == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(actualizado);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Paises> CREATOR = new Creator<Paises>() {
        @Override
        public Paises createFromParcel(Parcel in) {
            return new Paises(in);
        }

        @Override
        public Paises[] newArray(int size) {
            return new Paises[size];
        }
    };

    @NonNull
    public Long getIdPais() {
        return idPais;
    }

    public String getPais() {
        return pais;
    }

    public double getValorEnvio() {
        return valorEnvio;
    }

    public double getPrecioExcento() {
        return precioExcento;
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
        Paises paises = (Paises) o;
        return Double.compare(paises.valorEnvio, valorEnvio) == 0 &&
                Double.compare(paises.precioExcento, precioExcento) == 0 &&
                idPais.equals(paises.idPais) &&
                Objects.equals(pais, paises.pais) &&
                Objects.equals(creado, paises.creado) &&
                Objects.equals(actualizado, paises.actualizado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPais, pais, valorEnvio, precioExcento, creado, actualizado);
    }
}
