package com.example.backupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.AlphabeticIndex;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.ScrollView;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    // Variables
    private HashMap<String, Integer> tabMappings = new HashMap<String, Integer>();

    // OnCreate function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup Tab Switching
        this.initializeTabMappings();
        MainActivity thisClass = this;
        TabLayout tab_container = findViewById(R.id.tab_container);
        Log.d("toString", tab_container.toString());
        tab_container.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                thisClass.showTab(tab.getText().toString().toLowerCase());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                thisClass.hideTab(tab.getText().toString().toLowerCase());
            }

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
    private void showTab(String selectedTab) {
        Log.d("toString", selectedTab);
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
    }
}