package com.example.restaurantmanager.account;

import java.util.ArrayList;

public class Model {
    String title;
    String date;
    ArrayList<String> detail;
    String tongtien;
    Integer type;

    public Model(String title, String date, ArrayList<String> detail, String tongtien, Integer type) {
        this.title = title;
        this.date = date;
        this.detail = detail;
        this.tongtien = tongtien;
        this.type = type;
    }


    public Model(String name, Integer type) {
        this.title = name;
        this.type = type;
    }

    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
