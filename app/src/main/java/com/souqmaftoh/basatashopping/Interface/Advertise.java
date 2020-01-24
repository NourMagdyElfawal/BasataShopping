package com.souqmaftoh.basatashopping.Interface;

public class Advertise {

    private String title,description,main_image;
    private int price,sub_category;

    public Advertise(String title, int price, String description, String main_image, int sub_category) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.main_image = main_image;
        this.sub_category = sub_category;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getMain_image() {
        return main_image;
    }

    public int getSub_category() {
        return sub_category;
    }
}
