package com.example.restaurantmanager.adminew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.admin.AdapterNhap;
import com.example.restaurantmanager.databinding.ActivityDoanhThuBinding;

public class ActivityDoanhThu extends AppCompatActivity {
    ActivityDoanhThuBinding binding;
    // dùng adapter này nhé cái này header là ngày bắt đầu và ngày kết thúc thôi
    AdapterNhap adapterNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_doanh_thu);
    }
}