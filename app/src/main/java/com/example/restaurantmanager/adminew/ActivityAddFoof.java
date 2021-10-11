package com.example.restaurantmanager.adminew;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Toast;

import com.example.restaurantmanager.BaseActivity;
import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ActivityAddFoofBinding;
import com.example.restaurantmanager.ultils.Constants;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class ActivityAddFoof extends BaseActivity {
    private ActivityAddFoofBinding binding;
    private Integer type;
    private String encodeImage;
    private String typeFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_foof);

        if (getIntent() != null) {
            type = getIntent().getIntExtra("type", 0);
        }
        if (type != null) {
            if (type == 1) {
                binding.txtTitle.setText("Món ăn");
                typeFood = Constants.FOOD;
            } else if (type == 2) {
                binding.txtTitle.setText("CanTeen");
                typeFood = Constants.CANTEEN;
            } else {
                binding.txtTitle.setText("THỰC ĐƠN QUAN CHỨC");
                typeFood = Constants.OFFICIALS;
            }
        }

        binding.imgChooseImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImage.launch(intent);
        });

        binding.btnUpdate.setOnClickListener(view -> {
            addFood();
        });
    }

    private void addFood () {
        showProgressDialog(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> foods = new HashMap<>();
        foods.put(Constants.KEY_NAME, binding.edtName.getText().toString());
        foods.put(Constants.KEY_PRICE, binding.edtPrice.getText().toString());
        foods.put(Constants.KEY_IMAGE, encodeImage);
        foods.put(Constants.TYPE_FOOD, typeFood);
        db.collection(Constants.KEY_COLLECTION_FOOD)
                .add(foods)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    showProgressDialog(false);
                    finish();
                });
    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getData() != null) {
                    Uri imageUri = result.getData().getData();

                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        binding.imgChooseImage.setImageBitmap(bitmap);
                        encodeImage = encodeImage(bitmap);
                    } catch (FileNotFoundException e) {

                    }
                }
            }
    );

    private String encodeImage (Bitmap bitmap) {
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}