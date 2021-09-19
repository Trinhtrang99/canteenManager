package com.example.restaurantmanager.fragmentmain;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.HomefragmentBinding;
import com.example.restaurantmanager.datafake.ListData;

public class FragmentFood extends Fragment {
    HomefragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.homefragment, container, false);
        AdapterFood adapterFollower = new AdapterFood(ListData.list(), getContext());

        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        binding.rc.setLayoutManager(layoutManager1);
        binding.rc.setAdapter(adapterFollower);
        return binding.getRoot();
    }
}
