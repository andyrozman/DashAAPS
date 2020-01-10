package com.androidaps.dashaps.ui.command;

import com.androidaps.dashaps.MainApp;
import com.androidaps.dashaps.data.PodCommandUIType;
import com.androidaps.dashaps.ui.fragments.OverviewFragment;
import com.androidaps.dashaps.ui.fragments.treatment.MainTreatmentFragment;

import info.nightscout.androidaps.data.PumpEnactResult;
import info.nightscout.androidaps.plugins.pump.common.utils.DateTimeUtil;
import info.nightscout.androidaps.plugins.pump.omnipod.driver.OmnipodPumpStatus;

public class SetTBRUiCommand extends PodCommandQueueUi {

    private double amount;
    PumpEnactResult response;
    private boolean cancelled;
    private long started;


    public SetTBRUiCommand(double amount) {
        this.amount = amount;
    }

    @Override
    public PodCommandUIType getCommandType() {
        return PodCommandUIType.SetTemporaryBasal;
    }

    @Override
    public void executeCommand() {

        OmnipodPumpStatus pumpStatus = MainApp.getPumpStatus();

        pumpStatus.tempBasalStart = System.currentTimeMillis();
        started = pumpStatus.tempBasalStart;

        pumpStatus.tempBasalAmount = amount;
        pumpStatus.tempBasalEnd = DateTimeUtil.getTimeInFutureFromMinutes(30);
        pumpStatus.tempBasalLength = 30;

        response = MainApp.getOmnipodDashCommunicationManager().setTemporaryBasal(pumpStatus.getTemporaryBasal());

        if (response.success) {
            expireAt = pumpStatus.tempBasalEnd;
        } else {
            expireAt = DateTimeUtil.getTimeInFutureFromSeconds(30);
        }

        MainApp.instance().addCommandToQueue(this);

    }

    @Override
    public void setCancelled() {
        this.cancelled = true;
        expireAt = DateTimeUtil.getTimeInFutureFromSeconds(10);
    }


    @Override
    public void updateUi(OverviewFragment overviewFragment) {
        if (cancelled)
            overviewFragment.setTBR("Tbr cancelled");
        else if (response.success)
            overviewFragment.setTBR(String.format("Rate: %.3f U, Time: %d/30 min", amount, getTimeFromBeginning()));
        else
            overviewFragment.setTBR("Error setting Bolus: " + response.comment);
    }

    @Override
    public void updateUi(MainTreatmentFragment treatmentFragment) {
        if (cancelled)
            treatmentFragment.setTBR(UiStatusType.AllDisabled);
        else if (response.success)
            treatmentFragment.setTBR(UiStatusType.CancelEnabled);
        else
            treatmentFragment.setTBR(UiStatusType.AllDisabled);
    }

    @Override
    public void updateUiOnFinalize(OverviewFragment overviewFragment) {
        if (cancelled)
            overviewFragment.setTBR("Tbr cancelled");
        else if (response.success)
            overviewFragment.setTBR(String.format("Rate: %.3f U, Time: 30/30 min", amount));
        else
            overviewFragment.setTBR("Error setting TBR: " + response.comment);
    }

    @Override
    public void updateUiOnFinalize(MainTreatmentFragment treatmentFragment) {
        treatmentFragment.setTBR(UiStatusType.StartEnabled);
    }

    private int getTimeFromBeginning() {
        return (int) (System.currentTimeMillis() - started) / (1000 * 60);
    }
}
