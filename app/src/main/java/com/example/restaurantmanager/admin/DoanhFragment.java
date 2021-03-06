package com.example.restaurantmanager.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.FragmentDoanhBinding;

import java.util.ArrayList;

import im.dacer.androidcharts.LineView;


public class DoanhFragment extends Fragment {
    FragmentDoanhBinding binding;
    int randomint = 12;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void randomSet(LineView lineView) {

        ArrayList<Float> dataListF = new ArrayList<>();
        float randomF = (float) (Math.random() * 9 + 1);
        for (int i = 0; i < randomint; i++) {
            dataListF.add((float) (Math.random() * randomF));
        }

        ArrayList<Float> dataListF2 = new ArrayList<>();
        randomF = (int) (Math.random() * 9 + 1);
        for (int i = 0; i < randomint; i++) {
            dataListF2.add((float) (Math.random() * randomF));
        }

        ArrayList<Float> dataListF3 = new ArrayList<>();
        randomF = (int) (Math.random() * 9 + 1);
        for (int i = 0; i < randomint; i++) {
            dataListF3.add((float) (Math.random() * randomF));
        }

        ArrayList<ArrayList<Float>> dataListFs = new ArrayList<>();
        dataListFs.add(dataListF);
        dataListFs.add(dataListF2);
        dataListFs.add(dataListF3);

        lineView.setFloatDataList(dataListFs);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_doanh, container, false);
//        LineView lineView = (LineView) binding.getRoot().findViewById(R.id.line_view);
//        ArrayList<String> test = new ArrayList<String>();
//        for (int i = 0; i < randomint; i++) {
//            test.add(String.valueOf(i + 1));
//        }
//
//        lineView.setDrawDotLine(false);
//        lineView.setShowPopup(LineView.SHOW_POPUPS_MAXMIN_ONLY); //optional
//        lineView.setBottomTextList(test);
//        // ???????ng m??u ??en l?? nh???p , m??u GRAY l?? b??n ,m??u GREEN l?? l???i
//        lineView.setColorArray(new int[]{Color.BLACK, Color.GREEN, Color.GRAY, Color.CYAN});
//        randomSet(lineView);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnDoanhThuNam.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), ThongKeActivity.class);
            i.putExtra("btn1", "doanhthunam");
            startActivity(i);
        });
        binding.btnDoanhThuThang.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), ThongKeActivity.class);
            i.putExtra("btn1", "doangthuthang");
            startActivity(i);
        });
        binding.btnNhapThang.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), ThongKeActivity.class);
            i.putExtra("btn1", "nhapthang");
            startActivity(i);
        });
        binding.btnNhapNam.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), ThongKeActivity.class);
            i.putExtra("btn1", "nhapnam");
            startActivity(i);
        });
        binding.btnDoanhTrongNgay.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), ThongKeActivity.class);
            i.putExtra("btn1", "trongngay");
            startActivity(i);
        });
    }
}