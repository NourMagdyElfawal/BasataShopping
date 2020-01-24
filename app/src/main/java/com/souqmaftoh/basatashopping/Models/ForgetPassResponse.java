package com.souqmaftoh.basatashopping.Models;

public class ForgetPassResponse {

    private String message;
    private String data;

    public ForgetPassResponse(String message, String data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }
}
