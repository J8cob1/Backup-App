package com.example.backupapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

// Loads the settings fragment xml
// https://developer.android.com/training/animation/screen-slide-2
public class SettingsFragmentClass extends Fragment {
    private ThisApplication app = null;

    /*
    * Load saved settings from persistent storage
    * - settingsView: the view for the settings fragment page
    */
    private void loadSettings(View settingsView) {
        // Read in the configuration file settings
        Map<String, String> settings = new HashMap<String, String>();
        try {
            settings = this.app.getConfig();
        } catch(java.io.FileNotFoundException e) {
            Log.e("Settings", "Config location not set!");
        } catch(java.io.IOException e) {
            Log.e("Application", "Something went wrong trying to read the config file!");
        }

        // Get UI elements
        Spinner typeSpinner = settingsView.findViewById(R.id.settings_type_spinner);
        EditText locationEditText = settingsView.findViewById(R.id.settings_location_textbox);
        CheckBox textsCheckbox = settingsView.findViewById(R.id.settings_texts_checkbox);
        CheckBox photoVideoCheckbox = settingsView.findViewById(R.id.settings_photo_video_checkbox);
        CheckBox contactsCheckbox = settingsView.findViewById(R.id.settings_contacts_checkbox);
        CheckBox appsCheckbox = settingsView.findViewById(R.id.settings_apps_checkbox);

        // Set some defaults, just in case we are missing a setting or two
        typeSpinner.setSelection(0);
        locationEditText.setText("");
        textsCheckbox.setChecked(true);
        photoVideoCheckbox.setChecked(true);
        contactsCheckbox.setChecked(true);
        appsCheckbox.setChecked(true);

        // For each setting we were given, overload the appropriate UI element
        // https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
        for (Map.Entry<String, String> entry : settings.entrySet()) {
            // Declaring some variables up here, since I can't declare separate variables within the case statements
            boolean isChecked = false;
            int selection = 0;

            // For each category, set the given value
            String value = entry.getValue();
            switch (entry.getKey()) {
                case "BackupLocType":
                    selection = Integer.parseInt(value);
                    typeSpinner.setSelection(selection);
                    break;
                case "BackupLoc":
                    locationEditText.setText(value);
                    break;
                case "SaveTexts":
                    isChecked = Boolean.parseBoolean(value);
                    textsCheckbox.setChecked(isChecked);
                    break;
                case "SavePhotosVideos":
                    isChecked = Boolean.parseBoolean(value);
                    photoVideoCheckbox.setChecked(isChecked);
                    break;
                case "SaveContacts":
                    isChecked = Boolean.parseBoolean(value);
                    contactsCheckbox.setChecked(isChecked);
                    break;
                case "SaveApps":
                    isChecked = Boolean.parseBoolean(value);
                    appsCheckbox.setChecked(isChecked);
                    break;
            }
        }
    }


    /*
     * Save settings to persistent storage
     */
    public void saveSettings(View settingsView) {
        // Set settings
        Map<String, String> settings = new HashMap<String, String>();

        Spinner typeSpinner = settingsView.findViewById(R.id.settings_type_spinner);
        EditText locationEditText = settingsView.findViewById(R.id.settings_location_textbox);
        CheckBox textsCheckbox = settingsView.findViewById(R.id.settings_texts_checkbox);
        CheckBox photoVideoCheckbox = settingsView.findViewById(R.id.settings_photo_video_checkbox);
        CheckBox contactsCheckbox = settingsView.findViewById(R.id.settings_contacts_checkbox);
        CheckBox appsCheckbox = settingsView.findViewById(R.id.settings_apps_checkbox);

        settings.put("BackupLocType", Long.toString(typeSpinner.getSelectedItemId()));
        settings.put("BackupLoc", locationEditText.getText().toString());
        settings.put("SaveTexts", Boolean.toString(textsCheckbox.isChecked()));
        settings.put("SavePhotosVideos", Boolean.toString(photoVideoCheckbox.isChecked()));
        settings.put("SaveContacts", Boolean.toString(contactsCheckbox.isChecked()));
        settings.put("SaveApps", Boolean.toString(appsCheckbox.isChecked()));

        // Save the settings to file;
        try {
            this.app.saveConfig(settings);
        } catch(java.io.FileNotFoundException e) {
            Log.e("Settings", "Failed to save settings to file: " + e.toString());
        } catch(java.io.IOException e) {
            Log.e("Settings", "Something went wrong closing the outputFile. Error: " + e.toString());
        }

        // https://stackoverflow.com/questions/14376807/read-write-string-from-to-a-file-in-android
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Generate view and load existing settings, if they are any
        View view = inflater.inflate(R.layout.settings_fragment, container, false);

        // Save configuration file location. We will need this for saving and loading files
        this.app = (ThisApplication) this.getActivity().getApplication();
        this.app.setConfigLocation(getActivity().getFilesDir().toString() + "/config.txt");

        // Load configuration settings
        this.loadSettings(view);

        // Set the save button listener
        Button saveButton = view.findViewById(R.id.settings_save_button);
        SettingsFragmentClass thisClass = this;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisClass.saveSettings(view);
            }
        });

        return view;
    }
}

/*
* Thank you stack overflow, w3schools, GeeksForGeeks, and official android developer documentation for the helpful posts/pages
*/