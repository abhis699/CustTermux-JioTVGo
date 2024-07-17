package com.termux.sky;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

public class LoginOTP extends Activity {

    private static final int DELAY_MILLIS = 3000;  // Delay in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // First dialog to enter phone number
        showPhoneEntryDialog();
    }

    private void showPhoneEntryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Phone Number");

        // Create an EditText for phone number input
        final EditText phoneNumberInput = new EditText(this);
        phoneNumberInput.setInputType(InputType.TYPE_CLASS_PHONE);  // Set phone number input type
        builder.setView(phoneNumberInput);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String phoneNumber = phoneNumberInput.getText().toString().trim();

                // Simulate sending OTP (replace with actual implementation later)
                sendOTP(phoneNumber);  // Placeholder function call

                // Create a handler for the delay
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showVerificationDialog(phoneNumber);  // Pass the phone number after delay
                    }
                }, DELAY_MILLIS);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                showLoginunSuccessDialog();
            }
        });

        builder.create().show();
    }

    private void showVerificationDialog(final String phoneNumber) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter OTP");

        // Create an EditText for OTP input
        final EditText otpInput = new EditText(this);
        otpInput.setInputType(InputType.TYPE_CLASS_NUMBER);  // Set number input type
        builder.setView(otpInput);

        builder.setPositiveButton("Verify", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String enteredOTP = otpInput.getText().toString().trim();

                // Simulate OTP verification (replace with actual implementation later)
                if (verifyOTP(phoneNumber, enteredOTP)) {
                    Toast.makeText(com.termux.sky.LoginOTP.this, "OTP verified successfully!", Toast.LENGTH_SHORT).show();
                    showLoginSuccessDialog();
                } else {
                    Toast.makeText(com.termux.sky.LoginOTP.this, "Invalid OTP. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                showLoginunSuccessDialog();
            }
        });

        builder.create().show();
    }

    // Placeholder function for sending OTP (replace with actual implementation)
    private void sendOTP(String phoneNumber) {
        // Implement your logic to send OTP to the phone number (e.g., using SMS API)
        Toast.makeText(this, "Sending OTP to " + phoneNumber + " (placeholder)", Toast.LENGTH_SHORT).show();
    }

    // Placeholder function for verifying OTP (replace with actual implementation)
    private boolean verifyOTP(String phoneNumber, String enteredOTP) {
        // Implement your logic to verify the entered OTP against the actual OTP received by the user (e.g., from server)
        // This function should return true if the OTP is valid, false otherwise.
        Toast.makeText(this, "Sending OTP to " + phoneNumber + "___"+ enteredOTP+" (placeholder)", Toast.LENGTH_SHORT).show();
        return true;  // Placeholder return value
    }
    private void showLoginSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Login Successful!");
        builder.setMessage("You are now logged in.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.create().show();

    }

    private void showLoginunSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Login Unsuccessful!");
        builder.setMessage("Failed to login.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.create().show();

    }

}
