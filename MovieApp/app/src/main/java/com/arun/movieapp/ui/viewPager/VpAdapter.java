package com.arun.movieapp.ui.viewPager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class VpAdapter extends FragmentStateAdapter {


    private ArrayList<Fragment> fragmentArrayList;
    private ArrayList<String> titleArrayList ;

    private Fragment fragment ;

    public VpAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragmentArrayList = new ArrayList<>();
        titleArrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentArrayList.get(position);

    }

    @Override
    public int getItemCount() {
      return fragmentArrayList.size();
    }

    public void addFragment(Fragment fragment , String title){
        fragmentArrayList.add(fragment);
        titleArrayList.add(title);
    }


}
