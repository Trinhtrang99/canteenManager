package com.example.restaurantmanager.mainnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.restaurantmanager.BaseActivity;
import com.example.restaurantmanager.CommentActivity;
import com.example.restaurantmanager.PayActivity;
import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ActivityFoodBinding;
import com.example.restaurantmanager.datafake.Food;
import com.example.restaurantmanager.fragmentmain.AdapterFood;
import com.example.restaurantmanager.model.ICallbackCheckBox;
import com.example.restaurantmanager.ultils.Constants;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ActivityFood extends BaseActivity implements AdapterFood.OnLongPress, ICallbackCheckBox {
    private ActivityFoodBinding binding;
    // dùng adaper này nhé
    private AdapterFood adapterFood;
    private ArrayList<Food> foods;
    public static ArrayList<Food> chooseFoods;
    private Integer totalMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_food);

        foods = new ArrayList<>();
        chooseFoods = new ArrayList<>();

        getFoods();

        binding.btnCart.setOnClickListener(view -> {
            Intent intent = new Intent(ActivityFood.this, ActivityCart.class);
            intent.putExtra(Constants.TYPE_FOOD, Constants.FOOD);
            intent.putExtra(Constants.KEY_TOTAL_MONEY, totalMoney);
            startActivity(intent);
        });
    }

    private void getFoods () {
        showProgressDialog(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_COLLECTION_FOOD)
                .whereEqualTo(Constants.TYPE_FOOD, Constants.FOOD)
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
                    adapterFood.setCallbackCheckBox(this::listenCheckbox);
                    RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
                    binding.rc.setLayoutManager(layoutManager1);
                    binding.rc.setAdapter(adapterFood);

                    showProgressDialog(false);
                });
    }

    @Override
    public void onLongPress(String idFood) {
        Intent intent = new Intent(ActivityFood.this, CommentActivity.class);
        intent.putExtra(Constants.KEY_ID_FOOD, idFood);
        startActivity(intent);
    }

    @Override
    public void onPressEdit(Food food) {

    }

    @Override
    public void listenCheckbox(Integer totalMoney, ArrayList<Food> foods) {
        chooseFoods.clear();
        chooseFoods.addAll(foods);
        this.totalMoney = totalMoney;
        binding.btnCart.setText("Giỏ hàng (" + totalMoney + "VND)");
    }
}