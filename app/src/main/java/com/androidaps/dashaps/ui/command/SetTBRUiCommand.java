package com.androidaps.dashaps.ui.command;

import com.androidaps.dashaps.data.PodCommandUIType;

public class SetTBRUiCommand extends PodCommandUi {

    @Override
    public PodCommandUIType getCommandType() {
        return PodCommandUIType.SetTemporaryBasal;
    }

    @Override
    public void executeCommand() {

    }
}
