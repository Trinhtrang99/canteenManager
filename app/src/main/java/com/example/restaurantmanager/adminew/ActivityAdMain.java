package com.example.restaurantmanager.adminew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ActivityAdMainBinding;
import com.example.restaurantmanager.databinding.ActivityDoanhThuBindingImpl;
import com.example.restaurantmanager.mainnew.ActivityThamKhoa;

public class ActivityAdMain extends AppCompatActivity {
    ActivityAdMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ad_main);
        binding.btn1.setOnClickListener(v -> {
            Intent i = new Intent(this,ActivityAddFoof.class);
            i.putExtra("type",1);
            startActivity(i);
        });
        binding.btn2.setOnClickListener(v -> {
            Intent i = new Intent(this,ActivityAddFoof.class);
            i.putExtra("type",2);
            startActivity(i);
        });
        binding.btn3.setOnClickListener(v -> {
            Intent i = new Intent(this,ActivityAddFoof.class);
            i.putExtra("type",3);
            startActivity(i);
        });
        binding.btn4.setOnClickListener(v -> {
            Intent i = new Intent(this,ActivityDoanhThu.class);
            startActivity(i);
        });
        binding.btn5.setOnClickListener(v -> {
            startActivity(new Intent(this, ActivityThamKhoa.class));
        });
        binding.btn6.setOnClickListener(v -> {
            Intent i = new Intent(this,ActivityNhapHang.class);
            startActivity(i);
        });
        binding.btn7.setOnClickListener(v -> {
            Intent i = new Intent(this,ActivityQLkho.class);
            startActivity(i);
        });
        binding.btn8.setOnClickListener(v -> {
            Intent i = new Intent(this,RateActivity.class);
            startActivity(i);
        });
        binding.btn9.setOnClickListener(v -> {
            Intent i = new Intent(this,ActivityQLuser.class);
            startActivity(i);
        });
        binding.btn10.setOnClickListener(v -> {
            Intent i = new Intent(this,ActivityDoiMk.class);
            startActivity(i);
        });


    }
}