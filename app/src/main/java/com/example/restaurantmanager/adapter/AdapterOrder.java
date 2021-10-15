package com.example.restaurantmanager.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ItemOrderBinding;
import com.example.restaurantmanager.model.Order;
import com.example.restaurantmanager.ultils.BitmapUltil;

import java.util.List;

public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.OrderViewHolder> {

    private List<Order> orders;

    public AdapterOrder(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_order,
                parent,
                false
        );
        return new OrderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.binding.txtName.setText(orders.get(position).getName());
        holder.binding.txtPrice.setText(orders.get(position).getPrice().toString());
        holder.binding.txtDay.setText(orders.get(position).getDay() + "-" + orders.get(position).getTime());
        Bitmap bitmap = BitmapUltil.getBitmap(orders.get(position).getImage());
        if (bitmap != null) {
            holder.binding.imgFood.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        ItemOrderBinding binding;
        public OrderViewHolder(@NonNull ItemOrderBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
