package com.example.backupapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

// Loads the settings fragment xml as it's view
// https://developer.android.com/training/animation/screen-slide-2
public class SettingsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (ViewGroup) inflater.inflate(
                R.layout.settings_fragment, container, false);
    }
}