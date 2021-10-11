package com.example.restaurantmanager.mainnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.restaurantmanager.BaseActivity;
import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ActivityCanteenBinding;
import com.example.restaurantmanager.datafake.Food;
import com.example.restaurantmanager.fragmentmain.AdapterFood;
import com.example.restaurantmanager.ultils.Constants;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ActivityCanteen extends BaseActivity implements AdapterFood.OnLongPress {
    private ActivityCanteenBinding binding;
    private AdapterFood adapterFood;
    private ArrayList<Food> foods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_canteen);

        foods = new ArrayList<>();

        getFoods();
    }

    private void getFoods () {
        showProgressDialog(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_COLLECTION_FOOD)
                .whereEqualTo(Constants.TYPE_FOOD, Constants.CANTEEN)
                .get()
                .addOnCompleteListener(task -> {
                    foods.clear();
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        if (queryDocumentSnapshot.getString(Constants.KEY_PRICE) != null
                                && !queryDocumentSnapshot.getString(Constants.KEY_PRICE).equals("")) {

                            Food food = new Food(
                                    queryDocumentSnapshot.getString(Constants.KEY_NAME),
                                    Integer.parseInt(queryDocumentSnapshot.getString(Constants.KEY_PRICE)),
                                    queryDocumentSnapshot.getString(Constants.KEY_IMAGE)
                            );
                            food.setId(queryDocumentSnapshot.getId());
                            foods.add(food);
                        }
                    }

                    adapterFood = new AdapterFood(foods, this, false, this);
                    RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
                    binding.rc.setLayoutManager(layoutManager1);
                    binding.rc.setAdapter(adapterFood);

                    showProgressDialog(false);
                });
    }

    @Override
    public void onLongPress(String idFood) {

    }

    @Override
    public void onPressEdit(Food food) {

    }
}