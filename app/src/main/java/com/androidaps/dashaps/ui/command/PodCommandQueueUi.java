package com.androidaps.dashaps.ui.command;

import com.androidaps.dashaps.ui.fragments.OverviewFragment;
import com.androidaps.dashaps.ui.fragments.treatment.MainTreatmentFragment;

public abstract class PodCommandQueueUi extends PodCommandUi {

    public long expireAt;

    public boolean isCommandExpired() {
        return System.currentTimeMillis() > expireAt;
    }

    public abstract void updateUi(OverviewFragment overviewFragment);

    public abstract void updateUi(MainTreatmentFragment overviewFragment);

    public abstract void updateUiOnFinalize(OverviewFragment overviewFragment);

    public abstract void updateUiOnFinalize(MainTreatmentFragment overviewFragment);

    public abstract void setCancelled();

}
