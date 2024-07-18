package com.termux.sky;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.termux.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button run_termux;
    private Button updateButton;
    private Button loginButton;
    private Button openWebButton;
    private Button button5;
    private Button button6;

    private Handler handler;

    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        run_termux = findViewById(R.id.button1);
        updateButton = findViewById(R.id.button2);
        loginButton = findViewById(R.id.button3);
        openWebButton = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);

        // Set OnClickListener for all buttons
        run_termux.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        openWebButton.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);




        // Show the StartGameDialogFragment when the app opens
        FragmentManager fragmentManager = getSupportFragmentManager();
        StartDialogFragment dialog = new StartDialogFragment();
        //dialog.show(fragmentManager, "START_DIALOG");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1: // DONE
                // Main server starter
                TheShowRunner();
                break;
            case R.id.button2: // DONE
                // Webpage opener
                runWebPage();
                break;
            case R.id.button3:
                // Login-OTP
                //Intent intent = new Intent(this, com.termux.lake.MainActivity.class);
               // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear existing activities
               // startActivity(intent);

                Intent intentv = new Intent(this, com.termux.sky.LoginOTP.class);
                intentv.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear existing activities
                startActivity(intentv);

                break;
            case R.id.button4:
                //Change IPTV
                iptv();
                break;
            case R.id.button5:
                // RE-INSTALL
                reInstall();
                break;
            case R.id.button6: // DONE
                // EXIT
                exit_app();
                break;
            default:
                break;
        }
    }

    private void stopper() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                //Intent IPTVIntent = new Intent();
                //IPTVIntent.setClassName("se.hedekonsult.sparkle", "se.hedekonsult.sparkle.MainActivity");
                //startActivity(IPTVIntent);

                //finish();
            }
        };
        handler.postDelayed(runnable, 200);
    }

    private void iptv() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Intent IPTVIntent = new Intent();
                IPTVIntent.setClassName("com.termux", "com.termux.sky.IptvSelectorActivity");
                startActivity(IPTVIntent);

                //finish();
            }
        };
        handler.postDelayed(runnable, 200);
    }

    private void ScriptRunner() {
        // Stop the Termux service
        Intent termuxServiceIntent = new Intent();
        termuxServiceIntent.setClassName("com.termux", "com.termux.app.TermuxService");
        termuxServiceIntent.setAction("com.termux.service_stop");
        startService(termuxServiceIntent);


        // Start the Termux service
        Intent serviceIntent = new Intent();
        serviceIntent.setClassName("com.termux", "com.termux.app.TermuxService");
        serviceIntent.setAction("com.termux.service_execute");
        startService(serviceIntent);
    }

    private void TheShowRunner2() {
        //((MainActivity) getActivity()).TheShowRunner();

    }



    private void reInstall() {
        // Show the StartGameDialogFragment when the app opens
        // Stop the Termux service
        Intent termuxServiceIntent = new Intent();
        termuxServiceIntent.setClassName("com.termux", "com.termux.app.TermuxService");
        termuxServiceIntent.setAction("com.termux.service_stop");
        startService(termuxServiceIntent);

        stopper();

        Intent intentX = new Intent();
        intentX.setClassName("com.termux", "com.termux.app.RunCommandService");
        intentX.setAction("com.termux.RUN_COMMAND");
        intentX.putExtra("com.termux.RUN_COMMAND_PATH", "/data/data/com.termux/files/usr/bin/rm");
        intentX.putExtra("com.termux.RUN_COMMAND_ARGUMENTS", new String[]{"-rf", "/data/data/com.termux/files/home/.jiotv_go/"});
        intentX.putExtra("com.termux.RUN_COMMAND_WORKDIR", "/data/data/com.termux/files/home");
        intentX.putExtra("com.termux.RUN_COMMAND_BACKGROUND", false);
        intentX.putExtra("com.termux.RUN_COMMAND_SESSION_ACTION", "0");
        startService(intentX);

        stopper();

        // Start the Termux service
        Intent serviceIntent = new Intent();
        serviceIntent.setClassName("com.termux", "com.termux.app.TermuxService");
        serviceIntent.setAction("com.termux.service_execute");
        startService(serviceIntent);

    }

    void reInstall_confirm() {
        // Show the StartGameDialogFragment when the app opens

        Intent intent = new Intent();
        intent.setClassName("com.termux", "com.termux.app.RunCommandService");
        intent.setAction("com.termux.RUN_COMMAND");
        intent.putExtra("com.termux.RUN_COMMAND_PATH", "/data/data/com.termux/files/usr/bin/top");
        intent.putExtra("com.termux.RUN_COMMAND_ARGUMENTS", new String[]{"-n", "5"});
        intent.putExtra("com.termux.RUN_COMMAND_WORKDIR", "/data/data/com.termux/files/home");
        intent.putExtra("com.termux.RUN_COMMAND_BACKGROUND", false);
        intent.putExtra("com.termux.RUN_COMMAND_SESSION_ACTION", "0");

// Create the pending intent
        Intent resultIntent = new Intent("com.yourapp.RUN_COMMAND_RESULT");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        intent.putExtra("com.termux.service.EXTRA_PENDING_INTENT", pendingIntent);

        startService(intent);


    }

    private void restart_app() {

        // Stop the Termux service
        Intent termuxServiceIntent = new Intent();
        termuxServiceIntent.setClassName("com.termux", "com.termux.app.TermuxService");
        termuxServiceIntent.setAction("com.termux.service_stop");
        startService(termuxServiceIntent);

        // Show a toast notification
        Toast.makeText(this, "Waiting for 5 sec", Toast.LENGTH_SHORT).show();


        // Start the Termux service
        Intent serviceIntent = new Intent();
        serviceIntent.setClassName("com.termux", "com.termux.app.TermuxService");
        serviceIntent.setAction("com.termux.service_execute");
        startService(serviceIntent);
    }

    private void exit_app() {

        // Stop the Termux service
        Intent termuxServiceIntent = new Intent();
        termuxServiceIntent.setClassName("com.termux", "com.termux.app.TermuxService");
        termuxServiceIntent.setAction("com.termux.service_stop");
        startService(termuxServiceIntent);

        stopper();

        // Show a toast notification
        Toast.makeText(this, "✨ CustTermux Exited", Toast.LENGTH_SHORT).show();

        finish();
    }

    public void TheShowRunner() {

        Intent intentC = new Intent();
        intentC.setClassName("com.termux", "com.termux.app.RunCommandService");
        intentC.setAction("com.termux.RUN_COMMAND");
        intentC.putExtra("com.termux.RUN_COMMAND_PATH", "/data/data/com.termux/files/home/login.sh");
        intentC.putExtra("com.termux.RUN_COMMAND_ARGUMENTS", new String[]{"verifylogin"});
        intentC.putExtra("com.termux.RUN_COMMAND_WORKDIR", "/data/data/com.termux/files/home");
        intentC.putExtra("com.termux.RUN_COMMAND_BACKGROUND", false);
        intentC.putExtra("com.termux.RUN_COMMAND_SESSION_ACTION", "0");
        startService(intentC);

        stopper();

        Intent intentC2 = new Intent();
        intentC2.setClassName("com.termux", "com.termux.app.RunCommandService");
        intentC2.setAction("com.termux.RUN_COMMAND");
        intentC2.putExtra("com.termux.RUN_COMMAND_PATH", "/data/data/com.termux/files/home/login.sh");
        intentC2.putExtra("com.termux.RUN_COMMAND_ARGUMENTS", new String[]{"verifyiptv"});
        intentC2.putExtra("com.termux.RUN_COMMAND_WORKDIR", "/data/data/com.termux/files/home");
        intentC2.putExtra("com.termux.RUN_COMMAND_BACKGROUND", false);
        intentC2.putExtra("com.termux.RUN_COMMAND_SESSION_ACTION", "0");
        startService(intentC2);

        stopper();


        // Implement the MAXI function here
        Toast.makeText(this, "RUN MAXi", Toast.LENGTH_SHORT).show();

        // Stop the Termux service
        Intent termuxServiceIntent = new Intent();
        termuxServiceIntent.setClassName("com.termux", "com.termux.app.TermuxService");
        termuxServiceIntent.setAction("com.termux.service_stop");
        startService(termuxServiceIntent);



        Toast.makeText(this, "RUN MAXi - mid", Toast.LENGTH_SHORT).show();



        // Start the Termux service
        Intent serviceIntent = new Intent();
        serviceIntent.setClassName("com.termux", "com.termux.app.TermuxService");
        serviceIntent.setAction("com.termux.service_execute");
        startService(serviceIntent);






        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
               // //Intent IPTVIntent = new Intent();
                //IPTVIntent.setClassName("se.hedekonsult.sparkle", "se.hedekonsult.sparkle.MainActivity");
                //startActivity(IPTVIntent);
                Intent intentX = new Intent();
                intentX.setClassName("com.termux", "com.termux.app.RunCommandService");
                intentX.setAction("com.termux.RUN_COMMAND");
                intentX.putExtra("com.termux.RUN_COMMAND_PATH", "/data/data/com.termux/files/home/login.sh");
                intentX.putExtra("com.termux.RUN_COMMAND_ARGUMENTS", new String[]{"runiptv"});
                intentX.putExtra("com.termux.RUN_COMMAND_WORKDIR", "/data/data/com.termux/files/home");
                intentX.putExtra("com.termux.RUN_COMMAND_BACKGROUND", false);
                intentX.putExtra("com.termux.RUN_COMMAND_SESSION_ACTION", "0");
                startService(intentX);

                //finish();
            }
        };
        handler.postDelayed(runnable, 2000);



        // Open Google Calculator app
        //Intent IPTVIntent = new Intent();
        //IPTVIntent.setClassName("com.google.android.calculator", "com.android.calculator2.Calculator");
        //startActivity(IPTVIntent);

        // Open Google Calculator app


        //Intent intent3 = new Intent();
        //intent.setComponent(new ComponentName("com.google.android.calculator","com.android.calculator2.Calculator"));
        //startActivity(intent3);

        //se.hedekonsult.sparkle/se.hedekonsult.sparkle.MainActivity

        Toast.makeText(this, "RUN MAXi - ended", Toast.LENGTH_SHORT).show();

    }

    private void runWebPage() {

        Toast.makeText(this, "Opening Webpage", Toast.LENGTH_SHORT).show();

        ScriptRunner();

        stopper();

        String url = "http://localhost:5001";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);


    }
}
