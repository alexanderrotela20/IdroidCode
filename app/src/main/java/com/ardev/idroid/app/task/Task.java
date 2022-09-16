package com.ardev.idroid.app.task;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.ardev.idroid.R;

public class Task {
    private Runnable preTask, task, postTask;
    private AlertDialog alertDialog;

    public Task() {

    }

    public void setTasks(Runnable preTask, Runnable task, Runnable postTask) {
        this.preTask = preTask;
        this.task = task;
        this.postTask = postTask;
    }

    public void run() {
        new Thread(() -> {
            new Handler(Looper.getMainLooper()).post(preTask);
            task.run();
            new Handler(Looper.getMainLooper()).post(postTask);
        }).start();
    }

    public void dismiss() {
        if (alertDialog != null)
            alertDialog.dismiss();
    }

    @SuppressLint("ResourceType")
    public void showProgressDialog(String msg, Activity activity) {
        alertDialog = new MaterialAlertDialogBuilder(activity)
                .setCancelable(false)
                .setView(getProgressView(msg, activity))
                .show();
    }

    private View getProgressView(String msg, Activity activity) {
        @SuppressLint("InflateParams") View v = activity.getLayoutInflater().inflate(R.layout.progress_view, null);
        ((TextView) v.findViewById(R.id.msg)).setText(msg);
        return v;
    }
}


 // task.setTasks(() -> task.showProgressDialog("Espere...", this), () -> {
            // try {
               
               
            // } catch (Exception exception) {
                 
                 
            // }
        // }, () -> {
            // try {
               
                // }
                 
                // task.dismiss();
            // } catch (Exception exception) {
                // task.dismiss();
               
                 
            // }
        // });
        // task.run();
    
