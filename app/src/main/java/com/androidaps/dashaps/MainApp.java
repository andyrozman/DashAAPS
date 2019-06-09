package com.androidaps.dashaps;

import android.app.Application;
import android.content.Intent;
import android.content.res.Resources;

public class MainApp extends Application {



    private static MainApp sInstance;
    //private static ServiceClientConnection serviceClientConnection;
    public static Resources sResources;


    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        sResources = getResources();

        startService(new Intent(this, DashAapsService.class));

    }




    public static MainApp instance() {
        return sInstance;
    }


//    public static ServiceClientConnection getServiceClientConnection() {
//        if (serviceClientConnection == null) {
//            serviceClientConnection = new ServiceClientConnection();
//        }
//        return serviceClientConnection;
//    }



    public static String gs(int id) {
        return sResources.getString(id);
    }





}
