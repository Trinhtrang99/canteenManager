package com.example.restaurantmanager.model;

import com.example.restaurantmanager.datafake.Food;

import java.util.ArrayList;

public interface ICallbackCheckBox {
    void listenCheckbox(Integer totalMoney, ArrayList<Food> foods);
}
