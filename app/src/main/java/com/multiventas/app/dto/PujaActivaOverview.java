package com.multiventas.app.dto;

import java.util.Objects;

public class PujaActivaOverview {
    private Long idVentaDetallada;
    String nickname;
    String tituloProducto;
    String cProducto;

    public PujaActivaOverview(Long idVentaDetallada, String nickname, String tituloProducto, String cProducto) {
        this.idVentaDetallada = idVentaDetallada;
        this.nickname = nickname;
        this.tituloProducto = tituloProducto;
        this.cProducto = cProducto;
    }

    public Long getIdVentaDetallada() {
        return idVentaDetallada;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTituloProducto() {
        return tituloProducto;
    }

    public String getcProducto() {
        return cProducto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PujaActivaOverview that = (PujaActivaOverview) o;
        return idVentaDetallada.equals(that.idVentaDetallada) &&
                Objects.equals(nickname, that.nickname) &&
                Objects.equals(tituloProducto, that.tituloProducto) &&
                Objects.equals(cProducto, that.cProducto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVentaDetallada, nickname, tituloProducto, cProducto);
    }
}
