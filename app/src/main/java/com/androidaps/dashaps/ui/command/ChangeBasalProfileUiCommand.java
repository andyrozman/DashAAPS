package com.androidaps.dashaps.ui.command;

import com.androidaps.dashaps.data.PodCommandUIType;

public class ChangeBasalProfileUiCommand  extends PodCommandUi {

    @Override
    public PodCommandUIType getCommandType() {
        return PodCommandUIType.ChangeBasalProfile;
    }

    @Override
    public void executeCommand() {

    }
}
