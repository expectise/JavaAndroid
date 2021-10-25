package com.multiventas.app.dto;

import java.util.List;

public class VentasOutDTO {
    private Long idVentas;

    private Long idUsuarios;

    private Long idPuja;

    private String folio;

    private Double costoEnvio;

    private Double precioExcento;

    private Byte enviado;

    private String guia;

    private String empresa;

    private Long creado;

    private Long actualizado;

    private List<VentadetalladaOutDTO> ventadetalladaOutDTO;

    public VentasOutDTO(Long idVentas, Long idUsuarios, Long idPuja, String folio, Double costoEnvio, Double precioExcento, Byte enviado, String guia, String empresa, Long creado, Long actualizado, List<VentadetalladaOutDTO> ventadetalladaOutDTO) {
        this.idVentas = idVentas;
        this.idUsuarios = idUsuarios;
        this.idPuja = idPuja;
        this.folio = folio;
        this.costoEnvio = costoEnvio;
        this.precioExcento = precioExcento;
        this.enviado = enviado;
        this.guia = guia;
        this.empresa = empresa;
        this.creado = creado;
        this.actualizado = actualizado;
        this.ventadetalladaOutDTO = ventadetalladaOutDTO;
    }

    public Long getIdVentas() {
        return idVentas;
    }

    public Long getIdUsuarios() {
        return idUsuarios;
    }

    public Long getIdPuja() {
        return idPuja;
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

    public List<VentadetalladaOutDTO> getVentadetalladaOutDTO() {
        return ventadetalladaOutDTO;
    }

}
