package com.multiventas.app.dto;

public class VentadetalladaOutDTO {

    private Long idVentaDetallada;

    private Long idVentas;

    private Double precio;

    private String titulo;

    private String descripcion;

    private String codigoProducto;

    private Long creado;

    private Long actualizado;

    public VentadetalladaOutDTO(Long idVentaDetallada, Long idVentas, Double precio, String titulo, String descripcion, String codigoProducto, Long creado, Long actualizado) {
        this.idVentaDetallada = idVentaDetallada;
        this.idVentas = idVentas;
        this.precio = precio;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.codigoProducto = codigoProducto;
        this.creado = creado;
        this.actualizado = actualizado;
    }

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

}
