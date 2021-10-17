package com.example.restaurantmanager.adminew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.restaurantmanager.BaseActivity;
import com.example.restaurantmanager.R;
import com.example.restaurantmanager.admin.AdapterNhap;
import com.example.restaurantmanager.adminew.model.Revenue;
import com.example.restaurantmanager.databinding.ActivityDoanhThuBinding;
import com.example.restaurantmanager.datafake.Food;
import com.example.restaurantmanager.fragmentmain.AdapterFood;
import com.example.restaurantmanager.ultils.Constants;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ActivityDoanhThu extends BaseActivity {
    private ActivityDoanhThuBinding binding;
    private AdapterNhap adapterNhap;
    private List<Revenue> revenues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_doanh_thu);

        revenues = new ArrayList<>();

        binding.btnSearch.setOnClickListener(view -> getOrders());
    }

    private Long totalRevenue = 0L;
    private Long spendingMoney = 0L;
    private void getOrders () {
        totalRevenue = 0L;
        spendingMoney = 0L;
        showProgressDialog(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_COLLECTION_ORDER)
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

                            totalRevenue += queryDocumentSnapshot.getLong(Constants.KEY_PRICE);

                            revenues.add(revenue);
                        }
                    }

                    adapterNhap = new AdapterNhap(revenues);
                    binding.rc.setAdapter(adapterNhap);

                    binding.txtTotalRevenue.setText("Tổng thu : " + totalRevenue);

                    db.collection(Constants.KEY_COLLECTION_FOOD_MANAGER)
                            .get()
                            .addOnCompleteListener(task1 -> {
                                for (QueryDocumentSnapshot queryDocumentSnapshot : task1.getResult()) {

                                    spendingMoney += Long.parseLong(queryDocumentSnapshot.getString(Constants.KEY_TOTAL_MONEY));

                                    binding.txtSpendingMoney.setText("Tiền chi: " + spendingMoney);
                                }

                                showProgressDialog(false);

                                binding.txtTotalMoney.setText("Tổng thu nhập: " + (totalRevenue - spendingMoney));
                            });
                });
    }
}