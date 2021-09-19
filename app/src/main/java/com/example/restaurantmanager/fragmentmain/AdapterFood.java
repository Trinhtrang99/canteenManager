package com.example.restaurantmanager.fragmentmain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.datafake.Food;

import java.util.ArrayList;

public class AdapterFood extends RecyclerView.Adapter<AdapterFood.ViewHolder> {
    ArrayList<Food> list;
    Context context;

    public AdapterFood(ArrayList<Food> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterFood.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFood.ViewHolder holder, int position) {
        holder.tv_name.setText(list.get(position).getName());
        // Glide.with(context).load(list.get(position).getImg()).into(holder.img_food);
        holder.tv_price.setText(list.get(position).getPrice() + "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_price;
        ImageView img_food;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_food);
            img_food = itemView.findViewById(R.id.img_food);
            checkBox = itemView.findViewById(R.id.checked);
            tv_price = itemView.findViewById(R.id.tv_price);
        }
    }
}
