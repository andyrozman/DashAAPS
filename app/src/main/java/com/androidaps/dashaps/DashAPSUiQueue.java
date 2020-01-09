package com.androidaps.dashaps;

import android.util.Log;

import com.androidaps.dashaps.data.PodCommandUIType;
import com.androidaps.dashaps.ui.command.PodCommandQueueUi;
import com.androidaps.dashaps.ui.fragments.OverviewFragment;
import com.androidaps.dashaps.ui.fragments.treatment.MainTreatmentFragment;

import java.util.ArrayList;
import java.util.List;

public class DashAPSUiQueue {

    private List<PodCommandQueueUi> queue = new ArrayList<>();

    private PodCommandQueueUi bolusCommand = null;
    private PodCommandQueueUi tbrCommand = null;


    public DashAPSUiQueue() {
    }


    public void processQueue() {

        if (workWithQueue(QueueCommand.AnyElementOnQueue, null) != null) {
            Log.d("DashAPSUIQueue", "Run Queue (Process)");

            List<PodCommandQueueUi> queueList = (List<PodCommandQueueUi>) workWithQueue(QueueCommand.GetQueue, null);

            List<PodCommandQueueUi> removedList = new ArrayList<>();

            for (PodCommandQueueUi podCommandQueueUi : queueList) {

                if (podCommandQueueUi.isCommandExpired()) {
                    OverviewFragment.getInstance().processCommandFinished(podCommandQueueUi);
                    MainTreatmentFragment.getInstance().processCommandFinished(podCommandQueueUi);
                    removedList.add(podCommandQueueUi);
                    if (podCommandQueueUi.getCommandType() == PodCommandUIType.SetBolus) {
                        this.bolusCommand = null;
                    } else if (podCommandQueueUi.getCommandType() == PodCommandUIType.SetTemporaryBasal) {
                        this.tbrCommand = null;
                    }
                } else {
                    OverviewFragment.getInstance().processCommand(podCommandQueueUi);
                    MainTreatmentFragment.getInstance().processCommand(podCommandQueueUi);
                }

            }

            workWithQueue(QueueCommand.RemoveFromList, removedList);

        } else {
            Log.d("DashAPSUIQueue", "Run Queue (Empty)");
        }

    }


    public void startChangeCommand(QueueCommand queueCommand) {
        workWithQueue(queueCommand, null);
    }


    private synchronized Object workWithQueue(QueueCommand queueCommand, Object parameter) {

        switch (queueCommand) {

            case Add: {
                PodCommandQueueUi command = (PodCommandQueueUi) parameter;
                this.queue.add(command);
                if (command.getCommandType() == PodCommandUIType.SetBolus) {
                    this.bolusCommand = command;
                } else if (command.getCommandType() == PodCommandUIType.SetTemporaryBasal) {
                    this.tbrCommand = command;
                }
                return null;
            }

            case GetQueue:
                return new ArrayList<>(this.queue);

            case RemoveFromList:
                this.queue.removeAll((List<PodCommandQueueUi>) parameter);
                return null;

            case AnyElementOnQueue: {
                return (this.queue.size() == 0) ? null : "";
            }

            case CancelBolus:
                this.bolusCommand.setCancelled();
                break;

            case CancelTBR:
                this.tbrCommand.setCancelled();
                break;
        }

        return null;
    }

    public void addToQueue(PodCommandQueueUi command) {
        Log.d("DashAPSUIQueue", "Add To Queue");
        workWithQueue(QueueCommand.Add, command);
    }


    public enum QueueCommand {
        Add,
        GetQueue,
        RemoveFromList,
        AnyElementOnQueue,
        CancelBolus,
        CancelTBR
    }


}
