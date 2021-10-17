package com.example.restaurantmanager.adminew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.restaurantmanager.BaseActivity;
import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ActivityFood2Binding;
import com.example.restaurantmanager.datafake.Food;
import com.example.restaurantmanager.fragmentmain.AdapterFood;
import com.example.restaurantmanager.ultils.Constants;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends BaseActivity implements AdapterFood.OnLongPress{

    private AdapterFood adapterFood;
    private ArrayList<Food> foods;
    private ActivityFood2Binding binding;
    private Integer type;
    private String typeFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_food2);

        foods = new ArrayList<>();

        if (getIntent() != null) {
            type = getIntent().getIntExtra("type", 0);
        }
        if (type != null) {
            if (type == 1) {
                binding.txtTitle.setText("Món ăn");
                typeFood = Constants.FOOD;
            } else if (type == 2) {
                binding.txtTitle.setText("CanTeen");
                typeFood = Constants.CANTEEN;
            } else {
                binding.txtTitle.setText("THỰC ĐƠN QUAN CHỨC");
                typeFood = Constants.OFFICIALS;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        getFoods();
    }

    private void getFoods () {
        showProgressDialog(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_COLLECTION_FOOD)
                .whereEqualTo(Constants.TYPE_FOOD, typeFood)
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

                    adapterFood = new AdapterFood(foods, this, true, this);
                    RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
                    binding.rvFood.setLayoutManager(layoutManager1);
                    binding.rvFood.setAdapter(adapterFood);

                    showProgressDialog(false);
                });
    }

    @Override
    public void onLongPress(String idFood) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_COLLECTION_FOOD).document(idFood).delete()
                .addOnCompleteListener(task -> {
                   getFoods();
                });
    }

    @Override
    public void onPressEdit(Food food) {
        Intent i = new Intent(this, ActivityAddFoof.class);
        i.putExtra("type", type);
        i.putExtra("food", food);
        startActivity(i);
    }
}