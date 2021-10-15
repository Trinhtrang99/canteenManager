package com.example.restaurantmanager.model;

public class Order {
    private String image;
    private String name;
    private Long price;
    private String time;
    private String day;

    public Order(String image, String name, Long price, String time, String day) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.time = time;
        this.day = day;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
