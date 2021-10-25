package com.multiventas.app.dto;

public class Pais {

    private Long idPais;

    private String pais;

    private double valorEnvio;

    private double precioExcento;

    public Pais(Long idPais, String pais, double valorEnvio, double precioExcento) {
        this.idPais = idPais;
        this.pais = pais;
        this.valorEnvio = valorEnvio;
        this.precioExcento = precioExcento;
    }

    public Long getIdPais() {
        return idPais;
    }

    public void setIdPais(Long idPais) {
        this.idPais = idPais;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public double getValorEnvio() {
        return valorEnvio;
    }

    public void setValorEnvio(double valorEnvio) {
        this.valorEnvio = valorEnvio;
    }

    public double getPrecioExcento() {
        return precioExcento;
    }

    public void setPrecioExcento(double precioExcento) {
        this.precioExcento = precioExcento;
    }
}
