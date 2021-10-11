package com.example.restaurantmanager.adminew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ActivityAddFoofBinding;

public class ActivityAddFoof extends AppCompatActivity {
    ActivityAddFoofBinding binding;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_foof);
        if (getIntent() != null) {
            type = getIntent().getIntExtra("type", 0);
        }
        if (type == 1) {
            binding.txtTitle.setText("Món ăn");
        } else if (type == 2) {
            binding.txtTitle.setText("CanTeen");
        } else {
            binding.txtTitle.setText("THỰC ĐƠN QUAN CHỨC");
        }
    }
}