package com.multiventas.app.dto;

import java.util.Objects;

public class Venta {
    private final Long idVentas;

    private final long idUsuarios;

    private final String nickname;

    private final double costoTotal;

    private final double costoEnvio;

    public Venta(Long idVentas, long idUsuarios, String nickname, double costoTotal, double costoEnvio) {
        this.idVentas = idVentas;
        this.idUsuarios = idUsuarios;
        this.nickname = nickname;
        this.costoTotal = costoTotal;
        this.costoEnvio = costoEnvio;
    }

    public Long getIdVentas() {
        return idVentas;
    }

    public long getIdUsuarios() {
        return idUsuarios;
    }

    public String getNickname() {
        return nickname;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public double getCostoEnvio() {
        return costoEnvio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venta venta = (Venta) o;
        return idUsuarios == venta.idUsuarios &&
                Double.compare(venta.costoTotal, costoTotal) == 0 &&
                Double.compare(venta.costoEnvio, costoEnvio) == 0 &&
                Objects.equals(idVentas, venta.idVentas) &&
                Objects.equals(nickname, venta.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVentas, idUsuarios, nickname, costoTotal, costoEnvio);
    }
}
