package com.androidaps.dashaps.data;

import com.androidaps.dashaps.enums.ProgramState;

import org.joda.time.LocalDateTime;

public class PodStateDto {

//    private Long lastConnectTime;
//    private long lastScanTime;
//
//    private int alarmCode;
//    private boolean alarmErrorImmediateBolus;
//    private int alarmOcclusionType;
//    private long alarmTime;
//
//    private int podCommState;
//    private short podLife;
//
//    private Integer programState;

    private LocalDateTime lastBolusTime = null;
    private Double lastBolusAmount = null;

    private Long tbrAmount = null;
    private LocalDateTime tbrStarted = null;
    private int tbrDuration = 0;




}
