package com.souqmaftoh.basatashopping.Interface;

import java.util.ArrayList;

public class User {
    private String token;
    private String name;
    private String email;
    private String image;
    private boolean is_merchant;
    private String market_name;
    private String address;
    private String lat;
    private String lng;
    private String phone;
    private String description;

    public User(String token, String name, String email, String image, boolean is_merchant, String market_name, String address, String lat, String lng, String phone, String description, ArrayList<Object> social_links) {
        this.token = token;
        this.name = name;
        this.email = email;
        this.image = image;
        this.is_merchant = is_merchant;
        this.market_name = market_name;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.phone = phone;
        this.description = description;
        this.social_links = social_links;
    }

    ArrayList<Object> social_links = new ArrayList<Object>();

    public User(String name, String email, String image, boolean is_merchant, String market_name, String address, String lat, String lng, String phone, String description) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.is_merchant = is_merchant;
        this.market_name = market_name;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.phone = phone;
        this.description = description;
    }

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

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public User(String email, String name, String market_name, String address, String lat, String phone, String description) {
        this.email = email;
        this.name = name;
        this.market_name = market_name;
        this.address = address;
        this.lat = lat;
        this.phone = phone;
        this.description = description;
    }


    // Getter Methods

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }

    public boolean getIs_merchant() {
        return is_merchant;
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

    public String getLng() {
        return lng;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }

    // Setter Methods

    public void setToken(String token) {
        this.token = token;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}




