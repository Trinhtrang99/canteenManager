package com.example.restaurantmanager.adminew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.adminew.adaper.AdapterQLKho;
import com.example.restaurantmanager.databinding.ActivityQlkhoBinding;

public class ActivityQLkho extends AppCompatActivity {
    ActivityQlkhoBinding binding;
    // sử dụng adapter này nhé
    AdapterQLKho adapterQLKho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qlkho);
    }
}