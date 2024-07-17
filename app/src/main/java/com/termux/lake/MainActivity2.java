package com.termux.lake;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.termux.sky.SharedPrefManager;

public class MainActivity2 extends Activity {
    private SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Start the Termux service
        //Intent serviceIntent = new Intent();
        //serviceIntent.setClassName("com.termux", "com.termux.app.TermuxService");
        //.setAction("com.termux.service_execute");
        //startService(serviceIntent);

        sharedPrefManager = new SharedPrefManager(this);

        sharedPrefManager.saveText("vex", "NORMAL HAI RE BABA");

        String value = sharedPrefManager.getText("vex");

        // Show a toast notification
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show();

        // Open Google Calculator app
       // Intent calculatorIntent = new Intent();
       // calculatorIntent.setClassName("com.google.android.calculator", "com.android.calculator2.Calculator");
        //startActivity(calculatorIntent);

        // Finish the activity after a delay (example: 3 seconds)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                //Intent exitIntent = new Intent(this, TermuxService.class).setAction(TermuxConstants.TERMUX_APP.TERMUX_SERVICE.ACTION_STOP_SERVICE);
            }
        }, 3000); // 3000 milliseconds = 3 seconds
    }
}
