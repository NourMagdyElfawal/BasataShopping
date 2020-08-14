package com.souqmaftoh.basatashopping.Interface;

import android.net.Uri;

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
    private String facebookUrl,instagramUrl,  youtubeUrl;


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

    public User(String token,String name, String email, String image, boolean is_merchant, String market_name, String address, String lat, String lng, String phone, String description) {
        this.token=token;
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

    public User( String name,String email, String market_name, String address, String lat,String lng, String phone, String description) {
        this.name = name;
        this.email = email;
        this.market_name = market_name;
        this.address = address;
        this.lat = lat;
        this.lng=lng;
        this.phone = phone;
        this.description = description;
    }

    public User(String name,String email) {
        this.name = name;
        this.email = email;
    }

    public User(String name,String email,  String market_name, String address, String lat, String phone, String description) {
        this.name = name;
        this.email = email;
        this.market_name = market_name;
        this.address = address;
        this.lat = lat;
        this.phone = phone;
        this.description = description;
    }

    public User(String token, String name, String email, Boolean is_merchant, String market_name, String address, String lat, String lng, String phone, String description) {
        this.token = token;
        this.name = name;
        this.email = email;
        this.is_merchant = is_merchant;
        this.market_name = market_name;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.phone = phone;
        this.description = description;

    }

    public User(String image) {
        this.image = image;

    }

    public User(String token, String name, String email, Boolean is_merchant) {
        this.token = token;
        this.name = name;
        this.email = email;
        this.is_merchant = is_merchant;

    }

    public User(String token, String name, String email, String image, Boolean is_merchant, String market_name, String address, String lat, String lng, String phone, String description, String facebookUrl, String instagramUrl, String youtubeUrl) {
        this.token=token;
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
        this.facebookUrl=facebookUrl;
        this.instagramUrl=instagramUrl;
        this.youtubeUrl=youtubeUrl;


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

    public void setIs_merchant(boolean is_merchant) {
        this.is_merchant = is_merchant;
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

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getInstagramUrl() {
        return instagramUrl;
    }

    public void setInstagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }
}




