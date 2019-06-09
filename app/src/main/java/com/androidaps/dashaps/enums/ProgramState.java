// 
// Decompiled by Procyon v0.5.34
// 

package com.androidaps.dashaps.enums;

import java.util.HashMap;
import java.util.Map;

public enum ProgramState {
    UNKNOWN(0),
    RUNNING_BASAL(1),
    RUNNING_BASAL_AND_TEMP_BASAL(2),
    RUNNING_BASAL_AND_TEMP_BASAL_AND_BOLUS(3),
    RUNNING_BASAL_AND_TEMP_BASAL_AND_EXT_BOLUS(4),
    RUNNING_BASAL_AND_BOLUS(5),
    RUNNING_BASAL_AND_EXT_BOLUS(6);

    int value;
    private static Map<Integer, ProgramState> mapByValue;

    static {
        mapByValue = new HashMap<>();

        for (ProgramState programState : values()) {
            mapByValue.put(programState.value, programState);
        }
    }

    ProgramState(int value) {
        this.value = value;
    }


    public static ProgramState getByValue(final Integer n) {

        if (mapByValue.containsKey(n)) {
            return mapByValue.get(n);
        }

        return UNKNOWN;
    }



}
