package com.example.restaurantmanager.admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ActivityEditBinding;

public class EditActivity extends AppCompatActivity {
    private ActivityEditBinding binding;
    public static final int CAMERA_PIC_REQUEST = 99;
    Boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit);
        Intent i = getIntent();
        isEdit = i.getBooleanExtra("isEdit", false);
        binding.imgFood.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
        });

        binding.imgBack.setOnClickListener(v -> {
            finish();
        });
        if (isEdit) {
            binding.btnupdate.setOnClickListener(v -> {
                finish();
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            });
        } else {
            binding.btnupdate.setOnClickListener(v -> {
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            });
            binding.tvFood.setText("");
            binding.tvPrice.setText("");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            //sets imageview as the bitmap
            binding.imgFood.setImageBitmap(image);
        }
    }
}