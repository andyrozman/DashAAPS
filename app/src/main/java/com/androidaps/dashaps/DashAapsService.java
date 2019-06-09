package com.androidaps.dashaps;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.androidaps.dashaps.data.Pod;
import com.androidaps.dashaps.data.PodStateDto;
import com.androidaps.dashaps.ui.fragments.OverviewFragment;
import com.androidaps.dashaps.ui.fragments.PodFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.joda.time.LocalDateTime;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import info.nightscout.androidaps.utils.SP;

public class DashAapsService extends Service {

    Binder mBinder;
    boolean serviceRunning;
    int[] time = new int[6];
    private String TAG = "DashAapsService";
    //Gson gson = new Gson();

    public static Gson gsonInstancePretty = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting().create();

    public static Pod pod;
    public static PodStateDto podStateDto;

    static DashAapsService instance = new DashAapsService();

    public DashAapsService() {
    }

    public static DashAapsService getInstance() {
        return instance;
    }


    private void doProcess() {

        checkTime();





    }

    private void checkTime() {

        LocalDateTime ldt = new LocalDateTime();

        //Log.d(TAG, "Check Time: Test");

        int[] timeNew = new int[6];
        timeNew[0] = ldt.getDayOfMonth();
        timeNew[1] = ldt.getMonthOfYear();
        timeNew[2] = ldt.getYear();

        timeNew[3] = ldt.getHourOfDay();
        timeNew[4] = ldt.getMinuteOfHour();
        timeNew[5] = ldt.getSecondOfMinute();

        if (!Arrays.equals(time, timeNew)) {
            OverviewFragment.getInstance().setTime(timeNew);

            if (time[4] != timeNew[4]) {
                // do minute changes actions
                OverviewFragment.getInstance().setLocalDateTime(ldt);

                if (PodFragment.getInstance()!=null)
                    PodFragment.getInstance().setLocalDateTime(ldt);
            }

            time = timeNew;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    private Handler handler;
    private Runnable runnable;
    private final int runTime = 5000;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");

        this.serviceRunning = true;

        loadData();

        new Thread(() -> {

            do {
                SystemClock.sleep(500);
                //Log.d(TAG, "Thread");

                doProcess();

                //Log.i(TAG, "processLoop");

            } while (serviceRunning);

        }).start();

    }



    @Override
    public void onDestroy() {

        super.onDestroy();
        serviceRunning = false;

        //saveData();
    }


    private void loadData() {

        Log.d(TAG, "loadData");

        String data = SP.getString("Pod", null);

        if (data!=null) {
            Log.d(TAG, "loadData - Pod");
            Pod pod = gsonInstancePretty.fromJson(data, Pod.class);
            this.pod = pod;
        }

        //if (pod!=null)
        //    SP.putString("Pod", gsonInstancePretty.toJson(pod));
//
//        if (podStateDto!=null)
//            SP.putString("PodState", gsonInstancePretty.toJson(podStateDto));

    }


    public void saveData() {
        Log.d(TAG, "saveData");

        if (pod!=null) {
            SP.putString("Pod", gsonInstancePretty.toJson(pod));
            Log.d(TAG, "saveData - Pod");
        }



//
//        if (podStateDto!=null)
//            SP.putString("PodState", gsonInstancePretty.toJson(podStateDto));

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(TAG, "onStart");
    }



}
