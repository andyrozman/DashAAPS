package com.androidaps.dashaps.ui.util;


import org.joda.time.Duration;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;

import java.util.Random;

public class DashUIUtil {


    public static String getDateTimeAsString(LocalDateTime ldt) {
        return String.format("%02d.%02d.%04d    %02d:%02d:%02d", //
                ldt.getDayOfMonth(), ldt.getMonthOfYear(), ldt.getYear(), //
                ldt.getHourOfDay(), ldt.getMinuteOfHour(), ldt.getSecondOfMinute());
    }


    public static String getDateTimeAsString(int[] timeNew) {
        return String.format("%02d.%02d.%04d    %02d:%02d:%02d", timeNew[0], timeNew[1],timeNew[2],timeNew[3],timeNew[4],timeNew[5]);
    }

    public static String getTimeDifference(LocalDateTime podActivation, LocalDateTime currentTime) {
        if (podActivation.equals(currentTime)) {
            return "3 days";
        } else {
            //Duration duration = new Duration(podActivation., currentTime);


            Period period = new Period(podActivation, currentTime);

            int daysLeft = 2-period.getDays();
            int hoursLeft = 23 - period.getHours();
            int minuesLeft = 59 - period.getMinutes();

            return daysLeft + " days " + hoursLeft + " hrs " + minuesLeft + " mins";
        }
    }


    public static String randomMACAddress()  {
        Random rand = new Random();
        byte[] macAddr = new byte[6];
        rand.nextBytes(macAddr);

        macAddr[0] = (byte)(macAddr[0] & (byte)254);  //zeroing last 2 bytes to make it unicast and locally adminstrated

        StringBuilder sb = new StringBuilder(18);
        for(byte b : macAddr){

            if(sb.length() > 0)
                sb.append(":");

            sb.append(String.format("%02x", b));
        }


        return sb.toString();
    }


}
