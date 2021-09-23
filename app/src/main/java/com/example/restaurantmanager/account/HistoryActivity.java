package com.example.restaurantmanager.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.restaurantmanager.BaseActivity;
import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ActivityHistoryBinding;
import com.example.restaurantmanager.ultils.Constants;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.kodmap.library.kmrecyclerviewstickyheader.KmHeaderItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends BaseActivity {
    private RecyclerViewAdapter adapter;
    private LinearLayoutManager layoutManager;
    private KmHeaderItemDecoration kmHeaderItemDecoration;
    private ActivityHistoryBinding binding;
    private ArrayList<String> foods;

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

        //getFoodManager();
    }

    private void initAdapter() {
        adapter = new RecyclerViewAdapter();
        layoutManager = new LinearLayoutManager(this);
        //   recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        binding.recyclerView.setLayoutManager(layoutManager);
        kmHeaderItemDecoration = new KmHeaderItemDecoration(adapter);
        binding.recyclerView.setAdapter(adapter);

    }

    List<Model> modelList;
    private void initData() {
        modelList = new ArrayList<>();
        for (Integer i = 1; i < 12; i++) {
            Model headerModel = new Model("Tháng " + i, ItemType.Header);
            modelList.add(headerModel);
            for (Integer j = 1; j < 31; j++) {
                String date = i + "/" + j;
                Model model = new Model("avc", "date", null, "", ItemType.Post);
                modelList.add(model);
            }
        }
        adapter.submitList(modelList);
    }

    private void getFoodManager() {
        showProgressDialog(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_COLLECTION_FOOD_MANAGER)
                .get()
                .addOnCompleteListener(task -> {
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        /*Model headerModel = new Model("Tháng " + queryDocumentSnapshot.getString(Constants.KEY_MONTH), ItemType.Header);
                        modelList.add(headerModel);

                        foods = new ArrayList<>();
                        foods.add(queryDocumentSnapshot.getString(Constants.KEY_NAME));
                        Model model = new Model("", "date", null, queryDocumentSnapshot.getString(Constants.KEY_TOTAL_MONEY), ItemType.Post);
                        modelList.add(model);*/
                    }
                    adapter.submitList(modelList);
                    showProgressDialog(false);
                });
    }
}