package com.example.restaurantmanager.fragmentmain;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.admin.AdminActivity;
import com.example.restaurantmanager.admin.EditActivity;
import com.example.restaurantmanager.databinding.FramentfoodBinding;
import com.example.restaurantmanager.datafake.ListData;

public class FragmentFood extends Fragment implements AdapterFood.OnLongPress {
    FramentfoodBinding binding;
    AdapterFood adapterFollower;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.framentfood, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity().getClass().equals(AdminActivity.class)) {
            Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
            binding.imgBack.setVisibility(View.VISIBLE);
            binding.btn.setVisibility(View.VISIBLE);
            adapterFollower = new AdapterFood(ListData.list(), getContext(), true, this);
            RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
            binding.rc.setLayoutManager(layoutManager1);
            binding.rc.setAdapter(adapterFollower);
        } else {

//            adapterFollower = new AdapterFood(ListData.list(), getContext(),false);
//            RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
//            binding.rc.setLayoutManager(layoutManager1);
//            binding.rc.setAdapter(adapterFollower);
            binding.imgBack.setVisibility(View.GONE);
            binding.btn.setVisibility(View.GONE);
        }
        binding.imgBack.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStackImmediate();
        });
        binding.btn.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), EditActivity.class);
            i.putExtra("isEdit", false);
            startActivity(i);
        });
    }

    @Override
    public void onLongPress() {
        binding.imgdelete.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPressEdit() {
        Intent i = new Intent(getContext(), EditActivity.class);
        i.putExtra("isEdit", true);
        startActivity(i);

    }
}
