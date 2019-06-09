// 
// Decompiled by Procyon v0.5.34
// 

package com.androidaps.dashaps.data;


import com.androidaps.dashaps.enums.PodState;
import com.androidaps.dashaps.enums.ProgramState;
import com.google.common.base.Objects;
import com.google.gson.annotations.Expose;

import org.apache.commons.lang3.NotImplementedException;

import java.util.concurrent.TimeUnit;


public class Pod  {

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





    public Pod() {

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

}
