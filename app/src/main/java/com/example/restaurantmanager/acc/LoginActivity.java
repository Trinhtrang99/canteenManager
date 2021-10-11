package com.example.restaurantmanager.acc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.restaurantmanager.BaseActivity;
import com.example.restaurantmanager.MainActivity;
import com.example.restaurantmanager.R;
import com.example.restaurantmanager.admin.AdminActivity;
import com.example.restaurantmanager.adminew.ActivityAdMain;
import com.example.restaurantmanager.databinding.ActivityLoginBinding;
import com.example.restaurantmanager.mainnew.ActivityMainNew;
import com.example.restaurantmanager.ultils.Constants;
import com.example.restaurantmanager.ultils.PreferenceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        preferenceManager = new PreferenceManager(getApplicationContext());

        if (preferenceManager.getBoolean(Constants.KEY_IS_REMEMBER_PASSWORD)) {
            if (preferenceManager.getString(Constants.KEY_TYPE_USER).equals(Constants.TYPE_ADMIN)) {
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

        binding.tvregis.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisActivity.class));
        });

        binding.btnlogin.setOnClickListener(view -> {
            signIn();
        });

        binding.chkRememberMe.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferenceManager.putBoolean(Constants.KEY_IS_REMEMBER_PASSWORD, isChecked);
        });

        binding.forgotBtn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
        });
    }

    private void signIn () {
        showProgressDialog(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_ACCOUNT)
                .whereEqualTo(Constants.KEY_PHONE_NUMBER, binding.edtSdt.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD, binding.edtMk.getText().toString())
                .get()
                .addOnCompleteListener( task -> {
                    showProgressDialog(false);
                    if (task.isSuccessful() && task.getResult() != null
                            && task.getResult().getDocuments().size() > 0) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        preferenceManager.putString(Constants.KEY_PHONE_NUMBER, documentSnapshot.getString(Constants.KEY_PHONE_NUMBER));
                        preferenceManager.putString(Constants.KEY_PASSWORD, documentSnapshot.getString(Constants.KEY_PASSWORD));
                        preferenceManager.putString(Constants.KEY_TYPE_USER, documentSnapshot.getString(Constants.KEY_TYPE_USER));
                        preferenceManager.putString(Constants.KEY_ID_USER, documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_NAME, documentSnapshot.getString(Constants.KEY_NAME));

                        if (!documentSnapshot.getString(Constants.KEY_TYPE_USER).equals(Constants.TYPE_ADMIN)) {
                            preferenceManager.putString(Constants.KEY_SURPLUS, documentSnapshot.getString(Constants.KEY_SURPLUS));

                        }

                        Intent intent;
                        if (documentSnapshot.getString(Constants.KEY_TYPE_USER).equals(Constants.TYPE_ADMIN)) {
                            intent = new Intent(getApplicationContext(), ActivityAdMain.class);
                        } else {
                            intent = new Intent(getApplicationContext(), ActivityMainNew.class);
                        }
                        startActivity(intent);
                        finish();
                    }
                });
    }
}