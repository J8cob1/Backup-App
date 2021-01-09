package com.example.backupapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.*;

import com.google.android.material.tabs.TabLayout;


import java.util.*;

public class MainActivity extends FragmentActivity {

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    // Variables
    private HashMap<String, Integer> tabMappings = new HashMap<String, Integer>();
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;

    class newFragmentStateAdapter extends FragmentStateAdapter {
        private static final int NUM_PAGES = 3;

        public newFragmentStateAdapter(FragmentActivity activity) {
            super(activity);
        }

        @Override
        public Fragment createFragment(int position) {
            return new SettingsFragment();
        }

        @Override
        public int getItemCount() {
            return this.NUM_PAGES;
        }
    }

    // OnCreate function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Stuff for the main activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up viewPager
        this.viewPager = findViewById(R.id.content_container);
        this.pagerAdapter = new newFragmentStateAdapter(this);
        this.viewPager.setAdapter(this.pagerAdapter);


        // Setup Tab Switching
        this.initializeTabMappings();
        MainActivity thisClass = this;
        TabLayout tab_container = findViewById(R.id.tab_container);
        tab_container.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                thisClass.viewPager.setCurrentItem(tab.getPosition());
                Log.d("Test", String.valueOf(thisClass.viewPager.getCurrentItem()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

    }


    /* *********************** */
    /* Tab Switching Functions */
    /* *********************** */

    // Maps tabs to appropriate positions
    // I can't seem to initialize it directly as you would an array, so it's done with a function as suggested by a stack overflow post
    private void initializeTabMappings() {
        this.tabMappings.put("settings", 2);
        this.tabMappings.put("restore", 1);
        this.tabMappings.put("backup", 0);
    }

    // Shows a tab, one that corresponds to a given string
    /*private void showTab(String selectedTab) {
        Log.d("toString", selectedTab);
        this.viewPager.setCurrentItem();
        if (selectedTab.equals("backup")) {
            findViewById(R.id.backup_tab_view).setVisibility(View.VISIBLE);
        } else if (selectedTab.equals("restore")) {
            findViewById(R.id.restore_tab_view).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.settings_tab_view).setVisibility(View.VISIBLE);
        }
    }

    // Hides a tab, one that corresponds to a given string
    private void hideTab(String selectedTab) {
        if (selectedTab.equals("backup")) {
            findViewById(R.id.backup_tab_view).setVisibility(View.INVISIBLE);
        } else if (selectedTab.equals("restore")) {
            findViewById(R.id.restore_tab_view).setVisibility(View.INVISIBLE);
        } else {
            findViewById(R.id.settings_tab_view).setVisibility(View.INVISIBLE);
        }
    }*/
}