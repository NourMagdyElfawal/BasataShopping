package com.souqmaftoh.basatashopping.Interface;

public class SubCategory {
    private String subCategory,imageUrl;
    private int id;

    public SubCategory(String subCategory, int id) {
        this.subCategory=subCategory;
        this.id=id;
    }

    public SubCategory(String subCategory, int id, String imageUrl) {
        this.subCategory=subCategory;
        this.id=id;
        this.imageUrl=imageUrl;


    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public int getSubCategoryId() {
        return id;
    }

    public void setSubCategoryId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
