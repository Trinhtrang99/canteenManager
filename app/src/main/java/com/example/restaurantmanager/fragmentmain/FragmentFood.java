package com.example.restaurantmanager.fragmentmain;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager.BaseFragment;
import com.example.restaurantmanager.R;
import com.example.restaurantmanager.admin.AdminActivity;
import com.example.restaurantmanager.admin.EditActivity;
import com.example.restaurantmanager.databinding.FramentfoodBinding;
import com.example.restaurantmanager.datafake.Food;
import com.example.restaurantmanager.datafake.ListData;
import com.example.restaurantmanager.ultils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FragmentFood extends BaseFragment implements AdapterFood.OnLongPress {
    private FramentfoodBinding binding;
    private AdapterFood adapterFollower;
    private ArrayList<Food> foods;
    private ArrayList<String> idFoods;
    private Integer count;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.framentfood, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity().getClass().equals(AdminActivity.class)) {

            idFoods = new ArrayList<>();
            binding.imgBack.setVisibility(View.VISIBLE);
            binding.btn.setVisibility(View.VISIBLE);

        } else {

//            adapterFollower = new AdapterFood(ListData.list(), getContext(),false);
//            RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
//            binding.rc.setLayoutManager(layoutManager1);
//            binding.rc.setAdapter(adapterFollower);
            binding.imgBack.setVisibility(View.GONE);
            binding.btn.setVisibility(View.GONE);
        }
        binding.imgBack.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStackImmediate();
        });
        binding.btn.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), EditActivity.class);
            i.putExtra("isEdit", false);
            i.putExtra(Constants.TYPE_FOOD, Constants.KEY_COLLECTION_FOOD);
            startActivity(i);
        });

        binding.imgdelete.setOnClickListener(view1 -> {
            count = 0;
            showProgressDialog(true);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            for (String idFood: idFoods) {
                count++;
                db.collection(Constants.KEY_COLLECTION_FOOD).document(idFood).delete()
                        .addOnCompleteListener(task -> {
                            if (count == idFoods.size()) {
                                binding.imgdelete.setVisibility(View.GONE);
                                showProgressDialog(false);
                                count = 0;
                                idFoods.clear();

                                getFoods();
                            }
                        });
            }

        });
    }

    private void getFoods () {
        showProgressDialog(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_COLLECTION_FOOD)
                .get()
                .addOnCompleteListener(task -> {
                    foods.clear();
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        Food food = new Food(
                                queryDocumentSnapshot.getString(Constants.KEY_NAME),
                                Integer.parseInt(queryDocumentSnapshot.getString(Constants.KEY_PRICE)),
                                queryDocumentSnapshot.getString(Constants.KEY_IMAGE)
                        );
                        food.setId(queryDocumentSnapshot.getId());
                        foods.add(food);
                    }

                    adapterFollower = new AdapterFood(foods, getContext(), true, this);
                    RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
                    binding.rc.setLayoutManager(layoutManager1);
                    binding.rc.setAdapter(adapterFollower);

                    showProgressDialog(false);
                });
    }

    @Override
    public void onLongPress(String idFood) {
        idFoods.add(idFood);
        binding.imgdelete.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPressEdit(Food food) {
        Intent i = new Intent(getContext(), EditActivity.class);
        i.putExtra("isEdit", true);
        i.putExtra("food", food);
        i.putExtra(Constants.TYPE_FOOD, Constants.KEY_COLLECTION_FOOD);
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.imgdelete.setVisibility(View.GONE);
        foods = new ArrayList<>();
        getFoods();
    }
}
