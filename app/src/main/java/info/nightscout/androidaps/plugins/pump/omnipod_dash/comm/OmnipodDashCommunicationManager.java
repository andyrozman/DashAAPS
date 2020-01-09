package info.nightscout.androidaps.plugins.pump.omnipod_dash.comm;

import android.content.Context;

import com.androidaps.dashaps.MainApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.nightscout.androidaps.data.DetailedBolusInfo;
import info.nightscout.androidaps.data.Profile;
import info.nightscout.androidaps.data.PumpEnactResult;
import info.nightscout.androidaps.logging.L;
import info.nightscout.androidaps.plugins.pump.common.data.TempBasalPair;
import info.nightscout.androidaps.plugins.pump.common.hw.rileylink.RileyLinkConst;
import info.nightscout.androidaps.plugins.pump.omnipod.defs.OmnipodCommunicationManagerInterface;
import info.nightscout.androidaps.plugins.pump.omnipod.defs.PodInitActionType;
import info.nightscout.androidaps.plugins.pump.omnipod.defs.PodInitReceiver;
import info.nightscout.androidaps.plugins.pump.omnipod.driver.OmnipodPumpStatus;
import info.nightscout.androidaps.utils.SP;

/**
 * Created by andy on 4.8.2019
 */
public class OmnipodDashCommunicationManager implements OmnipodCommunicationManagerInterface {

    private static final Logger LOG = LoggerFactory.getLogger(L.PUMPCOMM);

    private static OmnipodDashCommunicationManager omnipodCommunicationManager;
    private String errorMessage;
    private OmnipodPumpStatus pumpStatus;
    private PumpEnactResult OK_RESPONSE = new PumpEnactResult().success(true);
    //PodCommManager podCommManager = null;

    public OmnipodDashCommunicationManager(Context context) {
        omnipodCommunicationManager = this;
        MainApp.getPumpStatus().previousConnection = SP.getLong(
                RileyLinkConst.Prefs.LastGoodDeviceCommunicationTime, 0L);

        //AppCommManager appCommManager = AppCommManager.getInstance();

        //this.podCommManager = PodCommManager.getInstance(context);
    }


//    private PodSessionState getPodSessionState() {
//        LOG.debug("getPodSessionState not implemented.");
//        return null;
//    }


    public static OmnipodDashCommunicationManager getInstance() {
        return omnipodCommunicationManager;
    }


    public String getErrorResponse() {
        return this.errorMessage;
    }


    private boolean isLogEnabled() {
        return L.isEnabled(L.PUMPCOMM);
    }


    @Override
    public PumpEnactResult initPod(PodInitActionType podInitActionType, PodInitReceiver podInitReceiver, Profile profile) {
        LOG.debug("getPodSessionState not implemented.");
        return OK_RESPONSE;
    }

    public PumpEnactResult getPodStatus() {
        LOG.debug("getPodStatus not implemented.");
        return OK_RESPONSE;
    }


    public PumpEnactResult deactivatePod(PodInitReceiver podInitReceiver) {
        LOG.debug("deactivatePod not implemented.");
        return OK_RESPONSE;
    }

    public PumpEnactResult setBasalProfile(Profile profile) {
        LOG.debug("setBasalProfile not implemented.");
        return OK_RESPONSE;
    }

    public PumpEnactResult resetPodStatus() {
        LOG.debug("resetPodStatus not implemented.");
        return OK_RESPONSE;
    }

    @Override
    public PumpEnactResult setBolus(DetailedBolusInfo detailedBolusInfo) {
        LOG.debug("setBolus not implemented.");
        return OK_RESPONSE;
    }

    public PumpEnactResult cancelBolus() {
        LOG.debug("cancelBolus not implemented.");
        return OK_RESPONSE;
    }

    public PumpEnactResult setTemporaryBasal(TempBasalPair tbr) {
        LOG.debug("setTemporaryBasal not implemented.");
        return OK_RESPONSE;
    }

    public PumpEnactResult cancelTemporaryBasal() {
        LOG.debug("cancelTemporaryBasal not implemented.");
        return OK_RESPONSE;
    }

    @Override
    public PumpEnactResult acknowledgeAlerts() {
        LOG.debug("acknowledgeAlerts not implemented.");
        return OK_RESPONSE;
    }

    @Override
    public PumpEnactResult setTime() {
        LOG.debug("setTime not implemented.");
        return OK_RESPONSE;
    }

    @Override
    public void setPumpStatus(OmnipodPumpStatus pumpStatus) {
        this.pumpStatus = pumpStatus;
    }

//    @Override
//    public PodInfoRecentPulseLog readPulseLog() {
//        return null;
//    }

}
