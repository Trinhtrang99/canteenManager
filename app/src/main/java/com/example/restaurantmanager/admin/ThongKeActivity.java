package com.example.restaurantmanager.admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.account.ItemType;
import com.example.restaurantmanager.databinding.ActivityThongKeBinding;
import com.kodmap.library.kmrecyclerviewstickyheader.KmHeaderItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ThongKeActivity extends AppCompatActivity {
    ActivityThongKeBinding binding;
    AdapterNhap adapter;
    private LinearLayoutManager layoutManager;
    private KmHeaderItemDecoration kmHeaderItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_thong_ke);
        String string = getIntent().getStringExtra("btn1");
        if (string.equals("doanhthunam")) {
            initAdapter();
            initData();

        } else if (string.equals("doangthuthang")) {
            initAdapter();
            initData();

        } else if (string.equals("nhapthang")) {
            // sửa header theo năm theo tháng
            initAdapter();
            initData();

        } else if (string.equals("nhapnam")) {
            // sửa header theo năm
            initAdapter();
            initData();
        } else if (string.equals("trongngay")) {
// list 1 item
        }
    }

    private void initAdapter() {
       /* adapter = new AdapterNhap();
        layoutManager = new LinearLayoutManager(this);
        //   recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        binding.rc.setLayoutManager(layoutManager);*/
        /*kmHeaderItemDecoration = new KmHeaderItemDecoration(adapter);
        binding.rc.setAdapter(adapter);*/

    }

    List<Nhap> modelList;

    private void initData() {

        modelList = new ArrayList<>();
        for (Integer i = 1; i < 12; i++) {
            Nhap headerModel = new Nhap("Tháng " + i + " (tổng 5.000.000đ)", ItemType.Header);
            modelList.add(headerModel);
            for (Integer j = 1; j < 31; j++) {
                Nhap model = new Nhap("", j + "/" + i, "bim bim", "giá : 10.000đ", "số lượng3",
                        "Tổng tiền : 30.000đ", ItemType.Post);
                modelList.add(model);
            }
        }
      //  adapter.submitList(modelList);
    }
}