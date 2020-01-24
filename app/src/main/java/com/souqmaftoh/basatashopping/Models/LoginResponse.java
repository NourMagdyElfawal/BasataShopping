package com.souqmaftoh.basatashopping.Models;

import com.souqmaftoh.basatashopping.Interface.User;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class LoginResponse {

        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("is_merchant")
        @Expose
        private Boolean isMerchant;
        @SerializedName("market_name")
        @Expose
        private String marketName;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("lng")
        @Expose
        private String lng;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("social_links")
        @Expose
        private List<Object> socialLinks = null;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Boolean getIsMerchant() {
            return isMerchant;
        }

        public void setIsMerchant(Boolean isMerchant) {
            this.isMerchant = isMerchant;
        }

        public String getMarketName() {
            return marketName;
        }

        public void setMarketName(String marketName) {
            this.marketName = marketName;
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

        public List<Object> getSocialLinks() {
            return socialLinks;
        }

        public void setSocialLinks(List<Object> socialLinks) {
            this.socialLinks = socialLinks;
        }

    }