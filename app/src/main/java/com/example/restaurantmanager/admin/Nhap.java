package com.example.restaurantmanager.admin;

public class Nhap {
    public String title;
    public String date;
    public String loai;
    public String gia;
    public String solg;
    public String tongtien;
    public  Integer type;

    public Nhap(String title, Integer type) {
        this.title = title;
        this.type = type;
    }

    public Nhap(String title, String date, String loai, String gia, String solg, String tongtien, Integer type) {
        this.title = title;
        this.date = date;
        this.loai = loai;
        this.gia = gia;
        this.solg = solg;
        this.tongtien = tongtien;
        this.type = type;
    }
}
