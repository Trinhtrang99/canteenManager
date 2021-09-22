package com.example.restaurantmanager.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.FragmentNhapBinding;

public class NhapFragment extends Fragment {
    private FragmentNhapBinding binding;
    String arr1[] = {
            "Thực phẩm",
            "Đồ ăn vặt"};
    String arr2[] = {
            "kg",
            "cái",
            "chai"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nhap, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arr1);

        arrayAdapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        binding.spin1.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter1 =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, arr2);

        arrayAdapter.setDropDownViewResource
                (android.R.layout.simple_list_item_single_choice);
        binding.spin2.setAdapter(arrayAdapter1);
    }
}