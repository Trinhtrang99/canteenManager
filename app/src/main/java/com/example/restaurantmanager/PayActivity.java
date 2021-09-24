package com.example.restaurantmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.restaurantmanager.adapter.PayAdapter;
import com.example.restaurantmanager.databinding.ActivityPayBinding;
import com.example.restaurantmanager.model.Pay;

import java.util.ArrayList;

public class PayActivity extends AppCompatActivity {

    private ActivityPayBinding binding;
    private PayAdapter adapter;
    private ArrayList<Pay> pays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pay);

        pays = (ArrayList<Pay>) getIntent().getSerializableExtra("pays");
        adapter = new PayAdapter(pays);
        binding.rvPay.setAdapter(adapter);
    }
}