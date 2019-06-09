package com.androidaps.dashaps.ui.command;

import com.androidaps.dashaps.DashAapsService;
import com.androidaps.dashaps.data.Pod;
import com.androidaps.dashaps.data.PodCommandUIType;
import com.androidaps.dashaps.enums.PodState;
import com.androidaps.dashaps.ui.fragments.OverviewFragment;
import com.androidaps.dashaps.ui.fragments.PodFragment;

import java.util.Random;


public class DeactivatePodUiCommand extends PodCommandUi {

    @Override
    public PodCommandUIType getCommandType() {
        return PodCommandUIType.DeactivatePod;
    }

    public void executeCommand() {

        DashAapsService.pod.setPodState(PodState.Deactivated);

        OverviewFragment.getInstance().setPod(DashAapsService.pod);
        PodFragment.getInstance().setPod(DashAapsService.pod);

        DashAapsService.getInstance().saveData();

    }

}
