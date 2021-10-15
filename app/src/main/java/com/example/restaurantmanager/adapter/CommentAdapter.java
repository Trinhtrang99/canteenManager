package com.example.restaurantmanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ItemCommentBinding;
import com.example.restaurantmanager.model.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> comments;

    public CommentAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommentBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_comment,
                parent,
                false
        );
        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.binding.txtName.setText(comments.get(position).getName());
        holder.binding.txtContent.setText(comments.get(position).getTitle());
        holder.binding.txtTime.setText(comments.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {
        ItemCommentBinding binding;
        public CommentViewHolder(@NonNull ItemCommentBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
