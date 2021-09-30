package com.example.restaurantmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.restaurantmanager.acc.LoginActivity;
import com.example.restaurantmanager.account.AcountFragment;
import com.example.restaurantmanager.adapter.AdapterViewPager;
import com.example.restaurantmanager.databinding.ActivityMainBinding;
import com.example.restaurantmanager.ultils.PreferenceManager;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;
    private FirebaseAuth firebaseAuth;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        firebaseAuth = FirebaseAuth.getInstance();
        preferenceManager = new PreferenceManager(getApplicationContext());

        binding.navigationview.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this, binding.drawer, R.string.open, R.string.close);
        binding.drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View view = binding.navigationview.inflateHeaderView(R.layout.header);

        AdapterViewPager scheduleViewPagerAdapter = new AdapterViewPager(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        binding.viewpager.setAdapter(scheduleViewPagerAdapter);
        binding.viewpager.setOffscreenPageLimit(2);
        binding.tablayout.setupWithViewPager(binding.viewpager);
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
                firebaseAuth.signOut();
                preferenceManager.clear();
                startActivity(new Intent(getApplicationContext() ,LoginActivity.class));
                finish();
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