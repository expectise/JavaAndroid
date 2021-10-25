package com.multiventas.app.dto;

public class PujasNewDTO {
    private Long fecPriAbo;
    private String titulo;

    public PujasNewDTO(Long fecPriAbo, String titulo) {
        this.fecPriAbo = fecPriAbo;
        this.titulo = titulo;
    }

    public Long getFecPriAbo() {
        return fecPriAbo;
    }

    public String getTitulo() {
        return titulo;
    }

}
