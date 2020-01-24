package com.souqmaftoh.basatashopping.Models;

public class RegistrationStoreResponse {

    String data;
    String message;


    public RegistrationStoreResponse(String data, String message) {
        this.data = data;
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}

