package com.multiventas.app.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;


@Entity(tableName = "Productos")
public class Productos implements Parcelable {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "idProducto", index = true)
    private Long idProducto;

    private String titulo;

    private double precio;

    private Long creado;

    private Long actualizado;

    private String codigoProducto;

    private String descripcion;

    public Productos(@NonNull Long idProducto, String titulo, double precio, Long creado, Long actualizado, String codigoProducto, String descripcion) {
        this.idProducto = idProducto;
        this.titulo = titulo;
        this.precio = precio;
        this.creado = creado;
        this.actualizado = actualizado;
        this.codigoProducto = codigoProducto;
        this.descripcion = descripcion;
    }

    protected Productos(Parcel in) {
        if (in.readByte() == 0) {
            idProducto = null;
        } else {
            idProducto = in.readLong();
        }
        titulo = in.readString();
        precio = in.readDouble();
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
        codigoProducto = in.readString();
        descripcion = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (idProducto == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(idProducto);
        }
        dest.writeString(titulo);
        dest.writeDouble(precio);
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
        dest.writeString(codigoProducto);
        dest.writeString(descripcion);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Productos> CREATOR = new Creator<Productos>() {
        @Override
        public Productos createFromParcel(Parcel in) {
            return new Productos(in);
        }

        @Override
        public Productos[] newArray(int size) {
            return new Productos[size];
        }
    };

    @NonNull
    public Long getIdProducto() {
        return idProducto;
    }

    public String getTitulo() {
        return titulo;
    }

    public double getPrecio() {
        return precio;
    }

    public Long getCreado() {
        return creado;
    }

    public Long getActualizado() {
        return actualizado;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Productos productos = (Productos) o;
        return Double.compare(productos.precio, precio) == 0 &&
                idProducto.equals(productos.idProducto) &&
                Objects.equals(titulo, productos.titulo) &&
                Objects.equals(creado, productos.creado) &&
                Objects.equals(actualizado, productos.actualizado) &&
                Objects.equals(codigoProducto, productos.codigoProducto) &&
                Objects.equals(descripcion, productos.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProducto, titulo, precio, creado, actualizado, codigoProducto, descripcion);
    }
}
