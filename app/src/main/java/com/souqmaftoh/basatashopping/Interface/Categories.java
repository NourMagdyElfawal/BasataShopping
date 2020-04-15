package com.souqmaftoh.basatashopping.Interface;

public class Categories {
    private String category;
    private int id;

    public Categories(String category, int id) {
        this.category=category;
        this.id=id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
