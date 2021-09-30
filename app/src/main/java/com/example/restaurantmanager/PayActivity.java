package com.example.restaurantmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.Toast;

import com.example.restaurantmanager.adapter.PayAdapter;
import com.example.restaurantmanager.databinding.ActivityPayBinding;
import com.example.restaurantmanager.model.Pay;
import com.example.restaurantmanager.ultils.Constants;
import com.example.restaurantmanager.ultils.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PayActivity extends BaseActivity {

    private ActivityPayBinding binding;
    private PayAdapter adapter;
    private ArrayList<Pay> pays;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pay);

        pays = (ArrayList<Pay>) getIntent().getSerializableExtra("pays");
        preferenceManager = new PreferenceManager(getApplicationContext());
        adapter = new PayAdapter(pays);
        binding.rvPay.setAdapter(adapter);

        binding.txtTotalMoney.setText(MainActivity.totalMoney + " VND");

        binding.btnOrder.setOnClickListener(view -> {
            addOrder();
        });
    }

    private Integer count;
    private void addOrder () {
        count = 0;
        showProgressDialog(true);
        for (Pay pay : pays) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            HashMap<String, Object> foods = new HashMap<>();
            foods.put(Constants.KEY_NAME, pay.getName());
            foods.put(Constants.KEY_PRICE, pay.getPrice());
            foods.put(Constants.KEY_IMAGE, pay.getImage());
            foods.put(Constants.KEY_TOTAL_MONEY, MainActivity.totalMoney);
            foods.put(Constants.KEY_TIME,  new SimpleDateFormat("dd/MM/yyyy_HH:mm").format(new Date()));
            db.collection(Constants.KEY_COLLECTION_ORDER)
                    .document(preferenceManager.getString(Constants.KEY_PHONE_NUMBER))
                    .collection(Constants.KEY_COLLECTION_ORDER_DETAIL)
                    .add(foods)
                    .addOnSuccessListener(documentReference -> {
                        count++;
                        if (count == pays.size()) {
                            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            showProgressDialog(false);
                        }
                    });
        }
    }
}