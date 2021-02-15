package com.example.backupapp;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// A class to hold all of the logic for performing a backup
public class PerformSambaBackup implements Runnable {
    private ThisApplication app;
    private Context context;

    public PerformSambaBackup(ThisApplication application) {
        this.app = application;
        this.context = app.context;
    }

    /*
    * Builds a massive string containing all of the messages in a given inbox/outbox/draftbox
    * - cursor: the ContentResolver cursor that you can use to get the messages
    * Returns: a string with all the message data
    */
    // https://stackoverflow.com/questions/848728/how-can-i-read-sms-messages-from-the-device-programmatically-in-android
    private String extractMessages(Cursor cursor) {
        String allMessageData = "";
        if (cursor.moveToFirst()) {
            do {
                // Construct a line with all data for a message
                String messageData = "";
                for (int index = 0; index < cursor.getColumnCount(); ++index) {
                   messageData += cursor.getColumnName(index) + "# " + cursor.getString(index);
                }

                // Append the text message information to the allMessageData variable
                allMessageData += messageData + "\n";
            } while(cursor.moveToNext());
        }
        return allMessageData;
    }

    @Override
    public void run() {

        // Get configuration information from config file
        Map<String, String> configuration = new HashMap<String, String>();
        try {
            this.app.getConfig();
        } catch (IOException e) {
            Log.e("Backup", "Failed to load configuration file");
            return;
        }

        // Extract needed settings from configuration
        String sambaConfigLoc = configuration.get("BackupLoc");
        boolean saveTexts = Boolean.valueOf(configuration.get("SaveTexts"));
        boolean savePhotosAndVideos = Boolean.valueOf(configuration.get("SavePhotosVideos"));
        boolean saveContacts = Boolean.valueOf(configuration.get("SaveContacts"));
        boolean saveApps = Boolean.valueOf(configuration.get("SaveApps"));

        // Save the texts, if selected
        // https://stackoverflow.com/questions/18353734/getting-all-sms-from-an-android-phone
        if (saveTexts) {
            // https://stackoverflow.com/questions/848728/how-can-i-read-sms-messages-from-the-device-programmatically-in-android
            Uri inbox = Uri.parse("content://sms/inbox");
            Uri sentbox = Uri.parse("content://sms/sent");
            Uri draftbox = Uri.parse("content://sms/draft");
            //String outbox = "content://sms/draft";


            ContentResolver contentResolver = this.context.getContentResolver();
            String inboxMessages = this.extractMessages(contentResolver.query(inbox, null, null, null, null));
            String draftMessages = this.extractMessages(contentResolver.query(sentbox, null, null, null, null));
            String outMessages = this.extractMessages(contentResolver.query(draftbox, null, null, null, null));

            Log.d("Inbox Messages", inboxMessages);
            Log.d("Draft Messages", draftMessages);
            Log.d("Outbox Messages", outMessages);
        }
        // Save the texts, if selected
        if (savePhotosAndVideos) {

        }
        // Save the texts, if selected
        if (saveContacts) {

        }
        if (saveApps) {

        }
    }
}


// https://stackoverflow.com/questions/8102741/android-how-to-use-string-resource-in-a-java-class
// https://stackoverflow.com/questions/10532907/android-retrieve-string-array-from-resources