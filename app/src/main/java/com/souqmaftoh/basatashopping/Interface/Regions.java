package com.souqmaftoh.basatashopping.Interface;

public class Regions {
    private String region;
    private int id;

    public Regions(String region, int id) {
        this.region=region;
        this.id=id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


