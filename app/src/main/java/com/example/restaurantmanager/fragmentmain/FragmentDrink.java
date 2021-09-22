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
import com.example.restaurantmanager.databinding.FragmentdrinkBinding;
import com.example.restaurantmanager.datafake.ListData;

public class FragmentDrink extends Fragment implements AdapterFood.OnLongPress {
    FragmentdrinkBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragmentdrink, container, false);
        AdapterFood adapterFollower = new AdapterFood(ListData.listDrink(), getContext(), false, this);

        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        binding.rc.setLayoutManager(layoutManager1);
        binding.rc.setAdapter(adapterFollower);
        return binding.getRoot();
    }

    @Override
    public void onLongPress() {

    }

    @Override
    public void onPressEdit() {

    }
}
