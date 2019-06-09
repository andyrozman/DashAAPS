package com.androidaps.dashaps.ui.command;

import com.androidaps.dashaps.DashAapsService;
import com.androidaps.dashaps.data.Pod;
import com.androidaps.dashaps.data.PodCommandUIType;
import com.androidaps.dashaps.enums.PodState;
import com.androidaps.dashaps.ui.fragments.OverviewFragment;
import com.androidaps.dashaps.ui.fragments.PodFragment;

import java.util.Random;


public class ActivatePodUiCommand extends PodCommandUi {

    @Override
    public PodCommandUIType getCommandType() {
        return PodCommandUIType.ActivatePod;
    }

    public void executeCommand() {

        Pod pod = new Pod();

        pod.setActivationTime(System.currentTimeMillis());
        pod.setAddress(randomMACAddress());
        pod.setBleVersion("0.0.1");
        pod.setPodVersion("0.0.2");

        Random random = new Random();
        random.setSeed(4873874838L);

        pod.setLotNumber(random.nextInt());
        pod.setPodState(PodState.Active);


        DashAapsService.pod = pod;

        OverviewFragment.getInstance().setPod(pod);
        PodFragment.getInstance().setPod(pod);

        DashAapsService.getInstance().saveData();

    }



    private String randomMACAddress(){
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
