package com.multiventas.app.dto;

public class AsignarProducto {
    private Long idProducto;
    private Long idUsuarios;

    public AsignarProducto(Long idProducto, Long idUsuarios) {
        this.idProducto = idProducto;
        this.idUsuarios = idUsuarios;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public Long getIdUsuarios() {
        return idUsuarios;
    }
}
