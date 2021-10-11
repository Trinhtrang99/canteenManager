package com.example.restaurantmanager.mainnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.account.HistoryActivity;
import com.example.restaurantmanager.adminew.ActivityDoiMk;
import com.example.restaurantmanager.databinding.ActivityMainNewBinding;

import jp.wasabeef.blurry.Blurry;

public class ActivityMainNew extends AppCompatActivity {
    ActivityMainNewBinding binding;
    // nếu là tk bthg là 1 , tk quan chức là 2
    int category = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_new);
        binding.rlfood.setOnClickListener(v -> {
            startActivity(new Intent(this, ActivityFood.class));
        });
        binding.rlcanteen.setOnClickListener(v -> {
            startActivity(new Intent(this, ActivityCanteen.class));
        });
        //nếu là tài khoản của quan chức thì cho hiển thị lên nhé đang set âne
        binding.rlquanchuc.setOnClickListener(v -> {
            startActivity(new Intent(this, ActivityThamKhoa.class));
        });
        binding.history.setOnClickListener(v -> {
            Intent i = new Intent(this, HistoryActivity.class);
            i.putExtra("category", category);
            startActivity(i);
        });
        binding.logout.setOnClickListener(v -> {

        });
        binding.changepass.setOnClickListener(v -> {
            Intent i = new Intent(this, ActivityDoiMk.class);
            // truyền gì sang thì truyền nhé
            i.putExtra("category", category);
            startActivity(i);
        });
    }
}