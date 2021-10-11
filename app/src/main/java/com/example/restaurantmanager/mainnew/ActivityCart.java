package com.example.restaurantmanager.mainnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ActivityCartBinding;
import com.example.restaurantmanager.fragmentmain.AdapterFood;

public class ActivityCart extends AppCompatActivity {
    ActivityCartBinding binding;
    AdapterFood adapterFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil. setContentView(this,R.layout.activity_cart);
    }
}