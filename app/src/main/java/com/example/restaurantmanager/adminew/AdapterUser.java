package com.example.restaurantmanager.adminew;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.adminew.model.UserModel;
import com.example.restaurantmanager.databinding.ItemuserBinding;

import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.ViewHolder> {
    List<UserModel> list;

    @NonNull
    @Override
    public AdapterUser.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemuserBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.itemuser,
                parent,
                false
        );

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUser.ViewHolder holder, int position) {
        holder.binding.name.setText(list.get(position).name);
        holder.binding.sodu.setText(list.get(position).du);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemuserBinding binding;

        public ViewHolder(@NonNull ItemuserBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
