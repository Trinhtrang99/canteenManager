package com.example.restaurantmanager.admin;

public class DoanhThu {
    public int type;
    public String title;
    public String data;
    public String nd;

    public DoanhThu(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public DoanhThu(int type, String title, String data, String nd) {
        this.type = type;
        this.title = title;
        this.data = data;
        this.nd = nd;
    }
}
