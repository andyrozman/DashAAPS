package com.androidaps.dashaps.ui.command;

import android.os.SystemClock;
import android.util.Log;

import com.androidaps.dashaps.data.PodCommandUIType;

public abstract class PodCommandUi {

    public String TAG = "PodCommandUi";

    public PodCommandUi() {
    }


    public abstract PodCommandUIType getCommandType();


    public void execute() {

        new Thread(() -> {

            Log.i(TAG, "Start command - " + getCommandType().name());

            executeCommand();

            Log.i(TAG, "End command - " + getCommandType().name());

        }).start();

    }


    public abstract void executeCommand();


}
