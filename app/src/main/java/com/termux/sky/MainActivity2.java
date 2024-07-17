package com.termux.sky;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.termux.R;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    private Button run_termux;
    private Button updateButton;
    private Button loginButton;
    private Button openWebButton;
    private Button button5;
    private Button button6;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                //launchIptvSelectorActivity();
                TheShowRunner();
                break;
            case R.id.button2:
                // Handle update button click
                ScriptRunner();
                break;
            case R.id.button3:
                // Handle login button click
                break;
            case R.id.button4:
                // Handle open web page button click
                break;
            case R.id.button5:
                // Handle button 5 click
                restart_app();
                break;
            case R.id.button6:
                // Handle button 6 click
                exit_app();
                break;
            default:
                break;
        }
    }



    private void ScriptRunner() {


        Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();

        //Intent intent = new Intent();
        //intent.setClassName(this, "com.termux.app.TermuxActivity");
        //startActivity(intent);


        Intent intent = new Intent();
        intent.setClassName("com.termux", "com.termux.app.RunCommandService");
        intent.setAction("com.termux.RUN_COMMAND");
        //intent.putExtra("com.termux.RUN_COMMAND_PATH", "/data/data/com.termux/files/usr/bin/top");
        intent.putExtra("com.termux.RUN_COMMAND_PATH", "/data/data/com.termux/files/home/.autoscript.sh");
        intent.putExtra("com.termux.RUN_COMMAND_ARGUMENTS", new String[]{});
        intent.putExtra("com.termux.RUN_COMMAND_WORKDIR", "/data/data/com.termux/files/home");
        intent.putExtra("com.termux.RUN_COMMAND_BACKGROUND", false);
        intent.putExtra("com.termux.RUN_COMMAND_SESSION_ACTION", "0");
        startService(intent);




        Toast.makeText(this, "end", Toast.LENGTH_SHORT).show();

        TheShowRunner();
    }

    private void TheShowRunner() {
        // Show a toast notification
        Toast.makeText(this, "Starting Server", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent();
        intent.setClassName(this, "com.termux.app.TermuxActivity");
        startActivity(intent);

        Toast.makeText(this, "Starting IPTV", Toast.LENGTH_SHORT).show();

        //Intent intent2 = new Intent(this, MainActivity.class);
        //startActivity(intent2);

    }



    private void launchIptvSelectorActivity() {

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

        // Show a toast notification
        Toast.makeText(this, "EXITED", Toast.LENGTH_SHORT).show();



    }
}
