package com.example.restaurantmanager.acc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.restaurantmanager.MainActivity;
import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ActivityForgotPasswordBinding;
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

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding binding;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String verificationId;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this ,R.layout.activity_forgot_password);

        firebaseAuth = FirebaseAuth.getInstance();

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

        binding.btnContinue.setOnClickListener(view -> {
            String phone = binding.edtSdt.getText().toString();
            if (!TextUtils.isEmpty(phone)) {
                startPhoneNumberVerification(phone);
            }
        });

        binding.btnSubmit.setOnClickListener(view -> {
            String code = binding.edtVerificationCode.getText().toString();
            if (!TextUtils.isEmpty(code)) {
                verifyPhoneNumberWriteCode (verificationId, code);
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
                        updateAccount();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void updateAccount() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_PHONE_NUMBER, binding.edtSdt.getText().toString());
        user.put(Constants.KEY_PASSWORD, binding.edtMk.getText().toString());
        database.collection(Constants.KEY_COLLECTION_ACCOUNT)
                .whereEqualTo(Constants.KEY_PHONE_NUMBER, binding.edtSdt.getText().toString())
                .get()
                .addOnCompleteListener( task -> {
                    if (task.isSuccessful() && task.getResult() != null
                            && task.getResult().getDocuments().size() > 0) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);

                        database.collection(Constants.KEY_COLLECTION_ACCOUNT)
                                .document(documentSnapshot.getId())
                                .update(user)
                                .addOnSuccessListener(documentReference -> {
                                    onBackPressed();
                                });

                    }
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