package com.androidaps.dashaps.enums;

import java.util.HashMap;
import java.util.Map;

public enum PodState {

    ReadyForActivation(0),
    GotPodVersion(1),
    SetPodUid(2),
    ProgrammedLowReservoirAlerts(3),
    ProgrammedLumpOfCoalAlert(4),
    PodPrimed(5),
    SetPodExpirationAlert(6),
    Activation_Phase1_Completed(7),
    ACTIVATION_PROGRAMMED_BASAL(8),
    ACTIVATION_PROGRAMMED_CANCEL_LOC_ETC_ALERT(9),
    ACTIVATION_INSERTED_CANNULA(10),
    GotStatus(11),
    Active(12),
    Suspended(13),
    Deactivated(14),

    ;


    private int value;
    private static Map<Integer,PodState> mapByPodState;

    static {
        mapByPodState = new HashMap<>();

        for (PodState podState : values()) {
            mapByPodState.put(podState.getValue(), podState);
        }
    }



    private PodState(final int n2) {

        this.value = n2;
    }

    public static PodState getByValue(final int n) {

        if (mapByPodState.containsKey(n)) {
            return mapByPodState.get(n);
        }

        return null;
    }

    public int getValue() {

        return this.value;
    }


}
