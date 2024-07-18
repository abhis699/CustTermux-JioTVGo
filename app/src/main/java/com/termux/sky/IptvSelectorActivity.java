package com.termux.sky;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.termux.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InstalledAppsFragment extends Fragment {

    private ListView appListView;
    private PackageManager packageManager;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_installed_apps, container, false);
        appListView = view.findViewById(R.id.appListView);
        packageManager = requireContext().getPackageManager();
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<AppInfo> installedApps = getInstalledApps();
        AppAdapter adapter = new AppAdapter(requireContext(), installedApps);
        appListView.setAdapter(adapter);

        appListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppInfo selectedApp = installedApps.get(position);
                String pkgName = selectedApp.packageName;

                // Save selected app package name
                saveSelectedAppToFile(pkgName);

                // Show a toast notification
                Toast.makeText(requireContext(), "App saved: " + selectedApp.appName, Toast.LENGTH_SHORT).show();

                // Launch MainActivity
                launchMainActivity();

                // Close the entire app
                requireActivity().finish();
            }
        });
    }

    private void saveSelectedAppToFile(String selectedAppPackage) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("savedAPP", selectedAppPackage);
        editor.apply();
    }

    private List<AppInfo> getInstalledApps() {
        List<AppInfo> apps = new ArrayList<>();
        List<android.content.pm.PackageInfo> packageList = packageManager.getInstalledPackages(PackageManager.GET_META_DATA);
        for (android.content.pm.PackageInfo packageInfo : packageList) {
            String appName = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
            Drawable appIcon = packageManager.getApplicationIcon(packageInfo.applicationInfo);
            String packageName = packageInfo.packageName;
            apps.add(new AppInfo(appName, appIcon, packageName));
        }
        Collections.sort(apps, (app1, app2) -> app1.appName.compareToIgnoreCase(app2.appName));
        return apps;
    }

    private void launchMainActivity() {
        Intent intent = new Intent(requireContext(), MainActivity.class);
        startActivity(intent);
    }

    private static class AppAdapter extends ArrayAdapter<AppInfo> {

        private Context context;

        AppAdapter(Context context, List<AppInfo> apps) {
            super(context, R.layout.item_installed_app, apps);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_installed_app, parent, false);
            }

            AppInfo appInfo = getItem(position);

            TextView appNameTextView = view.findViewById(R.id.appNameTextView);
            ImageView appIconImageView = view.findViewById(R.id.appIconImageView);

            if (appInfo != null) {
                appNameTextView.setText(appInfo.appName);
                appIconImageView.setImageDrawable(appInfo.appIcon);
            }

            return view;
        }
    }

    private static class AppInfo {
        String appName;
        Drawable appIcon;
        String packageName;

        AppInfo(String appName, Drawable appIcon, String packageName) {
            this.appName = appName;
            this.appIcon = appIcon;
            this.packageName = packageName;
        }
    }
}
