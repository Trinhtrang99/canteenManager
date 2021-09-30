package com.example.restaurantmanager.fragmentmain;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager.BaseFragment;
import com.example.restaurantmanager.PayActivity;
import com.example.restaurantmanager.R;
import com.example.restaurantmanager.admin.EditActivity;
import com.example.restaurantmanager.databinding.FragmentdrinkBinding;
import com.example.restaurantmanager.datafake.Food;
import com.example.restaurantmanager.datafake.ListData;
import com.example.restaurantmanager.model.ICallbackCheckBox;
import com.example.restaurantmanager.model.Pay;
import com.example.restaurantmanager.ultils.Constants;
import com.example.restaurantmanager.ultils.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FragmentDrink extends BaseFragment implements AdapterFood.OnLongPress {
    private FragmentdrinkBinding binding;
    private ArrayList<Food> drinks;
    private ArrayList<String> idFoods;
    private Integer count;
    private PreferenceManager preferenceManager;
    private boolean isAdmin;
    private ArrayList<Pay> pays;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragmentdrink, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        idFoods = new ArrayList<>();
        pays = new ArrayList<>();
        preferenceManager = new PreferenceManager(getContext());

        if (preferenceManager.getString(Constants.KEY_TYPE_USER).equals(Constants.TYPE_ADMIN)) {
            isAdmin = true;
            binding.btn.setVisibility(View.VISIBLE);
            binding.imgBack.setVisibility(View.VISIBLE);
        } else {
            binding.imgdelete.setVisibility(View.GONE);
            binding.btn.setVisibility(View.GONE);
            binding.imgBack.setVisibility(View.GONE);
            drinks = new ArrayList<>();
            getDrink();
        }
        binding.btn.setOnClickListener(view1 -> {
            Intent i = new Intent(getContext(), EditActivity.class);
            i.putExtra("isEdit", false);
            i.putExtra(Constants.TYPE_FOOD, Constants.KEY_COLLECTION_DRINK);
            startActivity(i);
        });

        binding.imgdelete.setOnClickListener(view1 -> {
            count = 0;
            showProgressDialog(true);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            for (String idFood: idFoods) {
                count++;
                db.collection(Constants.KEY_COLLECTION_DRINK).document(idFood).delete()
                        .addOnCompleteListener(task -> {
                            if (count == idFoods.size()) {
                                binding.imgdelete.setVisibility(View.GONE);
                                showProgressDialog(false);
                                count = 0;
                                idFoods.clear();

                                getDrink();
                            }
                        });
            }
        });
    }

    private void getDrink() {
        showProgressDialog(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_COLLECTION_DRINK)
                .get()
                .addOnCompleteListener(task -> {
                    drinks.clear();
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        Food food = new Food(
                                queryDocumentSnapshot.getString(Constants.KEY_NAME),
                                Integer.parseInt(queryDocumentSnapshot.getString(Constants.KEY_PRICE)),
                                queryDocumentSnapshot.getString(Constants.KEY_IMAGE)
                        );
                        food.setId(queryDocumentSnapshot.getId());
                        drinks.add(food);
                    }

                    AdapterFood adapterFollower = new AdapterFood(drinks, getContext(), isAdmin, this);
                    RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
                    binding.rc.setLayoutManager(layoutManager1);
                    binding.rc.setAdapter(adapterFollower);

                    showProgressDialog(false);
                });
    }

    @Override
    public void onLongPress(String idFood) {
        if (preferenceManager.getString(Constants.KEY_TYPE_USER).equals(Constants.TYPE_ADMIN)) {
            idFoods.add(idFood);
            binding.imgdelete.setVisibility(View.VISIBLE);
        } else {

        }
    }

    @Override
    public void onPressEdit(Food food) {
        if (preferenceManager.getString(Constants.KEY_TYPE_USER).equals(Constants.TYPE_ADMIN)) {
            Intent i = new Intent(getContext(), EditActivity.class);
            i.putExtra("isEdit", true);
            i.putExtra("food", food);
            i.putExtra(Constants.TYPE_FOOD, Constants.KEY_COLLECTION_DRINK);
            startActivity(i);
        } else {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (preferenceManager.getString(Constants.KEY_TYPE_USER).equals(Constants.TYPE_ADMIN)) {
            binding.imgdelete.setVisibility(View.GONE);
            drinks = new ArrayList<>();
            getDrink();
        }
    }
}
