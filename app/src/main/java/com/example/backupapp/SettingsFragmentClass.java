package com.example.backupapp;

import android.content.Context;
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
    private String configLocation = "";

    /*
    *  Set the views using the given map of settings
    *  - values: a map containing the category and value associated with the category
    *  - settingsView: the view for the settings fragment that contains the UI elements we want to load values into
    */
    private void load(Map<String, String> values, View settingsView) {
        // Get UI elements
        Spinner typeSpinner = settingsView.findViewById(R.id.settings_type_spinner);
        EditText locationEditText = settingsView.findViewById(R.id.settings_location_textbox);
        CheckBox textsCheckbox = settingsView.findViewById(R.id.settings_texts_checkbox);
        CheckBox photoVideoCheckbox = settingsView.findViewById(R.id.settings_photo_video_checkbox);
        CheckBox contactsCheckbox = settingsView.findViewById(R.id.settings_contacts_checkbox);
        CheckBox appsCheckbox = settingsView.findViewById(R.id.settings_apps_checkbox);

        // Set some defaults
        typeSpinner.setSelection(0);
        locationEditText.setText("Samba Share");
        textsCheckbox.setChecked(true);
        photoVideoCheckbox.setChecked(true);
        contactsCheckbox.setChecked(true);
        appsCheckbox.setChecked(true);

        // For each setting we were given, overload the appropriate UI element
        // https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
        for (Map.Entry<String, String> entry : values.entrySet()) {
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
    * Load saved settings to persistent storage
    * - settingsView: the view for the settings fragment page
    */
    private void loadSettings(View settingsView) {
        // Set the values of the view items based on the saved text
        // Try reading from the configuration file. Load default settings on failure
        Map<String, String> settings = new HashMap<String, String>();
        try {
            // Read the config file
            FileReader fileReader = new FileReader(this.configLocation);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = reader.readLine();
            while(line != null) {
                // Get category and value of the setting represented by this line of the text and keep track of it
                int separated_at = line.indexOf(':');
                String category = line.substring(0, separated_at);
                String value = line.substring(separated_at + 2); // the "+ 2" helps us skip the ": " characters in the line (not just those before it)
                settings.put(category, value);

                // Process the next line
                line = reader.readLine();
            }
            reader.close();
        } catch(java.io.IOException e) {
            Log.w("Settings", "No configuration file found. Loaded default settings. Error: " + e.toString());
        } finally {
            this.load(settings, settingsView);
        }
    }


    /*
     * Save settings to persistent storage
     */
    public void saveSettings(View settingsView) {
        // Try to write settings to file
        try {
            // Gather the UI elements
            Spinner typeSpinner = settingsView.findViewById(R.id.settings_type_spinner);
            EditText locationEditText = settingsView.findViewById(R.id.settings_location_textbox);
            CheckBox textsCheckbox = settingsView.findViewById(R.id.settings_texts_checkbox);
            CheckBox photoVideoCheckbox = settingsView.findViewById(R.id.settings_photo_video_checkbox);
            CheckBox contactsCheckbox = settingsView.findViewById(R.id.settings_contacts_checkbox);
            CheckBox appsCheckbox = settingsView.findViewById(R.id.settings_apps_checkbox);

            // Get the values for the UI elements
            long selectedType = typeSpinner.getSelectedItemId();
            String location = locationEditText.getText().toString();
            boolean saveTexts = textsCheckbox.isChecked();
            boolean savePhotosAndVideos = photoVideoCheckbox.isChecked();
            boolean saveContacts = contactsCheckbox.isChecked();
            boolean saveApps = appsCheckbox.isChecked();

            // Save the settings to file;
            FileWriter writer = new FileWriter(this.configLocation);
            writer.write("BackupLocType: " + selectedType + '\n');
            writer.write("BackupLoc: " + location + '\n');
            writer.write("SaveTexts: " + saveTexts + '\n');
            writer.write("SavePhotosVideos: " + savePhotosAndVideos + '\n');
            writer.write("SaveContacts: " + saveContacts + '\n');
            writer.write("SaveApps: " + saveApps + '\n');
            writer.close();
        } catch(java.io.FileNotFoundException e) {
            Log.e("Settings", "Failed to save settings to file: " + e.toString());
        } catch(java.io.IOException e) {
            Log.e("Settings", "Something went wrong closing the outputFile. Error: " + e.toString());
        }

        // https://stackoverflow.com/questions/14376807/read-write-string-from-to-a-file-in-android
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Save configuration file location. We will need this for saving and loading files
        this.configLocation = getActivity().getFilesDir().toString() + "/config.txt";

        // Generate view and load existing settings, if they are any
        View view = inflater.inflate(R.layout.settings_fragment, container, false);

        // Load configuration settings
        this.loadSettings(view);

        // Set the save button listener
        // settings_save_button
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