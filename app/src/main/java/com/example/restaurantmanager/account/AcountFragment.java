package com.example.restaurantmanager.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.restaurantmanager.BaseFragment;
import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.FragmentAcountBinding;
import com.example.restaurantmanager.ultils.Constants;
import com.example.restaurantmanager.ultils.PreferenceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AcountFragment extends BaseFragment {

    private FragmentAcountBinding binding;
    private PreferenceManager preferenceManager;
    private Boolean isAdmin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_acount, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferenceManager = new PreferenceManager(getContext());

        if (!preferenceManager.getString(Constants.KEY_TYPE_USER).equals(Constants.TYPE_ADMIN)) {
            binding.txtdu.setVisibility(View.GONE);
            binding.txtls.setVisibility(View.GONE);
        } else {
            binding.txtdu.setVisibility(View.VISIBLE);
            binding.txtls.setVisibility(View.VISIBLE);
        }

        getUser();

        binding.txtls.setOnClickListener(view1 -> {
            startActivity(new Intent(getActivity(), HistoryActivity.class));
        });
    }

    private void getUser() {
        showProgressDialog(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_COLLECTION_ACCOUNT)
                .whereEqualTo(Constants.KEY_PHONE_NUMBER, preferenceManager.getString(Constants.KEY_PHONE_NUMBER))
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                    binding.txtPhone.setText(documentSnapshot.getString(Constants.KEY_PHONE_NUMBER));
                    binding.txtdu.setText(documentSnapshot.getString(Constants.KEY_SURPLUS) + "VND");
                    binding.txtTk.setText(documentSnapshot.getString(Constants.KEY_ACCOUNT_NUMBER));

                    showProgressDialog(false);
                });
    }
}