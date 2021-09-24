package com.example.restaurantmanager.model;

import java.io.Serializable;

public class Pay implements Serializable {
    private String id;
    private String image;
    private String name;

    public Pay(String id, String image, String name) {
        this.image = image;
        this.name = name;
        this.id = id;
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
}
