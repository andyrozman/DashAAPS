package com.androidaps.dashaps.ui.command;

import com.androidaps.dashaps.DashAPSUiQueue;
import com.androidaps.dashaps.MainActivity;
import com.androidaps.dashaps.MainApp;
import com.androidaps.dashaps.data.PodCommandUIType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.nightscout.androidaps.data.PumpEnactResult;
import info.nightscout.androidaps.utils.OKDialog;

public class CancelBolusUiCommand extends PodCommandUi {

    private static final Logger LOG = LoggerFactory.getLogger(CancelBolusUiCommand.class);

    @Override
    public PodCommandUIType getCommandType() {
        return PodCommandUIType.CancelBolus;
    }

    @Override
    public void executeCommand() {

        PumpEnactResult result = MainApp.getOmnipodDashCommunicationManager().cancelBolus();

        if (result.success) {
            MainApp.startChangeCommand(DashAPSUiQueue.QueueCommand.CancelBolus);
        } else {
            LOG.error("Error cancelling bolus");
            OKDialog.show(MainActivity.getInstance(), "Warning", "Error canceling bolus (msg=" + result.comment + ").", null);
        }
    }
}
