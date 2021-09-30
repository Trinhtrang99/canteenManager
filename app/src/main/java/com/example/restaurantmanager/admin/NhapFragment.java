package com.example.restaurantmanager.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.restaurantmanager.BaseFragment;
import com.example.restaurantmanager.MainActivity;
import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.FragmentNhapBinding;
import com.example.restaurantmanager.ultils.Constants;
import com.example.restaurantmanager.ultils.PreferenceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class NhapFragment extends BaseFragment {
    private FragmentNhapBinding binding;
    private String unit;
    private String type;
    private Integer totalMoney;
    private PreferenceManager preferenceManager;

    private String arr1[] = {
            "Thực phẩm",
            "Đồ ăn vặt"
    };
    private String arr2[] = {
            "kg",
            "cái",
            "chai"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nhap, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        preferenceManager = new PreferenceManager(getContext());

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arr1);
        arrayAdapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        binding.spin1.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter1 =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arr2);
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
                    Integer quantity = Integer.parseInt(binding.edtQuantity.getText().toString());
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
        foods.put(Constants.KEY_DAY, new SimpleDateFormat("dd").format(new Date()));
        foods.put(Constants.KEY_TIME, new SimpleDateFormat("HH").format(new Date()));

        String year = new SimpleDateFormat("yyyy").format(new Date());
        String month = new SimpleDateFormat("M").format(new Date());
        db.collection(Constants.KEY_COLLECTION_FOOD_MANAGER)
                .document(year)
                .collection(Constants.KEY_COLLECTION_MONTH)
                .document(month)
                .collection(Constants.KEY_FOOD_BY_DAY)
                .add(foods)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    showProgressDialog(false);
                });


        HashMap<String, Object> userAdmin = new HashMap<>();
        Integer surplus = Integer.parseInt(preferenceManager.getString(Constants.KEY_SURPLUS));
        surplus = surplus - totalMoney;
        userAdmin.put(Constants.KEY_SURPLUS, String.valueOf(surplus));
        db.collection(Constants.KEY_COLLECTION_ACCOUNT)
                .document(preferenceManager.getString(Constants.KEY_ID_USER))
                .update(userAdmin);
    }
}