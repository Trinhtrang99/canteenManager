package com.example.restaurantmanager.mainnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.adminew.ActivityNhapHang;
import com.example.restaurantmanager.databinding.ActivityNhapHangBinding;

public class NhapActivity extends AppCompatActivity {
    ActivityNhapHangBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_nhap);
    }
}