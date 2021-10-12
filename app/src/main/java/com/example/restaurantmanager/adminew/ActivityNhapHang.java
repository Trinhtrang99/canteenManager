package com.example.restaurantmanager.adminew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.restaurantmanager.BaseActivity;
import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ActivityNhapHangBinding;
import com.example.restaurantmanager.ultils.Constants;
import com.example.restaurantmanager.ultils.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ActivityNhapHang extends BaseActivity {

    private ActivityNhapHangBinding binding;
    private String arr1[] = {
            "Thực phẩm",
            "Đồ ăn vặt"
    };
    private String arr2[] = {
            "kg",
            "cái",
            "chai"
    };
    private String type;
    private PreferenceManager preferenceManager;
    private String unit;
    private Integer totalMoney;

    // cái này bê bên kia sang hộ trang nhé (ok trang nhé :)) )
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_nhap_hang);

        preferenceManager = new PreferenceManager(this);

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr1);
        arrayAdapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        binding.spin1.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter1 =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr2);
        arrayAdapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        binding.spin2.setAdapter(arrayAdapter1);

        binding.spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                type = arr1[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        binding.spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                unit = arr2[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        binding.btnimg.setOnClickListener(view1 -> {
            addFoodManager();
        });

        binding.edtQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(binding.edtPrice.getText().toString())) {
                    Integer price = Integer.parseInt(binding.edtPrice.getText().toString());
                    Integer quantity = null;
                    if (!binding.edtQuantity.getText().toString().isEmpty()) {
                        quantity = Integer.parseInt(binding.edtQuantity.getText().toString());
                    }
                    if (quantity != null)
                        totalMoney = price * quantity;

                    binding.btnimg.setText( "Thêm(" + totalMoney + "VND)");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void addFoodManager () {
        showProgressDialog(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> foods = new HashMap<>();
        foods.put(Constants.KEY_NAME, binding.edtName.getText().toString());
        foods.put(Constants.KEY_PRICE, binding.edtPrice.getText().toString());
        foods.put(Constants.KEY_QUANTITY, binding.edtQuantity.getText().toString());
        foods.put(Constants.KEY_UNIT, String.valueOf(unit));
        foods.put(Constants.TYPE_FOOD, type);
        foods.put(Constants.KEY_TOTAL_MONEY, String.valueOf(totalMoney));
        foods.put(Constants.KEY_DAY, new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        db.collection(Constants.KEY_COLLECTION_FOOD_MANAGER)
                .add(foods)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(ActivityNhapHang.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    showProgressDialog(false);
                });
    }
}