package com.example.backupapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import android.widget.Button;

// Loads the settings fragment xml as it's view
// https://developer.android.com/training/animation/screen-slide-2
public class SettingsFragmentClass extends Fragment {
    private int fragmentXMLResourceID = -1;

    // Default constructor
    public SettingsFragmentClass() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }
}