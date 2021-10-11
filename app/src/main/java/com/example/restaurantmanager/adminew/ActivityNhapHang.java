package com.example.restaurantmanager.adminew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ActivityNhapHangBinding;

public class ActivityNhapHang extends AppCompatActivity {
    ActivityNhapHangBinding binding;

    // cái này bê bên kia sang hộ trang nhé
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_nhap_hang);
    }
}