package com.example.restaurantmanager.admin;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.account.ItemType;
import com.example.restaurantmanager.adminew.model.Revenue;
import com.example.restaurantmanager.databinding.ItemthongkenhapBinding;
import com.kodmap.library.kmrecyclerviewstickyheader.KmStickyListener;

import java.util.List;

public class AdapterNhap extends RecyclerView.Adapter<AdapterNhap.MyViewHolder>{

    private List<Revenue> revenues;

    public AdapterNhap(List<Revenue> revenues) {
        this.revenues = revenues;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemthongkenhapBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.itemthongkenhap,
                parent,
                false
        );
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.tvloai.setText(revenues.get(position).getType());
        holder.binding.txtGia.setText("Giá: " + revenues.get(position).getPrice());
       // holder.binding.txtTotalMoney.setText("Tổng tiền:" + revenues.get(position).getTotalMoney());
    }

    @Override
    public int getItemCount() {
        return revenues.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemthongkenhapBinding binding;
        public MyViewHolder(@NonNull ItemthongkenhapBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

   /* public AdapterNhap() {
        super(ModelDiffUtilCallback);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView;
        if (viewType == ItemType.Header) {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.title_header, viewGroup, false);
            return new AdapterNhap.HeaderViewHolder(itemView);
        } else {
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemthongkenhap, viewGroup, false);
            return new AdapterNhap.PostViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == ItemType.Header) {
            ((AdapterNhap.HeaderViewHolder) viewHolder).bind(getItem(i));
        } else {
            ((AdapterNhap.PostViewHolder) viewHolder).bind(getItem(i));
        }
    }


    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_header);
        }

        public void bind(Nhap model) {
            title.setText(model.title);
        }
    }

    class PostViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView loai;
        public TextView txt_gia;
        public TextView txt_slg;
        public TextView txt_total_money;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_post);
            loai = (TextView) itemView.findViewById(R.id.tvloai);
            txt_gia = (TextView) itemView.findViewById(R.id.txt_gia);
            txt_slg = (TextView) itemView.findViewById(R.id.txt_slg);
            txt_total_money = (TextView) itemView.findViewById(R.id.txt_total_money);
        }

        public void bind(Nhap model) {
            loai.setText(model.loai);
            title.setText(model.date);
            txt_gia.setText(model.gia);
            txt_slg.setText(model.solg);
            txt_total_money.setText(model.tongtien);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).type;
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
        tv.setText(getItem(headerPosition).title);
    }

    @Override
    public Boolean isHeader(Integer itemPosition) {
        return getItem(itemPosition).type.equals(ItemType.Header);
    }

    public static final DiffUtil.ItemCallback<Nhap> ModelDiffUtilCallback =
            new DiffUtil.ItemCallback<Nhap>() {
                @Override
                public boolean areItemsTheSame(@NonNull Nhap model, @NonNull Nhap t1) {
                    return model.title.equals(t1.title);
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Nhap model, @NonNull Nhap t1) {
                    return model.equals(t1);
                }
            };*/
}