package com.example.restaurantmanager.account;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.restaurantmanager.BaseActivity;
import com.example.restaurantmanager.R;
import com.example.restaurantmanager.databinding.ActivityHistoryBinding;
import com.example.restaurantmanager.ultils.Constants;
import com.example.restaurantmanager.ultils.DialogUltil;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.kodmap.library.kmrecyclerviewstickyheader.KmHeaderItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import cn.jeesoft.widget.pickerview.CharacterPickerView;
import cn.jeesoft.widget.pickerview.OnOptionChangedListener;

public class HistoryActivity extends BaseActivity implements OnOptionChangedListener {
    private RecyclerViewAdapter adapter;
    private LinearLayoutManager layoutManager;
    private KmHeaderItemDecoration kmHeaderItemDecoration;
    private ActivityHistoryBinding binding;
    private List<History> histories;
    private List<String> years;
    private String year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history);

        years = new ArrayList<>();
        histories = new ArrayList<>();
        initAdapter();
        binding.chart.setOnClickListener(v -> {
            Intent i = new Intent(this, ChartActivity.class);
            startActivity(i);
        });

        binding.imgYear.setOnClickListener(view -> {
            CharacterPickerView characterPickerView = new CharacterPickerView(HistoryActivity.this);
            characterPickerView.setPicker(years);
            characterPickerView.setMaxTextSize(15);
            characterPickerView.setOnOptionChangedListener(this);
            characterPickerView.setCyclic(true);

            DialogUltil.showDialog(HistoryActivity.this, "", characterPickerView);
        });

        getYear();
        year = new SimpleDateFormat("yyyy").format(new Date());
        getFoodManager();
    }

    @Override
    public void onOptionChanged(int option1, int option2, int option3) {
        year = years.get(option1);
        getFoodManager();
        DialogUltil.bottomDialog.dismiss();
    }

    private void initAdapter() {
        adapter = new RecyclerViewAdapter();
        layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        kmHeaderItemDecoration = new KmHeaderItemDecoration(adapter);
        binding.recyclerView.setAdapter(adapter);
    }

    private void getYear () {
        showProgressDialog(true);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_COLLECTION_FOOD_MANAGER)
                .get()
                .addOnCompleteListener(task -> {
                    for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                        years.add(queryDocumentSnapshot.getId());
                    }
                    adapter.submitList(histories);
                    showProgressDialog(false);

                });
    }

    private int countMonth;
    private void getFoodManager() {
        showProgressDialog(true);
        histories.clear();

        countMonth = 1;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.KEY_COLLECTION_FOOD_MANAGER)
                .document(year)
                .collection(Constants.KEY_COLLECTION_MONTH)
                .get()
                .addOnCompleteListener(taskMonth -> {
                    for (QueryDocumentSnapshot queryDocumentSnapshotMonth : taskMonth.getResult()) {

                        db.collection(Constants.KEY_COLLECTION_FOOD_MANAGER)
                                .document(year)
                                .collection(Constants.KEY_COLLECTION_MONTH)
                                .document(queryDocumentSnapshotMonth.getId() + "")
                                .collection(Constants.KEY_FOOD_BY_DAY)
                                .get()
                                .addOnCompleteListener(task -> {
                                    final Handler handler = new Handler(Looper.getMainLooper());
                                    handler.postDelayed(() -> {
                                        adapter = new RecyclerViewAdapter();
                                        layoutManager = new LinearLayoutManager(HistoryActivity.this);
                                        binding.recyclerView.setLayoutManager(layoutManager);
                                        kmHeaderItemDecoration = new KmHeaderItemDecoration(adapter);
                                        binding.recyclerView.setAdapter(adapter);

                                        History headerModel = new History();
                                        headerModel.setMonth("Tháng " + queryDocumentSnapshotMonth.getId());
                                        headerModel.setType(ItemType.Header);
                                        headerModel.setMonth(queryDocumentSnapshotMonth.getId() + "");
                                        histories.add(headerModel);

                                        for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                            History history = new History();
                                            if (queryDocumentSnapshot.getString(Constants.KEY_TIME) != null) {
                                                Integer time = Integer.parseInt(queryDocumentSnapshot.getString(Constants.KEY_TIME));
                                                if(time >= 6 && time <= 12) {
                                                    history.setTime("Sáng");
                                                } else if (time >= 12 && time <= 18) {
                                                    history.setTime("Chiều");
                                                } else {
                                                    history.setTime("Tối");
                                                }
                                            }
                                            history.setName(queryDocumentSnapshot.getString("name"));
                                            history.setType(ItemType.Post);
                                            history.setMonth(queryDocumentSnapshotMonth.getId() + "");
                                            history.setDay(queryDocumentSnapshot.getString(Constants.KEY_DAY));
                                            history.setTotalMoney("Tổng tiền: " + queryDocumentSnapshot.getString(Constants.KEY_TOTAL_MONEY));
                                            histories.add(history);
                                        }

                                        sortOrder(histories);
                                        adapter.submitList(histories);
                                        countMonth++;

                                        if (countMonth == 12) {
                                            showProgressDialog(false);
                                        }
                                    }, 2000);
                                });

                    }
                });
    }

    public static void sortOrder(List<History> models) {
        Collections.sort(models, (obj, obj1) -> {
            if (Integer.parseInt(obj.getMonth()) < Integer.parseInt(obj1.getMonth())) {
                return -1;
            } else {
                return 1;
            }
        });
    }
}