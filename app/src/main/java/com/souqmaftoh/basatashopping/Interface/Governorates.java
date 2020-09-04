package com.souqmaftoh.basatashopping.Interface;

public class Governorates {
    private String governorate;
    private int id;

    public Governorates(String governorate, int id) {
        this.governorate=governorate;
        this.id=id;
    }

    public String getGovernorate() {
        return governorate;
    }

    public void setGovernorate(String governorate) {
        this.governorate = governorate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

