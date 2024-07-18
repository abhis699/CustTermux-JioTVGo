package com.termux.sky;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.termux.R;

import java.util.ArrayList;
import java.util.List;

public class IptvSelectorActivity extends ListActivity {

    private List<AppInfo> installedApps;
    private MyAdapter adapter;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iptv_selector);

        sharedPrefManager = new SharedPrefManager(getApplicationContext());

        installedApps = getInstalledApps();

        adapter = new MyAdapter(this, R.layout.list_item, installedApps);
        setListAdapter(adapter);

        ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedAppPackage = installedApps.get(position).getPackageName();
                saveSelectedAppToFile(selectedAppPackage);
                Toast.makeText(getApplicationContext(), "Selected: " + selectedAppPackage, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private List<AppInfo> getInstalledApps() {
        List<AppInfo> apps = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> appList = packageManager.queryIntentActivities(mainIntent, 0);

        for (ResolveInfo info : appList) {
            String appName = info.activityInfo.loadLabel(packageManager).toString();
            String packageName = info.activityInfo.packageName;
            Drawable icon = info.activityInfo.loadIcon(packageManager);
            apps.add(new AppInfo(appName, packageName, icon));
        }

        return apps;
    }

    private void saveSelectedAppToFile(String selectedAppPackage) {
        // Same logic as before...
    }

    // Custom adapter class to hold app info and handle icon display
    private class MyAdapter extends ArrayAdapter<AppInfo> {

        private final int layoutResource;

        public MyAdapter(Context context, int resource, List<AppInfo> apps) {
            super(context, resource, apps);
            this.layoutResource = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = getLayoutInflater().inflate(layoutResource, parent, false);
            }

            ImageView appIcon = (ImageView) view.findViewById(R.id.app_icon);
            TextView appName = (TextView) view.findViewById(R.id.app_name);

            AppInfo appInfo = getItem(position);
            appIcon.setImageDrawable(appInfo.getIcon());
            appName.setText(appInfo.getAppName());

            return view;
        }
    }

    // Class to hold app information
    private static class AppInfo {
        private final String appName;
        private final String packageName;
        private final Drawable icon;

        public AppInfo(String appName, String packageName, Drawable icon) {
            this.appName = appName;
            this.packageName = packageName;
            this.icon = icon;
        }

        public String getAppName() {
            return appName;
        }

        public String getPackageName() {
            return packageName;
        }

        public Drawable getIcon() {
            return icon;
        }
    }
}
