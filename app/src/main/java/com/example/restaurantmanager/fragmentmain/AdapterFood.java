package com.example.restaurantmanager.fragmentmain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.datafake.Food;
import com.example.restaurantmanager.ultils.BitmapUltil;

import java.util.ArrayList;

public class AdapterFood extends RecyclerView.Adapter<AdapterFood.ViewHolder> {
    ArrayList<Food> list;
    Context context;
    Boolean isAdmin;
    OnLongPress onLongPress;

    public AdapterFood(ArrayList<Food> list, Context context, Boolean isAdmin, OnLongPress onLongPress) {
        this.list = list;
        this.context = context;
        this.isAdmin = isAdmin;
        this.onLongPress = onLongPress;
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
        Bitmap bitmap = BitmapUltil.getBitmap(list.get(position).getImg());
        if (bitmap != null) {
            holder.img_food.setImageBitmap(bitmap);
        }
        if (isAdmin) {
            holder.checkBox.setVisibility(View.GONE);
            holder.rl.setOnLongClickListener(v -> {
                onLongPress.onLongPress(list.get(position).getId());
                holder.checkBox.setVisibility(View.VISIBLE);
                holder.checkBox.setChecked(true);
                return true;
            });
            holder.rl.setOnClickListener(v -> {
                onLongPress.onPressEdit(list.get(position));
            });
        } else {
            holder.checkBox.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_price;
        ImageView img_food;
        CheckBox checkBox;
        RelativeLayout rl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_food);
            img_food = itemView.findViewById(R.id.img_food);
            checkBox = itemView.findViewById(R.id.checked);
            tv_price = itemView.findViewById(R.id.tv_price);
            rl = itemView.findViewById(R.id.rl);
        }
    }

    interface OnLongPress{
        void onLongPress(String idFood);
        void onPressEdit(Food food);
    }
}
