package com.example.restaurantmanager.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ActivityHistoryBinding;
import com.kodmap.library.kmrecyclerviewstickyheader.KmHeaderItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerViewAdapter adapter;
    private LinearLayoutManager layoutManager;
    private KmHeaderItemDecoration kmHeaderItemDecoration;
    ActivityHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history);
        initAdapter();
        initData();
        binding.chart.setOnClickListener(v -> {
            Intent i = new Intent(this, ChartActivity.class);
            startActivity(i);
        });
    }

    private void initAdapter() {
        adapter = new RecyclerViewAdapter();
        layoutManager = new LinearLayoutManager(this);
        //   recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        binding.recyclerView.setLayoutManager(layoutManager);
        kmHeaderItemDecoration = new KmHeaderItemDecoration(adapter);
        binding.recyclerView.setAdapter(adapter);

    }


    private void initData() {
        List<Model> modelList = new ArrayList<>();
        for (Integer i = 1; i < 12; i++) {
            Model headerModel = new Model("Tháng " + i, ItemType.Header);
            modelList.add(headerModel);
            for (Integer j = 1; j < 31; j++) {
                String date = i + "/" + j;
                Model model = new Model("Sáng ", date, "Canh(500.000), Cá (500.000), Cà (500.000)", "20.000đ", ItemType.Post);
                modelList.add(model);
            }
        }
        adapter.submitList(modelList);
    }
}