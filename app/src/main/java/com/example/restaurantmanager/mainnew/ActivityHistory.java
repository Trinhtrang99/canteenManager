package com.example.restaurantmanager.mainnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.restaurantmanager.BaseActivity;
import com.example.restaurantmanager.R;
import com.example.restaurantmanager.admin.AdapterNhap;
import com.example.restaurantmanager.adminew.model.Revenue;
import com.example.restaurantmanager.databinding.ActivityHistory2Binding;
import com.example.restaurantmanager.ultils.Constants;
import com.example.restaurantmanager.ultils.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ActivityHistory extends BaseActivity {
    private ActivityHistory2Binding binding;
    private AdapterNhap adapterNhap;
    private List<Revenue> revenues;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history2);

        revenues = new ArrayList<>();
        preferenceManager = new PreferenceManager(getApplicationContext());

        binding.btnSearch.setOnClickListener(view -> {
            getOrders();
        });
    }

    private void getOrders () {
        showProgressDialog(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_COLLECTION_ORDER)
                .whereEqualTo(Constants.KEY_PHONE_NUMBER, preferenceManager.getString(Constants.KEY_PHONE_NUMBER))
                .get()
                .addOnCompleteListener(task -> {

                    revenues.clear();

                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        if (queryDocumentSnapshot.getString(Constants.KEY_DAY).compareTo(binding.edtDayStart.getText().toString()) >= 0
                                && queryDocumentSnapshot.getString(Constants.KEY_DAY).compareTo(binding.edtDayEnd.getText().toString()) <= 0) {

                            Revenue revenue = new Revenue (
                                    queryDocumentSnapshot.getString(Constants.KEY_NAME),
                                    queryDocumentSnapshot.getLong(Constants.KEY_PRICE).toString(),
                                    queryDocumentSnapshot.getLong(Constants.KEY_TOTAL_MONEY).toString()
                            );

                            revenues.add(revenue);
                        }
                    }

                    adapterNhap = new AdapterNhap(revenues);
                    binding.recyclerView.setAdapter(adapterNhap);

                    showProgressDialog(false);
                });
    }
}