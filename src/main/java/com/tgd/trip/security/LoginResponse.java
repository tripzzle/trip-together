package com.tgd.trip.security;

public class LoginResponse{
    private String accessToken;

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
