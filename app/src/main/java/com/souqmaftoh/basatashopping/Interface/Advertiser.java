package com.souqmaftoh.basatashopping.Interface;

import org.json.JSONArray;

public class Advertiser {

    private String name;
    private String image;
    private String market_name;
    private String address;
    private String lat;
    private String lng;
    private String phone;
    private String description;
    private String facebookUrl,instagramUrl,  youtubeUrl;
    private String firebase_id;

    public Advertiser(String name, String image, String market_name, String address, String lat, String lng, String phone, String description, String facebookUrl, String instagramUrl, String youtubeUrl, String firebase_id) {
        this.name = name;
        this.image = image;
        this.market_name = market_name;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.phone = phone;
        this.description = description;
        this.facebookUrl=facebookUrl;
        this.instagramUrl=instagramUrl;
        this.youtubeUrl=youtubeUrl;
        this.firebase_id=firebase_id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMarket_name() {
        return market_name;
    }

    public void setMarket_name(String market_name) {
        this.market_name = market_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getFirebase_id() {
        return firebase_id;
    }

    public void setFirebase_id(String firebase_id) {
        this.firebase_id = firebase_id;
    }
}
