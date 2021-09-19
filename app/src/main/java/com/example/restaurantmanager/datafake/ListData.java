package com.example.restaurantmanager.datafake;

import java.util.ArrayList;

public class ListData {
    public static ArrayList<Food> list() {
        ArrayList<Food> list = new ArrayList<>();
        list.add(new Food("Thịt kho", 5000, "https://afamilycdn.com/150157425591193600/2021/4/1/thit-kho-tau-1-1617264490364992329242.jpeg"));
        list.add(new Food("rau luộc", 5000, "https://afamilycdn.com/150157425591193600/2021/4/1/thit-kho-tau-1-1617264490364992329242.jpeg"));
        list.add(new Food("rau muống xào", 5000, "https://anh.eva.vn/upload/3-2018/images/2018-07-30/ava-1532942175-858-width640height480.jpg"));
        list.add(new Food("Cá kho", 5000, "https://anh.eva.vn/upload/3-2018/images/2018-07-30/ava-1532942175-858-width640height480.jpg"));
        list.add(new Food("Thịt kho trứng", 5000, "https://anh.eva.vn/upload/3-2018/images/2018-07-30/ava-1532942175-858-width640height480.jpg"));
        list.add(new Food("Rau cải luộc", 5000, "https://anh.eva.vn/upload/3-2018/images/2018-07-30/ava-1532942175-858-width640height480.jpg"));
        list.add(new Food("Bắp giò ", 5000, "https://anh.eva.vn/upload/3-2018/images/2018-07-30/ava-1532942175-858-width640height480.jpg"));
        list.add(new Food("Gio", 5000, "https://anh.eva.vn/upload/3-2018/images/2018-07-30/ava-1532942175-858-width640height480.jpg"));
        list.add(new Food("Canh chua", 5000, "https://anh.eva.vn/upload/3-2018/images/2018-07-30/ava-1532942175-858-width640height480.jpg"));
        list.add(new Food("Chả cá", 5000, "https://anh.eva.vn/upload/3-2018/images/2018-07-30/ava-1532942175-858-width640height480.jpg"));
        list.add(new Food("Thịt kho đậu", 5000, "https://anh.eva.vn/upload/3-2018/images/2018-07-30/ava-1532942175-858-width640height480.jpg"));
        list.add(new Food("Thịt", 5000, "https://anh.eva.vn/upload/3-2018/images/2018-07-30/ava-1532942175-858-width640height480.jpg"));
        return list;
    }
    public static ArrayList<Food> listDrink() {
        ArrayList<Food> list = new ArrayList<>();
        list.add(new Food("pessi", 15000, "https://afamilycdn.com/150157425591193600/2021/4/1/thit-kho-tau-1-1617264490364992329242.jpeg"));
        list.add(new Food("caca", 15000, "https://afamilycdn.com/150157425591193600/2021/4/1/thit-kho-tau-1-1617264490364992329242.jpeg"));
        list.add(new Food("nước đậu", 15000, "https://anh.eva.vn/upload/3-2018/images/2018-07-30/ava-1532942175-858-width640height480.jpg"));
        list.add(new Food("sữa chua", 15000, "https://anh.eva.vn/upload/3-2018/images/2018-07-30/ava-1532942175-858-width640height480.jpg"));
        list.add(new Food("sữa đậu", 5000, "https://anh.eva.vn/upload/3-2018/images/2018-07-30/ava-1532942175-858-width640height480.jpg"));
        list.add(new Food("Nước lọc", 5000, "https://anh.eva.vn/upload/3-2018/images/2018-07-30/ava-1532942175-858-width640height480.jpg"));
        return list;
    }
}
