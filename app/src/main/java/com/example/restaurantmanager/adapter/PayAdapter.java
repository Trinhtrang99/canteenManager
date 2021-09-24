package com.example.restaurantmanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ItemPayBinding;
import com.example.restaurantmanager.model.Pay;
import com.example.restaurantmanager.ultils.BitmapUltil;

import java.util.ArrayList;

public class PayAdapter extends RecyclerView.Adapter<PayAdapter.PayViewHolder>{

    private ArrayList<Pay> pays;

    public PayAdapter(ArrayList<Pay> pays) {
        this.pays = pays;
    }

    @NonNull
    @Override
    public PayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPayBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_pay,
                parent,
                false
        );

        return new PayViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PayViewHolder holder, int position) {
        holder.binding.txtName.setText(pays.get(position).getName());
        holder.binding.imgFood.setImageBitmap(BitmapUltil.getBitmap(pays.get(position).getImage()));
    }

    @Override
    public int getItemCount() {
        return pays.size();
    }

    class PayViewHolder extends RecyclerView.ViewHolder {
        ItemPayBinding binding;
        public PayViewHolder(@NonNull ItemPayBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
