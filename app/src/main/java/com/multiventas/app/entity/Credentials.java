package com.multiventas.app.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "Credentials")
public class Credentials {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "id", index = true)
    private final double id;

    private final String jwt;

    private final String fireToken;

    private final String role;

    public Credentials(double id, String jwt, String fireToken, String role) {
        this.id = id;
        this.jwt = jwt;
        this.fireToken = fireToken;
        this.role = role;
    }

    public double getId() {
        return id;
    }

    public String getJwt() {
        return jwt;
    }

    public String getFireToken() {
        return fireToken;
    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credentials that = (Credentials) o;
        return Double.compare(that.id, id) == 0 &&
                Objects.equals(jwt, that.jwt) &&
                Objects.equals(fireToken, that.fireToken) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, jwt, fireToken, role);
    }
}
