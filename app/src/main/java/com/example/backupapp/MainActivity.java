package com.example.backupapp;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends FragmentActivity {

    private ViewPager2 viewPager;
    private PageAdapter pageAdapter;

    // It might be better to use an enum, but for now, constants!
    private static final int GET_LOCAL_LOCATION = 0;

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

    /* A bunch of static functions to implement */
    // https://stackoverflow.com/questions/20715503/get-result-from-activity-called-with-intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.GET_LOCAL_LOCATION) {
            if (resultCode == RESULT_OK) {
                Log.d("THISISSOMETHING", data.getData().toString());
            }
        }
    }
}