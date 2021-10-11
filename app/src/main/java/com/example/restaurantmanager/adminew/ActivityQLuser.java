package com.example.restaurantmanager.adminew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ActivityQluserBinding;

public class ActivityQLuser extends AppCompatActivity {
    ActivityQluserBinding binding;
    // dùng adapernay nhé
    AdapterUser adapterUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qluser);

    }
}