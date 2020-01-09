package info.nightscout.androidaps.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.nightscout.androidaps.logging.L;

// TODO this class has a lot of removed stuff, its here for integration purposes only (dependedcies)

public class CareportalEvent /*implements DataPointWithLabelInterface, Interval*/ {
    private static Logger log = LoggerFactory.getLogger(L.DATABASE);

    public long date;

    public boolean isValid = true;

    public int source = Source.NONE;
    public String _id;

    public String eventType;
    public String json;

    public static final String CARBCORRECTION = "Carb Correction";
    public static final String BOLUSWIZARD = "Bolus Wizard";
    public static final String CORRECTIONBOLUS = "Correction Bolus";
    public static final String MEALBOLUS = "Meal Bolus";
    public static final String COMBOBOLUS = "Combo Bolus";
    public static final String TEMPBASAL = "Temp Basal";
    public static final String TEMPORARYTARGET = "Temporary Target";
    public static final String PROFILESWITCH = "Profile Switch";
    public static final String SITECHANGE = "Site Change";
    public static final String INSULINCHANGE = "Insulin Change";
    public static final String SENSORCHANGE = "Sensor Change";
    public static final String PUMPBATTERYCHANGE = "Pump Battery Change";
    public static final String BGCHECK = "BG Check";
    public static final String ANNOUNCEMENT = "Announcement";
    public static final String NOTE = "Note";
    public static final String QUESTION = "Question";
    public static final String EXERCISE = "Exercise";
    public static final String OPENAPSOFFLINE = "OpenAPS Offline";
    public static final String NONE = "<none>";

    public static final String MBG = "Mbg"; // comming from entries

    public CareportalEvent() {
    }

//    public CareportalEvent(NSMbg mbg) {
//        date = mbg.date;
//        eventType = MBG;
//        json = mbg.json;
//    }

    public long getMillisecondsFromStart() {
        return System.currentTimeMillis() - date;
    }

    public double getHoursFromStart() {
        return (System.currentTimeMillis() - date) / (60 * 60 * 1000.0);
    }


    public boolean isOlderThan(double hours) {
        return getHoursFromStart() > hours;
    }

    @Override
    public String toString() {
        return "CareportalEvent{" +
                "date= " + date +
                //", date= " + DateUtil.dateAndTimeString(date) +
                ", isValid= " + isValid +
                ", _id= " + _id +
                ", eventType= " + eventType +
                ", json= " + json +
                "}";
    }


}
