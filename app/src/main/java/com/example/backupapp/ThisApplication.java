package com.example.backupapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
* The "Application" class of the BackupApp, which we will use to store global variables and methods
*/
public class ThisApplication extends Application {
    public String configLocation = "";
    public Context context = null;
    public enum BACKUP_LOC_TYPES { SAMBA }; // Add to here when implementing more types

    public void setConfigLocation(String configLoc) {
        this.configLocation = configLoc;
        this.context = this.getApplicationContext();
    }

    // Get the content of the configuration file
    // I put this here because I use it in two places
    public Map<String, String> getConfig() throws IOException {
        Map<String, String> configurationSettings = new HashMap<String, String>();
        FileReader fileReader = new FileReader(this.configLocation);
        BufferedReader reader = new BufferedReader(fileReader);
        String nextLineOfConfig = reader.readLine();
        while(nextLineOfConfig != null) {
            // Get category and value of the setting represented by the line of the configuration file
            int separated_at = nextLineOfConfig.indexOf(':');
            String category = nextLineOfConfig.substring(0, separated_at);
            String value = nextLineOfConfig.substring(separated_at + 2); // the "+ 2" helps us skip the ": " characters in the line (not just those before it)
            configurationSettings.put(category, value);

            // Try to get the next line
            nextLineOfConfig = reader.readLine();
        }
        reader.close();
        return configurationSettings;
    }

    // Gets the content of the configuration file
    // I put this here because I use it in two places
    public void saveConfig(Map<String, String> configuration) throws IOException {
        FileWriter writer = new FileWriter(this.configLocation);
        for (Map.Entry<String, String> entry : configuration.entrySet()) {
            writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
        }
        writer.close();
    }
}
