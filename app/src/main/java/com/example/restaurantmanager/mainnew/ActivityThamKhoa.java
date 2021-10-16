package com.example.restaurantmanager.mainnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.os.Bundle;

import com.example.restaurantmanager.BaseActivity;
import com.example.restaurantmanager.R;
import com.example.restaurantmanager.adapter.CommentAdapter;
import com.example.restaurantmanager.databinding.ActivityThamKhoaBinding;
import com.example.restaurantmanager.model.Comment;
import com.example.restaurantmanager.ultils.Constants;
import com.example.restaurantmanager.ultils.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ActivityThamKhoa extends BaseActivity {
    private ActivityThamKhoaBinding binding;
    private PreferenceManager preferenceManager;
    private List<Comment> comments;
    private CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tham_khoa);

        preferenceManager = new PreferenceManager(getApplicationContext());
        comments = new ArrayList<>();

        binding.btnSend.setOnClickListener(view -> {
            if (!binding.edtComment.getText().toString().isEmpty()) {
                addComment();
            }
        });

        getComment();
    }

    private void addComment () {
        showProgressDialog(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> comments = new HashMap<>();
        comments.put(Constants.KEY_NAME, preferenceManager.getString(Constants.KEY_NAME));
        comments.put(Constants.KEY_TITLE, binding.edtComment.getText().toString());
        comments.put(Constants.KEY_ID_FOOD, getIntent().getStringExtra(Constants.KEY_ID_FOOD));
        comments.put(Constants.KEY_TIME, new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        db.collection(Constants.KEY_COLLECTION_COMMENT)
                .add(comments)
                .addOnSuccessListener(documentReference -> {
                    getComment();
                });
    }

    private void getComment () {
        showProgressDialog(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_COLLECTION_COMMENT)
                .whereEqualTo(Constants.KEY_TIME, new SimpleDateFormat("dd/MM/yyyy").format(new Date()))
                .get()
                .addOnCompleteListener(task -> {

                    comments.clear();

                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        Comment comment = new Comment(
                                queryDocumentSnapshot.getId(),
                                queryDocumentSnapshot.getString(Constants.KEY_NAME),
                                queryDocumentSnapshot.getString(Constants.KEY_TITLE),
                                queryDocumentSnapshot.getString(Constants.KEY_TIME)
                        );

                        comments.add(comment);
                    }

                    adapter = new CommentAdapter(comments);
                    binding.rc.addItemDecoration(new DividerItemDecoration(binding.rc.getContext(), DividerItemDecoration.VERTICAL));
                    binding.rc.setAdapter(adapter);

                    showProgressDialog(false);
                });
    }
}