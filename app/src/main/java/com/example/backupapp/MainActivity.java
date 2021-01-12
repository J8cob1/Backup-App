package com.example.backupapp;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends FragmentActivity {

    private ViewPager2 viewPager;
    private PageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Stuff for the main activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tab_container = findViewById(R.id.tab_container);
        MainActivity thisActivity = this;
        this.viewPager = findViewById(R.id.content_container);
        this.pageAdapter = new PageAdapter(this);
        this.viewPager.setAdapter(this.pageAdapter);

        // Setup the tabs to switch whenever the view pager changes the page (and vice versa)
        // SetupWithViewPager doesn't work with ViewPager2
        this.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected (int position) {
                tab_container.getTabAt(position).select();
            }
        });
        tab_container.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                thisActivity.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

    }
}