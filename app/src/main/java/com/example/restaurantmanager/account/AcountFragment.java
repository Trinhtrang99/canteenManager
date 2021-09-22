package com.example.restaurantmanager.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.FragmentAcountBinding;

public class AcountFragment extends Fragment {

    private FragmentAcountBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding.txtls.setOnClickListener(v -> {
//            Intent i = new Intent(getContext(), HistoryActivity.class);
//            startActivity(i);
//        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_acount, container, false);
        return binding.getRoot();
    }
}