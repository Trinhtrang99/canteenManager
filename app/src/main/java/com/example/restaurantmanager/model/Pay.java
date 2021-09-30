package com.example.restaurantmanager.model;

import java.io.Serializable;

public class Pay implements Serializable {
    private String id;
    private String image;
    private String name;
    private String price;

    public Pay(String id, String image, String name, String price) {
        this.image = image;
        this.name = name;
        this.id = id;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPrice() {
        return price;
    }
}
