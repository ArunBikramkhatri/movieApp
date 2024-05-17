package com.arun.movieapp.ui.viewPager;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.arun.movieapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ViewPagerActivity extends AppCompatActivity {

    private TabLayout tabLayout ;
    private ViewPager2 viewPager2 ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);

        initFields();



       VpAdapter vpAdapter = new VpAdapter(this);
       vpAdapter.addFragment(new FragmentOne() , "Action");
       vpAdapter.addFragment(new FragmentTwo() ,"Horror");
       viewPager2.setAdapter(vpAdapter);
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText("dsfa")
        ).attach();
    }

    private void initFields() {

        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager2);
    }
}
