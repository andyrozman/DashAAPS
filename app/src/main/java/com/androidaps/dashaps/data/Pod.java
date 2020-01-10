// 
// Decompiled by Procyon v0.5.34
// 

package com.androidaps.dashaps.data;


import com.androidaps.dashaps.enums.PodState;
import com.google.gson.annotations.Expose;

import java.util.concurrent.TimeUnit;

import info.nightscout.androidaps.plugins.pump.common.utils.DateTimeUtil;
import info.nightscout.androidaps.plugins.pump.omnipod.driver.OmnipodPumpStatus;


public class Pod {

    private static final String TAG = "Pod";

    //private int activationState;
    @Expose
    private String address;
    @Expose
    private long lotNumber;
    @Expose
    private String bleVersion;
    @Expose
    private String podVersion;
    @Expose
    private Long activationTime;
    @Expose
    private int podState;

    private Long podExpire;

    private Long podWarning;

    // FIXME Pod will be used instead of OmnipodPumpStatus for now

    public Pod() {

    }

    public void checkPodExpiry() {
        if (getPodStateObject() == PodState.Active) {
            //Long activationTime = this.pod.getActivationTime();

            Long expireTime = DateTimeUtil.getTimeInFutureFromMinutes(activationTime, 3 * 24 * 60);

            if (System.currentTimeMillis() > expireTime) {
                setPodState(PodState.Deactivated);
            } else {
                this.podExpire = expireTime;
                this.podWarning = expireTime - (8 * 24 * 60 * 60 * 1000);
            }
        }
    }


    public boolean checkTimeIsWithinPodLifetime(final Long n) {

        return n == null || n <= 0L || this.getActivationTime() == null || (this.getActivationTime() <= n && n <= this.getActivationTime() + TimeUnit.HOURS.toMillis(80L));
    }


    public Long getActivationTime() {

        return this.activationTime;
    }


    public String getAddress() {

        return this.address;
    }


    public long getLotNumber() {

        return this.lotNumber;
    }


    public void setActivationTime(final Long activationTime) {

        this.activationTime = activationTime;
    }


    public void setAddress(final String address) {

        this.address = address;
    }


    public void setLotNumber(final long lotNumber) {

        this.lotNumber = lotNumber;
    }


    public void setPodState(final int podState) {

        this.podState = podState;
    }


    public void setPodState(final PodState podState) {

        this.podState = podState.getValue();
    }


    public void resetAddress() {

        this.setAddress(null);
    }

//    public void setActivationState(final PodCommManager.ActivationStatus a) {
//
//        this.setActivationState(a.getValue());
//    }

    public PodState getPodStateObject() {
        return PodState.getByValue(this.podState);
    }


//    public void setPulsesRemaining(final Short n) {
//
//        this.setPulsesRemaining(n);
//    }


//    public void setState(final PodStatusType g) {
//
//        this.setPodState(g.name());
//    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pod{");
        sb.append("activationTime=").append(activationTime);
        sb.append(", address='").append(address).append('\'');
        sb.append(", bleVersion='").append(bleVersion).append('\'');
        sb.append(", lotNumber=").append(lotNumber);
        sb.append(", podVersion='").append(podVersion).append('\'');
        sb.append(", podState=").append(podState);
        sb.append('}');
        return sb.toString();
    }

    public void setBleVersion(String bleVersion) {
        this.bleVersion = bleVersion;
    }

    public String getBleVersion() {
        return this.bleVersion;
    }

    public void setPodVersion(String podVersion) {
        this.podVersion = podVersion;
    }

    public String getPodVersion() {
        return this.podVersion;
    }


    public void update(OmnipodPumpStatus pumpStatus, PodUpdateType podUpdateType) {

    }


    public long getPodExpire() {
        return podExpire;
    }

    public void setPodExpire(long podExpire) {
        this.podExpire = podExpire;
    }

    public long getPodWarning() {
        return podWarning;
    }

    public void setPodWarning(long podWarning) {
        this.podWarning = podWarning;
    }

    public boolean isExpired() {
        if (podExpire != null) {
            if (System.currentTimeMillis() > podExpire) {
                setPodState(PodState.Deactivated);
                podExpire = null;
                return true;
            } else
                return false;
        } else
            return true;
    }
}
