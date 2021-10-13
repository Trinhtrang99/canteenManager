package com.example.restaurantmanager.fragmentmain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ActivityHistory3Binding;

public class ActivityHistory2 extends AppCompatActivity {
    AdapterHistory adapterHistory;
    ActivityHistory3Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history3);
    }
}