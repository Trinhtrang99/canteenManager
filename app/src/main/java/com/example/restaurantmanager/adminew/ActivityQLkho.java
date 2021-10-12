package com.example.restaurantmanager.adminew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.restaurantmanager.BaseActivity;
import com.example.restaurantmanager.R;
import com.example.restaurantmanager.adminew.adaper.AdapterQLKho;
import com.example.restaurantmanager.adminew.model.KhoMOdel;
import com.example.restaurantmanager.databinding.ActivityQlkhoBinding;
import com.example.restaurantmanager.datafake.Food;
import com.example.restaurantmanager.fragmentmain.AdapterFood;
import com.example.restaurantmanager.ultils.Constants;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ActivityQLkho extends BaseActivity {
    private ActivityQlkhoBinding binding;
    // sử dụng adapter này nhé
    private AdapterQLKho adapterQLKho;
    private List<KhoMOdel> khoMOdels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qlkho);

        khoMOdels = new ArrayList<>();

        getStore();
    }

    private void getStore () {
        showProgressDialog(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_COLLECTION_FOOD_MANAGER)
                .get()
                .addOnCompleteListener(task -> {
                    khoMOdels.clear();
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        if (queryDocumentSnapshot.getString(Constants.KEY_UNIT).equals(Constants.TYPE_BOTTLE)
                                || queryDocumentSnapshot.getString(Constants.KEY_UNIT).equals(Constants.TYPE_FEMALE)) {

                            KhoMOdel khoMOdel = new KhoMOdel(
                                    queryDocumentSnapshot.getString(Constants.KEY_NAME),
                                    queryDocumentSnapshot.getString(Constants.KEY_QUANTITY)
                            );

                            khoMOdels.add(khoMOdel);

                        }
                    }

                    adapterQLKho = new AdapterQLKho(khoMOdels);
                    binding.rvStore.setAdapter(adapterQLKho);

                    showProgressDialog(false);
                });
    }
}