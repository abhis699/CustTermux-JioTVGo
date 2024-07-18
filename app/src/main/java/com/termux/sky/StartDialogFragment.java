package com.termux.sky;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.termux.R;
import com.termux.app.TermuxActivity;

public class StartDialogFragment extends DialogFragment {
    private Handler handler;
    private Runnable runnable;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_start_text)
            .setPositiveButton(R.string.interupt, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Handle interruption action here.
                    handler.removeCallbacks(runnable); // Stop the runnable if button is clicked
                    dismiss();
                }
            });

        Dialog dialog = builder.create();

        // Schedule to run the MAXI function after 5 seconds
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                //((MainActivity) getActivity()).TheShowRunner();
                work ();
                ((TermuxActivity) getActivity()).work2();
                dismiss();
            }
        };
        handler.postDelayed(runnable, 2000);

        return dialog;
    }
    private void work() {
        Log.d("E", "work: DONE 1 ");

    }

}
