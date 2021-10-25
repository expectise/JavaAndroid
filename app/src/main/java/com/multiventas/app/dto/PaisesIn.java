package com.multiventas.app.dto;

public class PaisesIn {
    private Long idPais;

    private String pais;

    private double valorEnvio;

    private double precioExcento;

    private Boolean activo;

    private Long creado;

    private Long actualizado;

    public PaisesIn(Long idPais, String pais, double valorEnvio, double precioExcento, Boolean activo, Long creado, Long actualizado) {
        this.idPais = idPais;
        this.pais = pais;
        this.valorEnvio = valorEnvio;
        this.precioExcento = precioExcento;
        this.activo = activo;
        this.creado = creado;
        this.actualizado = actualizado;
    }

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

    public Boolean getActivo() {
        return activo;
    }

    public Long getCreado() {
        return creado;
    }

    public Long getActualizado() {
        return actualizado;
    }
}
