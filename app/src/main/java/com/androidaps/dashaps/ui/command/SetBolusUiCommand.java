package com.androidaps.dashaps.ui.command;

import com.androidaps.dashaps.MainApp;
import com.androidaps.dashaps.R;
import com.androidaps.dashaps.data.PodCommandUIType;
import com.androidaps.dashaps.ui.fragments.OverviewFragment;
import com.androidaps.dashaps.ui.fragments.treatment.MainTreatmentFragment;
import com.androidaps.dashaps.ui.util.DashUIUtil;

import org.joda.time.Duration;

import java.util.Date;
import java.util.GregorianCalendar;

import info.nightscout.androidaps.data.DetailedBolusInfo;
import info.nightscout.androidaps.data.PumpEnactResult;
import info.nightscout.androidaps.plugins.pump.common.utils.DateTimeUtil;
import info.nightscout.androidaps.plugins.pump.omnipod.driver.OmnipodPumpStatus;

public class SetBolusUiCommand extends PodCommandQueueUi {

    private double amount;
    PumpEnactResult response;
    private boolean cancelled = false;
    double delivered;
    long started = 0;

    public SetBolusUiCommand(double amount) {
        this.amount = amount;
    }


    @Override
    public PodCommandUIType getCommandType() {
        return PodCommandUIType.SetBolus;
    }

    @Override
    public void executeCommand() {

        OmnipodPumpStatus pumpStatus = MainApp.getPumpStatus();

        Duration duration = DashUIUtil.calculateBolusDuration(amount);

        pumpStatus.lastBolusTime = new Date();
        pumpStatus.lastBolusAmount = amount;
        pumpStatus.lastBolusEstimatedEndTime = DateTimeUtil.getTimeInFutureFromSeconds((int) duration.getStandardSeconds());

        started = System.currentTimeMillis();

        DetailedBolusInfo detailedBolusInfo = new DetailedBolusInfo();
        detailedBolusInfo.insulin = amount;
        detailedBolusInfo.isSMB = false;
        detailedBolusInfo.date = started;

        response = MainApp.getOmnipodDashCommunicationManager().setBolus(detailedBolusInfo);


        if (response.success) {
            expireAt = DateTimeUtil.getTimeInFutureFromSeconds((int) duration.getStandardSeconds());
        } else {
            expireAt = DateTimeUtil.getTimeInFutureFromSeconds(30);
        }

        MainApp.instance().addCommandToQueue(this);
    }


    @Override
    public void setCancelled() {
        this.cancelled = true;

        long diff = System.currentTimeMillis() - started;

        diff /= 1000;

        this.delivered = DashUIUtil.getAmountFromDeliveryTime((int) diff);
        expireAt = DateTimeUtil.getTimeInFutureFromSeconds(10);
    }


    @Override
    public void updateUi(OverviewFragment overviewFragment) {
        if (cancelled)
            overviewFragment.setBolus(MainApp.gs(R.string.bolus_cancelled, String.format("%.1f", delivered)));
        else if (response.success)
            overviewFragment.setBolus(MainApp.gs(R.string.bolus_delivering, String.format("%.1f", amount)));
        else
            overviewFragment.setBolus("Error setting Bolus: " + response.comment);
    }

    @Override
    public void updateUi(MainTreatmentFragment treatmentFragment) {
        if (cancelled)
            treatmentFragment.setBolus(UiStatusType.AllDisabled);
        else if (response.success)
            treatmentFragment.setBolus(UiStatusType.CancelEnabled);
        else
            treatmentFragment.setBolus(UiStatusType.AllDisabled);
    }

    @Override
    public void updateUiOnFinalize(OverviewFragment overviewFragment) {
        if (cancelled)
            overviewFragment.setBolus(MainApp.gs(R.string.bolus_cancelled, String.format("%.1f", delivered)));
        else if (response.success)
            overviewFragment.setBolus(String.format("Delivered %.1f U (at %s)", amount, DateTimeUtil.toString(new GregorianCalendar())));
        else
            overviewFragment.setBolus("Error setting Bolus: " + response.comment);
    }

    @Override
    public void updateUiOnFinalize(MainTreatmentFragment treatmentFragment) {
        treatmentFragment.setBolus(UiStatusType.StartEnabled);
    }


}
