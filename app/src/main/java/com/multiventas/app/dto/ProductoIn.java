package com.multiventas.app.dto;

import java.util.Date;

public class ProductoIn {

    private Long idProducto;

    private String titulo;

    private String descripcion;

    private Long creado;

    private Long actualizado;

    private double precio;

    private String codigoProducto;


    public ProductoIn(Long idProducto, String titulo, String descripcion, Long creado, Long actualizado, double precio, String codigoProducto) {
        this.idProducto = idProducto;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.creado = creado;
        this.actualizado = actualizado;
        this.precio = precio;
        this.codigoProducto = codigoProducto;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getCreado() {
        return creado;
    }

    public void setCreado(Long creado) {
        this.creado = creado;
    }

    public Long getActualizado() {
        return actualizado;
    }

    public void setActualizado(Long actualizado) {
        this.actualizado = actualizado;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
}
