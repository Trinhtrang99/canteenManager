package com.example.restaurantmanager.datafake;

import java.io.Serializable;

public class Food implements Serializable {
    private String id;
    private String name;
    private int price;
    private String img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Food(String name, int price, String img) {
        this.name = name;
        this.price = price;
        this.img = img;
    }
}
