package com.example.restaurantmanager.adminew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.restaurantmanager.BaseActivity;
import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ActivityDoiMkBinding;
import com.example.restaurantmanager.mainnew.ActivityMainNew;
import com.example.restaurantmanager.ultils.Constants;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.restaurantmanager.ultils.PreferenceManager;

public class ActivityDoiMk extends BaseActivity {
    private ActivityDoiMkBinding binding;
    private com.example.restaurantmanager.ultils.PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_doi_mk);

        preferenceManager = new PreferenceManager(getApplicationContext());

        if (binding.edtOldPassword.getText().toString().equals(preferenceManager.getString(Constants.KEY_PASSWORD))) {
            if (binding.edtNewPassword.getText().toString().equals(binding.edtNewPasswordAgain.getText().toString())) {
                getUserLogin();
            }
        }

        binding.btnOk.setOnClickListener(view -> getUserLogin());
    }

    private void getUserLogin () {
        showProgressDialog(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_ACCOUNT)
                .document(preferenceManager.getString(Constants.KEY_ID_USER))
                .update(Constants.KEY_PASSWORD, binding.edtNewPassword.getText().toString())
                .addOnCompleteListener( task -> {
                    showProgressDialog(false);
                    finish();
                });
    }
}