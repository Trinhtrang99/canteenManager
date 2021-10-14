package com.example.restaurantmanager.mainnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.restaurantmanager.BaseActivity;
import com.example.restaurantmanager.MainActivity;
import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ActivityCartBinding;
import com.example.restaurantmanager.datafake.Food;
import com.example.restaurantmanager.fragmentmain.AdapterFood;
import com.example.restaurantmanager.model.Pay;
import com.example.restaurantmanager.ultils.Constants;
import com.example.restaurantmanager.ultils.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ActivityCart extends BaseActivity implements AdapterFood.OnLongPress {
    private ActivityCartBinding binding;
    private AdapterFood adapterFood;
    private ArrayList<Food> foods;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this,R.layout.activity_cart);
       preferenceManager = new PreferenceManager(getApplicationContext());

       foods = new ArrayList<>();

       if (getIntent().getStringExtra(Constants.TYPE_FOOD).equals(Constants.FOOD)) {
           foods.addAll(ActivityFood.chooseFoods);
       }

        if (getIntent().getStringExtra(Constants.TYPE_FOOD).equals(Constants.CANTEEN)) {
            foods.addAll(ActivityCanteen.chooseFoods);
        }

        adapterFood = new AdapterFood(foods, this, false, this);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);
        binding.rc.setLayoutManager(layoutManager1);
        binding.rc.setAdapter(adapterFood);

        binding.btnOrder.setOnClickListener(view -> addOrder());
    }

    private Integer count = 0;
    private void addOrder () {
        count = 0;
        showProgressDialog(true);
        for (Food food : foods) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            HashMap<String, Object> foods = new HashMap<>();
            foods.put(Constants.KEY_NAME, food.getName());
            foods.put(Constants.KEY_PRICE, food.getPrice());
            foods.put(Constants.KEY_IMAGE, food.getImg());
            foods.put(Constants.KEY_PHONE_NUMBER, preferenceManager.getString(Constants.KEY_PHONE_NUMBER));
            foods.put(Constants.KEY_TOTAL_MONEY, getIntent().getIntExtra(Constants.KEY_TOTAL_MONEY, 0));
            foods.put(Constants.KEY_DAY, new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
            foods.put(Constants.KEY_TIME, new SimpleDateFormat("HH:mm").format(new Date()));
            db.collection(Constants.KEY_COLLECTION_ORDER)
                    .add(foods)
                    .addOnSuccessListener(documentReference -> {
                        count++;
                        if (count == this.foods.size()) {
                            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            showProgressDialog(false);
                            onBackPressed();
                        }
                    });
        }
    }

    @Override
    public void onLongPress(String idFood) {

    }

    @Override
    public void onPressEdit(Food food) {

    }
}