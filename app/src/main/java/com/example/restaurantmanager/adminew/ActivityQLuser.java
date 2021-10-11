package com.example.restaurantmanager.adminew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.restaurantmanager.BaseActivity;
import com.example.restaurantmanager.R;
import com.example.restaurantmanager.adminew.model.UserModel;
import com.example.restaurantmanager.databinding.ActivityQluserBinding;
import com.example.restaurantmanager.datafake.Food;
import com.example.restaurantmanager.fragmentmain.AdapterFood;
import com.example.restaurantmanager.ultils.Constants;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ActivityQLuser extends BaseActivity {
    private ActivityQluserBinding binding;
    // dùng adapernay nhé
    private AdapterUser adapterUser;
    private ArrayList<UserModel> userModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qluser);

        userModels = new ArrayList<>();

        binding.search.setOnClickListener(view -> {
            getUser();
        });
    }

    private void getUser () {
        showProgressDialog(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_COLLECTION_ACCOUNT)
                .get()
                .addOnCompleteListener(task -> {
                    userModels.clear();
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        if (queryDocumentSnapshot.getString(Constants.KEY_TYPE_USER).equals(Constants.TYPE_ADMIN)) {
                            continue;
                        }

                        if (queryDocumentSnapshot.getString(Constants.KEY_NAME).contains(binding.edtSearch.getText().toString())) {
                            UserModel userModel = new UserModel(
                                    queryDocumentSnapshot.getString(Constants.KEY_NAME),
                                    queryDocumentSnapshot.getString(Constants.KEY_SURPLUS)
                            );

                            userModels.add(userModel);
                        }
                    }

                    adapterUser = new AdapterUser(userModels);
                    binding.rc.setAdapter(adapterUser);

                    showProgressDialog(false);
                });
    }
}