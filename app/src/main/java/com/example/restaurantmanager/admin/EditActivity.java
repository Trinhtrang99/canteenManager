package com.example.restaurantmanager.admin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.example.restaurantmanager.BaseActivity;
import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ActivityEditBinding;
import com.example.restaurantmanager.datafake.Food;
import com.example.restaurantmanager.ultils.BitmapUltil;
import com.example.restaurantmanager.ultils.Constants;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class EditActivity extends BaseActivity {
    private ActivityEditBinding binding;
    private static final int CAMERA_PERMISSION = 1000;
    private Boolean isEdit;
    private String encodeImg;
    private String pathTakeImage;
    private Food food;
    private String typeFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit);
        Intent i = getIntent();
        isEdit = i.getBooleanExtra("isEdit", false);
        food = (Food) i.getSerializableExtra("food");
        typeFood = i.getStringExtra(Constants.TYPE_FOOD);

        if (isEdit && food != null) {
            binding.imgFood.setImageBitmap(BitmapUltil.getBitmap(food.getImg()));
            binding.edtFood.setText(food.getName());
            binding.edtPrice.setText(String.valueOf(food.getPrice()));
            encodeImg = food.getImg();
        }

        binding.imgFood.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(EditActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(EditActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
            } else {
                openCamera();
            }
        });

        binding.imgBack.setOnClickListener(v -> {
            finish();
        });
        if (isEdit) {
            binding.btnupdate.setOnClickListener(v -> {
                if (typeFood.equals(Constants.KEY_COLLECTION_FOOD)) {
                    updateFood();
                } else if (typeFood.equals(Constants.KEY_COLLECTION_DRINK)) {
                    updateDrink();
                }
            });
        } else {
            binding.btnupdate.setOnClickListener(v -> {
                if (typeFood.equals(Constants.KEY_COLLECTION_FOOD)) {
                    addFood();
                } else if (typeFood.equals(Constants.KEY_COLLECTION_DRINK)) {
                    addDrink();
                }
            });
            binding.edtFood.setText("");
            binding.edtPrice.setText("");
        }

    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getData() != null) {
                    Bitmap bitmap = BitmapFactory.decodeFile(pathTakeImage);
                    binding.imgFood.setImageBitmap(bitmap);
                    encodeImg = encodeImage(bitmap);
                }
            }
    );

    private void addFood () {
        showProgressDialog(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> foods = new HashMap<>();
        foods.put(Constants.KEY_NAME, binding.edtFood.getText().toString());
        foods.put(Constants.KEY_PRICE, binding.edtPrice.getText().toString());
        foods.put(Constants.KEY_IMAGE, encodeImg);
        db.collection(Constants.KEY_COLLECTION_FOOD)
                .add(foods)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    showProgressDialog(false);
                    finish();
                });
    }

    private void addDrink () {
        showProgressDialog(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> foods = new HashMap<>();
        foods.put(Constants.KEY_NAME, binding.edtFood.getText().toString());
        foods.put(Constants.KEY_PRICE, binding.edtPrice.getText().toString());
        foods.put(Constants.KEY_IMAGE, encodeImg);
        db.collection(Constants.KEY_COLLECTION_DRINK)
                .add(foods)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    showProgressDialog(false);
                    finish();
                });
    }

    private void updateFood () {
        showProgressDialog(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> foods = new HashMap<>();
        foods.put(Constants.KEY_NAME, binding.edtFood.getText().toString());
        foods.put(Constants.KEY_PRICE, binding.edtPrice.getText().toString());
        foods.put(Constants.KEY_IMAGE, encodeImg);
        db.collection(Constants.KEY_COLLECTION_FOOD).document(food.getId())
                .update(foods)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
                    showProgressDialog(false);
                    finish();
                });
    }

    private void updateDrink () {
        showProgressDialog(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> foods = new HashMap<>();
        foods.put(Constants.KEY_NAME, binding.edtFood.getText().toString());
        foods.put(Constants.KEY_PRICE, binding.edtPrice.getText().toString());
        foods.put(Constants.KEY_IMAGE, encodeImg);
        db.collection(Constants.KEY_COLLECTION_DRINK).document(food.getId())
                .update(foods)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
                    showProgressDialog(false);
                    finish();
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION) {
            openCamera();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = File.createTempFile(timeStamp, ".jpg", storageDir);
        pathTakeImage = file.getAbsolutePath();
        return file;
    }

    private void openCamera () {
        Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (cameraIntent.resolveActivity(this.getPackageManager()) != null) {
            File imageFile = null;
            try {
                imageFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (imageFile != null) {
                Uri photoUri = FileProvider.getUriForFile(this,
                        getApplicationContext().getPackageName() + ".fileprovider",
                        imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            }
        }
        pickImage.launch(cameraIntent);
    }

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