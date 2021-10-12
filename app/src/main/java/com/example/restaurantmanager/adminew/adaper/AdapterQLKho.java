package com.example.restaurantmanager.adminew.adaper;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.adminew.model.KhoMOdel;
import com.example.restaurantmanager.databinding.ItemkhoBinding;

import java.util.List;

public class AdapterQLKho extends RecyclerView.Adapter<AdapterQLKho.ViewHolder> {
    private List<KhoMOdel> list;

    public AdapterQLKho(List<KhoMOdel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterQLKho.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemkhoBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.itemkho,
                parent,
                false
        );

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterQLKho.ViewHolder holder, int position) {
        holder.binding.name.setText(list.get(position).name);
        holder.binding.slg.setText("Số lượng : " + list.get(position).slg);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemkhoBinding binding;

        public ViewHolder(@NonNull ItemkhoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
