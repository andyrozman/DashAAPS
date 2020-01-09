package com.androidaps.dashaps.ui.command;

import com.androidaps.dashaps.DashAPSUiQueue;
import com.androidaps.dashaps.MainActivity;
import com.androidaps.dashaps.MainApp;
import com.androidaps.dashaps.data.PodCommandUIType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.nightscout.androidaps.data.PumpEnactResult;
import info.nightscout.androidaps.utils.OKDialog;

public class CancelTBRUiCommand extends PodCommandUi {

    private static final Logger LOG = LoggerFactory.getLogger(CancelTBRUiCommand.class);


    @Override
    public PodCommandUIType getCommandType() {
        return PodCommandUIType.CancelTemporaryBasal;
    }

    @Override
    public void executeCommand() {

        PumpEnactResult result = MainApp.getOmnipodDashCommunicationManager().cancelTemporaryBasal();

        if (result.success) {
            MainApp.startChangeCommand(DashAPSUiQueue.QueueCommand.CancelTBR);
        } else {
            LOG.error("Error cancelling TBR");
            OKDialog.show(MainActivity.getInstance(), "Warning", "Error canceling TBR (msg=" + result.comment + ").", null);
        }
    }


}
