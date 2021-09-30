package com.example.restaurantmanager.account;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager.R;
import com.kodmap.library.kmrecyclerviewstickyheader.KmStickyListener;

public class RecyclerViewAdapter extends ListAdapter<History, RecyclerView.ViewHolder> implements KmStickyListener {

    public RecyclerViewAdapter() {
        super(ModelDiffUtilCallback);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView;
        if (viewType == ItemType.Header) {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.title_header, viewGroup, false);
            return new HeaderViewHolder(itemView);
        } else {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_post, viewGroup, false);
            return new PostViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == ItemType.Header) {
            ((HeaderViewHolder) viewHolder).bind(getItem(i));
        } else {
            ((PostViewHolder) viewHolder).bind(getItem(i));
        }
    }


    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_header);
        }

        public void bind(History model) {
            title.setText("Tháng" + model.getMonth());
        }
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTime, txtDate, txtFood, txtTotalMoney;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txt_time);
            txtDate = itemView.findViewById(R.id.txt_date);
            txtFood = itemView.findViewById(R.id.txt_food);
            txtTotalMoney = itemView.findViewById(R.id.txt_total_money);
        }

        public void bind(History history) {
            txtTime.setText(history.getTime());
            txtDate.setText("Ngày " + history.getDay());
            txtFood.setText(history.getName());
            txtTotalMoney.setText(history.getTotalMoney() + "VND");
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType();
    }

    @Override
    public Integer getHeaderPositionForItem(Integer itemPosition) {
        Integer headerPosition = 0;
        for (Integer i = itemPosition; i > 0; i--) {
            if (isHeader(i)) {
                headerPosition = i;
                return headerPosition;
            }
        }
        return headerPosition;
    }

    @Override
    public Integer getHeaderLayout(Integer headerPosition) {
        return R.layout.title_header;
    }

    @Override
    public void bindHeaderData(View header, Integer headerPosition) {
        TextView tv = header.findViewById(R.id.title_header);
        tv.setText("Tháng" + getItem(headerPosition).getMonth());
    }

    @Override
    public Boolean isHeader(Integer itemPosition) {
        return getItem(itemPosition).getType().equals(ItemType.Header);
    }

    public static final DiffUtil.ItemCallback<History> ModelDiffUtilCallback =
            new DiffUtil.ItemCallback<History>() {
                @Override
                public boolean areItemsTheSame(@NonNull History model, @NonNull History t1) {
                    return model.getMonth().equals(t1.getMonth());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull History model, @NonNull History t1) {
                    return model.equals(t1);
                }
            };
}