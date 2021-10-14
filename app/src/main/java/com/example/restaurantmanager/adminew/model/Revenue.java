package com.example.restaurantmanager.adminew.model;

public class Revenue {
    private String type;
    private String price;
    private String totalMoney;

    public Revenue(String type, String price, String totalMoney) {
        this.type = type;
        this.price = price;
        this.totalMoney = totalMoney;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }
}
