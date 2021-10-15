package com.example.restaurantmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.restaurantmanager.adapter.AdapterOrder;
import com.example.restaurantmanager.admin.AdapterNhap;
import com.example.restaurantmanager.adminew.model.Revenue;
import com.example.restaurantmanager.databinding.ActivityOrderBinding;
import com.example.restaurantmanager.model.Order;
import com.example.restaurantmanager.ultils.Constants;
import com.example.restaurantmanager.ultils.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends BaseActivity {

    private AdapterOrder adapter;
    private List<Order> orders;
    private PreferenceManager preferenceManager;
    private ActivityOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_order);

        orders = new ArrayList<>();
        preferenceManager = new PreferenceManager(getApplicationContext());

        getOrders();
    }

    private void getOrders () {
        showProgressDialog(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_COLLECTION_ORDER)
                .whereEqualTo(Constants.KEY_PHONE_NUMBER, preferenceManager.getString(Constants.KEY_PHONE_NUMBER))
                .get()
                .addOnCompleteListener(task -> {

                    orders.clear();

                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        Order order = new Order(
                                queryDocumentSnapshot.getString(Constants.KEY_IMAGE),
                                queryDocumentSnapshot.getString(Constants.KEY_NAME),
                                queryDocumentSnapshot.getLong(Constants.KEY_PRICE),
                                queryDocumentSnapshot.getString(Constants.KEY_TIME),
                                queryDocumentSnapshot.getString(Constants.KEY_DAY)
                        );

                        orders.add(order);
                    }

                    adapter = new AdapterOrder(orders);
                    binding.rvOrder.setAdapter(adapter);

                    showProgressDialog(false);
                });
    }

}