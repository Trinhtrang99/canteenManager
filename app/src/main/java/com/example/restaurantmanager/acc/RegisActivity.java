package com.example.restaurantmanager.acc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ActivityRegisBinding;
import com.example.restaurantmanager.ultils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RegisActivity extends AppCompatActivity {


    private ActivityRegisBinding binding;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String verificationId;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private List<String> typeUsers;
    private String typeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_regis);

        firebaseAuth = FirebaseAuth.getInstance();
        typeUsers = new ArrayList<>();
        typeUsers.add("Sinh Viên");
        typeUsers.add("Quan chức");
        setSpinner();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCancelable(false);

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(s, token);

                Log.d("KMFG", "onCodeSent: " + s);

                verificationId = s;
                forceResendingToken = token;
                progressDialog.dismiss();

                binding.clRegister.setVisibility(View.GONE);
                binding.clVerification.setVisibility(View.VISIBLE);
            }
        };

        binding.txtRegis.setOnClickListener(view -> {
            String phone = binding.edtSdt.getText().toString();
            FirebaseFirestore database = FirebaseFirestore.getInstance();
            database.collection(Constants.KEY_COLLECTION_ACCOUNT)
                    .whereEqualTo(Constants.KEY_PHONE_NUMBER, phone)
                    .get()
                    .addOnCompleteListener( task -> {
                        if (task.isSuccessful() && task.getResult() != null
                                && task.getResult().getDocuments().size() > 0) {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            Toast.makeText(getApplicationContext(), "So dt ton tai", Toast.LENGTH_SHORT).show();
                        } else {
                            if (!TextUtils.isEmpty(phone)) {
                                startPhoneNumberVerification(phone);
                            }
                        }
                    });
        });

        binding.btnSubmit.setOnClickListener(view -> {
            String code = binding.edtVerificationCode.getText().toString();
            if (!TextUtils.isEmpty(code)) {
                verifyPhoneNumberWriteCode (verificationId, code);
            }
        });
    }

    private void setSpinner () {
        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, typeUsers);

        binding.spinner.setAdapter(spinnerAdapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //String msg = "position :" + position + " value :" + typeUsers.get(position);
                typeUser = typeUsers.get(position);
                //Toast.makeText(RegisActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(RegisActivity.this, "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verifyPhoneNumberWriteCode(String verificationId, String code) {
        progressDialog.setMessage("Verifying Phone Number");
        progressDialog.show();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential (PhoneAuthCredential credential) {
        progressDialog.setMessage("Logging In...");
        progressDialog.show();

        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        progressDialog.dismiss();
                        String phone = firebaseAuth.getCurrentUser().getPhoneNumber();
                        registerAccount();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void registerAccount () {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_PHONE_NUMBER, binding.edtSdt.getText().toString());
        user.put(Constants.KEY_PASSWORD, binding.edtMk.getText().toString());
        user.put(Constants.KEY_TYPE_USER, typeUser);
        user.put(Constants.KEY_NAME, binding.edtName.getText().toString());
        database.collection(Constants.KEY_COLLECTION_ACCOUNT)
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    onBackPressed();
                });
    }

    private void startPhoneNumberVerification (String phone) {
        progressDialog.setMessage("Verifying Phone Number");
        progressDialog.show();

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void resendVerification (String phone, PhoneAuthProvider.ForceResendingToken token) {
        progressDialog.setMessage("Resending Code");
        progressDialog.show();

        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callbacks)
                .setForceResendingToken(token)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}