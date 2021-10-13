package com.example.restaurantmanager.fragmentmain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.account.History;

import java.util.List;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ViewHolder> {
    List<History> list;
    @NonNull
    @Override
    public AdapterHistory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistory.ViewHolder holder, int position) {
        holder.txtTime.setText(list.get(position).getTime());
        holder.txtDate.setText("Ngày " + list.get(position).getDay());
        holder.txtFood.setText(list.get(position).getName());
        holder.txtTotalMoney.setText(list.get(position).getTotalMoney() + "VND");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTime, txtDate, txtFood, txtTotalMoney;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txt_time);
            txtDate = itemView.findViewById(R.id.txt_date);
            txtFood = itemView.findViewById(R.id.txt_food);
            txtTotalMoney = itemView.findViewById(R.id.txt_total_money);
        }
    }
}
