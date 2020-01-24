package com.souqmaftoh.basatashopping.Models;

import com.souqmaftoh.basatashopping.Interface.Message;

public class RegisterationUserResponse {
        private String data;
        Message MessageObject;


        // Getter Methods

        public String getData() {
            return data;
        }

        public Message getMessage() {
            return MessageObject;
        }

        // Setter Methods

        public void setData(String data) {
            this.data = data;
        }

        public void setMessage(Message messageObject) {
            this.MessageObject = messageObject;
        }
    }
