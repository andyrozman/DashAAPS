package com.androidaps.dashaps;

import android.app.Application;
import android.content.Intent;
import android.content.res.Resources;

import com.androidaps.dashaps.ui.command.PodCommandQueueUi;

import info.nightscout.androidaps.plugins.pump.omnipod.driver.OmnipodPumpStatus;
import info.nightscout.androidaps.plugins.pump.omnipod_dash.comm.OmnipodDashCommunicationManager;

public class MainApp extends Application {

    static DashAPSUiQueue dashAPSUiQueue;

    private static MainApp sInstance;
    //private static ServiceClientConnection serviceClientConnection;
    public static Resources sResources;

    public static OmnipodPumpStatus pumpStatus = null;

    private static OmnipodDashCommunicationManager omnipodDashCommunicationManager;

    public static DashAPSUiQueue getQueue() {
        return dashAPSUiQueue;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        sResources = getResources();

        pumpStatus = new OmnipodPumpStatus(null);

        omnipodDashCommunicationManager = new OmnipodDashCommunicationManager(getApplicationContext());

        dashAPSUiQueue = new DashAPSUiQueue();

        startService(new Intent(this, DashAapsService.class));

    }

    private Object doSomething() {
        return null;
    }

    private Object handleError() {
        return null;
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

    public static String gs(int id, Object... formatArgs) {
        return sResources.getString(id, formatArgs);
    }


    public static OmnipodPumpStatus getPumpStatus() {
        return pumpStatus;
    }


    public static OmnipodDashCommunicationManager getOmnipodDashCommunicationManager() {
        return omnipodDashCommunicationManager;
    }

    public static void addCommandToQueue(PodCommandQueueUi command) {
        dashAPSUiQueue.addToQueue(command);
    }

    public static void startChangeCommand(DashAPSUiQueue.QueueCommand queueCommand) {
        dashAPSUiQueue.startChangeCommand(queueCommand);
    }
}
