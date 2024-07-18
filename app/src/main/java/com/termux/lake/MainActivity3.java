package com.termux.lake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.termux.sky.SharedPrefManager;

public class MainActivity extends Activity {
    private SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent IPTVIntent = new Intent();
        IPTVIntent.setClassName("com.termux", "com.termux.sky.IptvSelectorActivity");
        startActivity(IPTVIntent);


        Intent termuxServiceIntent = new Intent();
        termuxServiceIntent.setClassName("com.termux", "com.termux.sky.IptvSelectorActivity");
        startService(termuxServiceIntent);
    }
}
