package com.souqmaftoh.basatashopping.Interface;

import java.util.ArrayList;

public class Advertise {

    private String active,  offer,  title,  main_image,  price,  descriptionAdv;
    private String category,  sub_category_id,  sub_category,  is_favorite,  rate;
    private String rate_users,  item_condition,  status;
    private ArrayList<String> arr_images;

    public Advertise(String title, int price, String description, String main_image, int sub_category) {
    }

    public Advertise(String active, String offer, String title, String main_image, String price, String descriptionAdv,
                     String category, String sub_category_id, String sub_category, String is_favorite, String rate,
                     String rate_users, String item_condition, String status, ArrayList<String> arr_images) {

        this.active=active;
        this.offer=offer;
        this.title=title;
        this.main_image=main_image;
        this.price=price;
        this.descriptionAdv=descriptionAdv;
        this.category=category;
        this.sub_category_id=sub_category_id;
        this.sub_category=sub_category;
        this.is_favorite=is_favorite;
        this.rate=rate;
        this.rate_users=rate_users;
        this.item_condition=item_condition;
        this.status=status;
        this.arr_images=arr_images;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getOffer() {
        return offer;
    }

    public ArrayList<String> getArr_images() {
        return arr_images;
    }

    public void setArr_images(ArrayList<String> arr_images) {
        this.arr_images = arr_images;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMain_image() {
        return main_image;
    }

    public void setMain_image(String main_image) {
        this.main_image = main_image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescriptionAdv() {
        return descriptionAdv;
    }

    public void setDescriptionAdv(String descriptionAdv) {
        this.descriptionAdv = descriptionAdv;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(String sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public String getSub_category() {
        return sub_category;
    }

    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    public String getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(String is_favorite) {
        this.is_favorite = is_favorite;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRate_users() {
        return rate_users;
    }

    public void setRate_users(String rate_users) {
        this.rate_users = rate_users;
    }

    public String getItem_condition() {
        return item_condition;
    }

    public void setItem_condition(String item_condition) {
        this.item_condition = item_condition;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
