package com.multiventas.app.dto;

import java.util.List;

public class PujasIn {
    private Long idPujas;

    private String titulo;

    private Long fechaInicio;

    private Long fechaFinal;

    private Long fecPriAbo;

    private List<VentasOutDTO> ventasOutDTO;

    public PujasIn(Long idPujas, String titulo, Long fechaInicio, Long fechaFinal, Long fecPriAbo, List<VentasOutDTO> ventasOutDTO) {
        this.idPujas = idPujas;
        this.titulo = titulo;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.fecPriAbo = fecPriAbo;
        this.ventasOutDTO = ventasOutDTO;
    }

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

    public List<VentasOutDTO> getVentasOutDTO() {
        return ventasOutDTO;
    }

}
