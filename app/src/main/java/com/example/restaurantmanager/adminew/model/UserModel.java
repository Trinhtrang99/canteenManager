package com.example.restaurantmanager.adminew.model;

import java.util.List;

public class UserModel {
    public  String name;
    public  String du;
    public   List<Pay> list;

    public UserModel(String name, String du, List<Pay> list) {
        this.name = name;
        this.du = du;
        this.list = list;
    }
}
