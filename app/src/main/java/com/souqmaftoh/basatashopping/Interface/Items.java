package com.souqmaftoh.basatashopping.Interface;

import com.google.gson.annotations.SerializedName;

public class Items {

        @SerializedName("ad_key")
        private String mAdKey;
        @SerializedName("imageUrl")
        private String mImageUrl;
        @SerializedName("info")
        private String mInfo;
        @SerializedName("subTitle")
        private String mSubTitle;
        @SerializedName("title")
        private String mTitle;

    @SerializedName("price")
    private int price;



    @SerializedName("item_condition")
        private String item_condition;
        @SerializedName("offer")
        private String offer;
        @SerializedName("sub_category")
        private String sub_category;
        @SerializedName("active")
        private String active;
        @SerializedName("status")
        private String status;
    @SerializedName("owner_name")
    private String owner_name;
    @SerializedName("description")
    private String description;



    public Items(String mImageUrl, String mInfo, String mSubTitle, String mTitle) {
            this.mImageUrl = mImageUrl;
            this.mInfo = mInfo;
            this.mSubTitle = mSubTitle;
            this.mTitle = mTitle;
        }


    public Items(String mAdKey, String mImageUrl, String item_condition, String mTitle, String price, String offer, String category
            , String sub_category, String active, String status) {
        this.mAdKey=mAdKey;
        this.mImageUrl = mImageUrl;
        this.item_condition=item_condition;
        this.mTitle = mTitle;
        this.mInfo = price;
        this.offer=offer;
        this.mSubTitle = category;
        this.sub_category=sub_category;
        this.active=active;
        this.status=status;

    }
    public Items(String mAdKey, String mImageUrl, String item_condition, String mTitle, int price,
                 String sub_category, String status,String owner_name,String description) {
        this.mAdKey=mAdKey;
        this.mImageUrl = mImageUrl;
        this.item_condition=item_condition;
        this.mTitle = mTitle;
        this.price = price;
        this.mSubTitle = owner_name;
        this.sub_category=sub_category;
        this.status=status;
        this.description=description;


    }


    public String getmAdKey() {
            return mAdKey;
        }

        public void setmAdKey(String mAdKey) {
            this.mAdKey = mAdKey;
        }

        public String getImageUrl() {
                return mImageUrl;
            }

        public void setImageUrl(String imageUrl) {
            mImageUrl = imageUrl;
        }

        public String getInfo() {
            return mInfo;
        }

        public void setInfo(String info) {
            mInfo = info;
        }

        public String getSubTitle() {
            return mSubTitle;
        }

        public void setSubTitle(String subTitle) {
            mSubTitle = subTitle;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(String title) {
            mTitle = title;
        }


        public String getItem_condition() {
            return item_condition;
        }

        public void setItem_condition(String item_condition) {
            this.item_condition = item_condition;
        }

        public String getOffer() {
            return offer;
        }

        public void setOffer(String offer) {
            this.offer = offer;
        }

        public String getSub_category() {
            return sub_category;
        }

        public void setSub_category(String sub_category) {
            this.sub_category = sub_category;
        }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }
}
