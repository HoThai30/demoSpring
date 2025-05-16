package com.example.vmall.dto.Respone;

public class LoginRespone {
    private String message;
    private String token;

    public LoginRespone(String message, String token) {
        this.message = message;
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
