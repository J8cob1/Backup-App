package com.example.backupapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.core.util.LogWriter;
import androidx.fragment.app.Fragment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

// Loads the settings fragment xml as it's view
// https://developer.android.com/training/animation/screen-slide-2
public class SettingsFragmentClass extends Fragment {
    private int fragmentXMLResourceID = -1;
    private String configLocation = "";
    private String filesDir = "";
    private enum box {Checked, NotChecked};

    /*
    * Load settings to persistent storage
    */
    private void loadSettings(View settingsView) {
        // Get view items
        Spinner typeSpinner = settingsView.findViewById(R.id.settings_type_spinner);
        EditText locationEditText = settingsView.findViewById(R.id.settings_location_textbox);
        CheckBox textsCheckbox = settingsView.findViewById(R.id.settings_texts_checkbox);
        CheckBox photoVideoCheckbox = settingsView.findViewById(R.id.settings_photo_video_checkbox);
        CheckBox contactsCheckbox = settingsView.findViewById(R.id.settings_contacts_checkbox);
        CheckBox appsCheckbox = settingsView.findViewById(R.id.settings_apps_checkbox);

        // Set the values of the view items based on the saved text
        // Try reading from the configuration file. Load default settings on failure
        try {
            // Read in config file and save it in memory as a string
            byte[] data = new byte[512];
            FileInputStream reader = new FileInputStream(this.configLocation);
            reader.read(data);
            String config = new String(data);

            Log.d("Settings", config);
        } catch(java.io.IOException e) {
            typeSpinner.setSelection(0);
            locationEditText.setText("Samba Share");
            textsCheckbox.setChecked(true);
            photoVideoCheckbox.setChecked(true);
            contactsCheckbox.setChecked(true);
            appsCheckbox.setChecked(true);

            Log.w("Settings", "No configuration file found. Loaded default settings. Error: " + e.toString());
        }
    }

    /*
     * Save settings to persistent storage
     */
    private void saveSettings(View settingsView) {
        // Try to write settings to file
        try {
            // Gather the settings from the view
            Spinner typeSpinner = settingsView.findViewById(R.id.settings_type_spinner);
            EditText locationEditText = settingsView.findViewById(R.id.settings_location_textbox);
            CheckBox textsCheckbox = settingsView.findViewById(R.id.settings_texts_checkbox);
            CheckBox photoVideoCheckbox = settingsView.findViewById(R.id.settings_photo_video_checkbox);
            CheckBox contactsCheckbox = settingsView.findViewById(R.id.settings_contacts_checkbox);
            CheckBox appsCheckbox = settingsView.findViewById(R.id.settings_apps_checkbox);

            long selectedType = typeSpinner.getSelectedItemId();
            String location = locationEditText.getText().toString();
            boolean saveTexts = textsCheckbox.isChecked();
            boolean savePhotosAndVideos = photoVideoCheckbox.isChecked();
            boolean saveContacts = contactsCheckbox.isChecked();
            boolean saveApps = appsCheckbox.isChecked();

            // Save the settings to file
            //FileOutputStream outputFile = getContext().openFileOutput("settings.txt", Context.MODE_PRIVATE);
            //OutputStreamWriter outputStream = new OutputStreamWriter(outputFile);
            FileWriter writer = new FileWriter(getActivity().getFilesDir().toString() + "/settings.txt");
            writer.write("BackupLocType: " + selectedType);
            writer.write("BackupLoc: " + location);
            writer.write("SaveTexts: " + saveTexts);
            writer.write("SavePhotosVideos: " + savePhotosAndVideos);
            writer.write("SaveContacts: " + saveContacts);
            writer.write("SaveApps: " + saveApps);
            writer.flush();
            writer.close();
        } catch(java.io.FileNotFoundException e) {
            Log.e("Settings", "Failed to save settings to file: " + e.toString());
        } catch(java.io.IOException e) {
            Log.e("Settings", "Something went wrong closing the outputFile. Error: " + e.toString());
        }

        // https://stackoverflow.com/questions/14376807/read-write-string-from-to-a-file-in-android
    }

    // Default constructor
    public SettingsFragmentClass() {
        // Load settings data
        //Log.d("Test", getActivity().getFilesDir().toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Save configuration file location. We will need this for saving and loading files
        this.configLocation = getActivity().getFilesDir().toString() + "/config.txt";

        // Generate view and load existing settings, if they are any
        View view = inflater.inflate(R.layout.settings_fragment, container, false);

        this.saveSettings(view);
        this.loadSettings(view);

        return view;
    }

    // The peeps at StackOverflow say that I can override the onAttach method to access the activity right after it loads
    public void onCreate() {
        Log.d("Test", "SDFDS");
    }
}