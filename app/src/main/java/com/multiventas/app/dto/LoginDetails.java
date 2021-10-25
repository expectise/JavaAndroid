package com.multiventas.app.dto;

public class LoginDetails {
    private String bearer;
    private String role;

    public LoginDetails(String bearer, String role) {
        this.bearer = bearer;
        this.role = role;
    }

    public String getBearer() {
        return bearer;
    }

    public String getRole() {
        return role;
    }
}
