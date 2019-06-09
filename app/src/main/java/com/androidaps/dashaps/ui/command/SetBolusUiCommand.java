package com.androidaps.dashaps.ui.command;

import com.androidaps.dashaps.data.PodCommandUIType;

public class SetBolusUiCommand extends PodCommandUi {
    @Override
    public PodCommandUIType getCommandType() {
        return PodCommandUIType.SetBolus;
    }

    @Override
    public void executeCommand() {

    }
}
