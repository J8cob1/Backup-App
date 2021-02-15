package com.example.backupapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Loads the settings fragment xml as it's view
// https://developer.android.com/training/animation/screen-slide-2
public class BackupRestoreFragmentClass extends Fragment {
    private int fragmentXMLResourceID = -1;
    private ThisApplication app = null;

    public BackupRestoreFragmentClass() {}

    @Override
    // https://stackoverflow.com/questions/21192386/android-fragment-onclick-button-method
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Get View
        View view = inflater.inflate(R.layout.backup_restore_fragment, container, false);

        // Initiate New BackupButton
        Button newBackupButton = (Button) view.findViewById(R.id.backup_restore_new_backup_button);

        this.app = (ThisApplication) this.getActivity().getApplication();

        BackupRestoreFragmentClass thisClass = this;
        newBackupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                Objects.requireNonNull(getActivity()).startActivityForResult(intent, 0);
                //Uri location = Uri.parse(intent.getData());

                // Create New Backup
                // Starting by exporting a simple text file to disk
                // https://stackoverflow.com/questions/14376807/read-write-string-from-to-a-file-in-android
                try {
                    null == null;
                } catch (java.io.IOException exception) {
                    Log.e("Problem", "Something went wrong creating a new backup");
                }*/
                Log.d("BUTTON", "PRESSED");
                PerformSambaBackup classThread = new PerformSambaBackup(thisClass.app);
                classThread.run();
                //ExecutorService executor = Executors.newSingleThreadExecutor();
                //executor.;
            }
        });
        return view;
    }

}

// https://developer.android.com/guide/navigation/navigation-swipe-view-2
// https://stackoverflow.com/questions/24772095/input-and-output-path-in-android
// https://developer.android.com/guide/topics/providers/document-provider
// https://stackoverflow.com/questions/4186021/how-to-start-new-activity-on-button-click
// https://developer.android.com/reference/android/content/Context