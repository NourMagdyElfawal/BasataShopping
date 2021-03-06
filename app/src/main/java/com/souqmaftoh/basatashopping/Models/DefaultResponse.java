package com.souqmaftoh.basatashopping.Models;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse {

    private String message;
    private String data;

    public DefaultResponse(String message, String data) {
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
