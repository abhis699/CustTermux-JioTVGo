package com.termux.sky;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.termux.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IptvSelectorActivity extends ListActivity {

    private List<String> installedApps;
    private ArrayAdapter<String> adapter;
    private String selectedApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iptv_selector);

        // Initialize the list of installed apps
        installedApps = getInstalledApps();

        // Set up the ListView with the adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, installedApps);
        setListAdapter(adapter);

        // Handle item click events
        ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedApp = installedApps.get(position);
                saveSelectedAppToFile(selectedApp);
                Toast.makeText(getApplicationContext(), "Selected: " + selectedApp, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<String> getInstalledApps() {
        List<String> apps = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> appList = packageManager.queryIntentActivities(mainIntent, 0);
        for (ResolveInfo info : appList) {
            apps.add(info.activityInfo.packageName);
        }
        return apps;
    }

    private void saveSelectedAppToFile(String selectedApp) {
        File file = new File(getFilesDir(), "selected_app.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(selectedApp);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
