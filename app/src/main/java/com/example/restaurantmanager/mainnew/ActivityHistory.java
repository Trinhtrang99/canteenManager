package com.example.restaurantmanager.mainnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ActivityHistory2Binding;

public class ActivityHistory extends AppCompatActivity {
    // thằng này đung cái HistoryActivity nhé
    ActivityHistory2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history2);
    }
}