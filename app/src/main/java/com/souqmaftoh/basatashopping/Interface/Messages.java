package com.souqmaftoh.basatashopping.Interface;

public class Messages {
    private String from,message,type,messageID,to;

    public Messages(){

    }

    public Messages(String from, String message, String type){
        this.from=from;
        this.message=message;
        this.type=type;

    }

    public Messages(String messageID,String from,String to, String message, String type){
        this.messageID=messageID;
        this.to=to;
        this.from=from;
        this.message=message;
        this.type=type;

    }



    public String getFrom() {
        return from;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



}
