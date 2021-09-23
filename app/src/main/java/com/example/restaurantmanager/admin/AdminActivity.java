package com.example.restaurantmanager.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.restaurantmanager.R;
import com.example.restaurantmanager.acc.LoginActivity;
import com.example.restaurantmanager.account.AcountFragment;
import com.example.restaurantmanager.databinding.ActivityAdminBinding;
import com.example.restaurantmanager.ultils.PreferenceManager;
import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityAdminBinding binding;
    private ActionBarDrawerToggle toggle;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin);
        setSupportActionBar(binding.toolbar);
        preferenceManager = new PreferenceManager(getApplicationContext());

        binding.navigationview.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this, binding.drawer, R.string.open, R.string.close);
        binding.drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View view = binding.navigationview.inflateHeaderView(R.layout.header);
        getFragment(new HomeFragment());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                preferenceManager.clear();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;

            case R.id.home:
                getFragment(new HomeFragment());
                break;
            case R.id.nhap:
                getFragment(new NhapFragment());
                break;
            case R.id.doanhthu:
                getFragment(new DoanhFragment());
                break;
            case R.id.acc:
                AcountFragment acountFragment = new AcountFragment();
                getFragment(acountFragment);
                break;
        }
        return false;
    }

    public void getFragment(Fragment fragment) {
        try {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment)
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}