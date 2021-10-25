package com.multiventas.app.dto;

public class Producto {
    private Long idProducto;

    private String titulo;

    private String descripcion;

    private Double precio;

    private String codigoProducto;

    public Producto(Long idProducto, String titulo, String descripcion, Double precio, String codigoProducto) {
        this.idProducto = idProducto;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.codigoProducto = codigoProducto;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
}
