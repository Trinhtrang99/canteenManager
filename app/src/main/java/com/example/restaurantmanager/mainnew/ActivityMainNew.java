package com.example.restaurantmanager.mainnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.acc.LoginActivity;
import com.example.restaurantmanager.account.HistoryActivity;
import com.example.restaurantmanager.adminew.ActivityDoiMk;
import com.example.restaurantmanager.databinding.ActivityMainNewBinding;
import com.example.restaurantmanager.ultils.Constants;
import com.example.restaurantmanager.ultils.PreferenceManager;

import jp.wasabeef.blurry.Blurry;

public class ActivityMainNew extends AppCompatActivity {
    private ActivityMainNewBinding binding;
    // nếu là tk bthg là 1 , tk quan chức là 2
    private int category = 0;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_new);

        preferenceManager = new PreferenceManager(getApplicationContext());

        binding.txtSurplus.setText("số dư:" + preferenceManager.getString(Constants.KEY_SURPLUS) + "VND");

        binding.txtName.setText(preferenceManager.getString(Constants.KEY_NAME));
        binding.txtPhone.setText(preferenceManager.getString(Constants.KEY_PHONE_NUMBER));

        binding.rlfood.setOnClickListener(v -> {
            startActivity(new Intent(this, ActivityFood.class));
        });
        binding.rlcanteen.setOnClickListener(v -> {
            startActivity(new Intent(this, ActivityCanteen.class));
        });
        //nếu là tài khoản của quan chức thì cho hiển thị lên nhé đang set âne
        if (preferenceManager.getString(Constants.KEY_TYPE_USER).equals(Constants.TYPE_OFFICIAL)) {
            binding.rlquanchuc.setVisibility(View.VISIBLE);
        }
        binding.rlquanchuc.setOnClickListener(v -> {
            startActivity(new Intent(this, ActivityThamKhoa.class));
        });
        binding.history.setOnClickListener(v -> {
            Intent i = new Intent(this, HistoryActivity.class);
            i.putExtra("category", category);
            startActivity(i);
        });
        binding.logout.setOnClickListener(v -> {
            preferenceManager.clear();
            startActivity(new Intent(ActivityMainNew.this, LoginActivity.class));
        });
        binding.changepass.setOnClickListener(v -> {
            Intent i = new Intent(this, ActivityDoiMk.class);
            // truyền gì sang thì truyền nhé
            i.putExtra("category", category);
            startActivity(i);
        });
    }
}