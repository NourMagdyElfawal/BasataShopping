package com.souqmaftoh.basatashopping.Models;

    import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class LoginDefault {

        @SerializedName("data")
        @Expose
        private LoginResponse data;
        @SerializedName("message")
        @Expose
        private String message;

        public LoginResponse getData() {
            return data;
        }

        public void setData(LoginResponse data) {
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }


