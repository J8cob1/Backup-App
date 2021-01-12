package com.example.backupapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

// Loads the settings fragment xml as it's view
// https://developer.android.com/training/animation/screen-slide-2
public class FragmentClass extends Fragment {
    private int fragmentXMLResourceID = -1;

    // Default constructor
    public FragmentClass() {}

    // Non-Default Constructor. Specifies the fragment you want to load
    public FragmentClass(String fragment_type) {
        if (fragment_type.equals("backup")) {
            this.fragmentXMLResourceID = R.layout.backup_fragment;
        } else if (fragment_type.equals("restore")) {
            this.fragmentXMLResourceID = R.layout.restore_fragment; // Fix
        } else if (fragment_type.equals("settings")) {
            this.fragmentXMLResourceID = R.layout.settings_fragment; // Fix
        } else {
            this.fragmentXMLResourceID = 0;
            // Throw exception
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(this.fragmentXMLResourceID, container, false);
    }
}