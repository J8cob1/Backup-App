package com.example.backupapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

/*
* The fragment adapter for use with ViewPager2. It is responsible for loading XML fragments
* https://developer.android.com/guide/navigation/navigation-swipe-view-2
* https://www.w3schools.com/java/java_enums.asp
*/
public class PageAdapter extends FragmentStateAdapter {
    private static final int NUM_PAGES = 2;
    private TabLayout tab_bar;

    public PageAdapter(FragmentActivity fragment) { super(fragment); }

    @Override
    @NotNull
    public Fragment createFragment(int position) {
        if (position == 0) { // 0 = Backup
            return new BackupRestoreFragmentClass();
        } else { // 2 = Settings
            return new SettingsFragmentClass();
        }
    }

    @Override
    public int getItemCount() { return this.NUM_PAGES; }
}