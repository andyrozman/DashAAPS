package com.androidaps.dashaps.ui.command;

import com.androidaps.dashaps.data.PodCommandUIType;
import com.androidaps.dashaps.ui.fragments.OverviewFragment;
import com.androidaps.dashaps.ui.fragments.treatment.MainTreatmentFragment;

public class SetTBRUiCommand extends PodCommandQueueUi {


    public SetTBRUiCommand(double amount) {
    }

    @Override
    public PodCommandUIType getCommandType() {
        return PodCommandUIType.SetTemporaryBasal;
    }

    @Override
    public void executeCommand() {


//        response = MainApp.getOmnipodDashCommunicationManager().setBolus(detailedBolusInfo);
//
//
//        if (response.success) {
//            expireAt = DateTimeUtil.getTimeInFutureFromSeconds((int) duration.getStandardSeconds());
//        } else {
//            expireAt = DateTimeUtil.getTimeInFutureFromSeconds(30);
//        }


    }

    @Override
    public void updateUi(OverviewFragment overviewFragment) {

    }

    @Override
    public void updateUi(MainTreatmentFragment overviewFragment) {

    }

    @Override
    public void updateUiOnFinalize(OverviewFragment overviewFragment) {

    }

    @Override
    public void updateUiOnFinalize(MainTreatmentFragment overviewFragment) {

    }

    @Override
    public void setCancelled() {

    }
}
