package com.example.restaurantmanager.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.FragmentHomeBinding;
import com.example.restaurantmanager.databinding.HomefragmentBinding;
import com.example.restaurantmanager.datafake.ListData;
import com.example.restaurantmanager.fragmentmain.AdapterFood;
import com.example.restaurantmanager.fragmentmain.FragmentDrink;
import com.example.restaurantmanager.fragmentmain.FragmentFood;


public class HomeFragment extends Fragment {
    HomefragmentBinding binding;
    AdapterFood adapterFollower;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.homefragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.food.setOnClickListener(v -> {
            loadFragment(new FragmentFood());
        });

        binding.drink.setOnClickListener(v -> {
            loadFragment(new FragmentDrink());
        });
    }

    private void loadFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,
                fragment).commit();
    }


}