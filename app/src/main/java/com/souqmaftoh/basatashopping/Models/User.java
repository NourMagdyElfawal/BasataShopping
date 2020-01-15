package com.souqmaftoh.basatashopping.Models;

public class User {
    private String email,name,image,market_name,address,lat,phone,description;

    public User(String email, String name, String image, String market_name, String address, String lat, String phone, String description) {
        this.email = email;
        this.name = name;
        this.image = image;
        this.market_name = market_name;
        this.address = address;
        this.lat = lat;
        this.phone = phone;
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public String getMarket_name() {
        return market_name;
    }

    public String getAddress() {
        return address;
    }

    public String getLat() {
        return lat;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}

