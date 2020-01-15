package com.souqmaftoh.basatashopping.Models;

public class LoginResponse {

    private String message;
    private User data;

    public LoginResponse(String message, User data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public User getData() {
        return data;
    }
}
