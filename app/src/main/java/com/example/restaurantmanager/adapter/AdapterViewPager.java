package com.example.restaurantmanager.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.restaurantmanager.fragmentmain.FragmentDrink;
import com.example.restaurantmanager.fragmentmain.FragmentFood;

public class AdapterViewPager extends FragmentPagerAdapter {

    private final String[] mTabsTitle = {"Đồ ăn","Đồ uống"};
    FragmentDrink fragmentDrink = new FragmentDrink();
    FragmentFood fragmentFood = new FragmentFood();
    public AdapterViewPager(FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
            case 2:
                return fragmentDrink;
            default:
                return fragmentFood;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabsTitle[position];
    }
//    public AdapterViewPager(@NonNull FragmentManager fm, int behavior) {
//        super(fm, behavior);
//    }
//
//    @NonNull
//    @Override
//    public Fragment getItem(int position) {
//        switch (position) {
//            case 0:
//                new FragmentDrink();
//                break;
//            case 1:
//                new FragmentDrink();
//                break;
//            default:
//                new FragmentDrink();
//        }
//        return null;
//    }
//
//    @Override
//    public int getCount() {
//        return 2;
//    }
//
//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        String title = "";
//        switch (position) {
//            case 0:
//                title = " Đồ ăn";
//                break;
//            case 1:
//                title = " Đồ uống";
//                break;
//        }
//        return super.getPageTitle(position);
//    }
}
