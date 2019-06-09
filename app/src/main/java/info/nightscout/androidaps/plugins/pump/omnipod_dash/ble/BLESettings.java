package info.nightscout.androidaps.plugins.pump.omnipod_dash.ble;

public class BLESettings {

    public static boolean debugBLE = true;
    public static boolean debugEnDecoding = true;

    // InsuPdmGeneralPrefs
    long controller_id = 35681384;

    // InsuPdmSlaveSpecificPrefs
    String server_seq_no = "AAAAAAAE";
    String server_ltk = "X81i9fx5HwAfgKmmTDt3HA==";

    // pdm
    String password = "66765D7259AE4B92B99A164AD194A9D0";
    String host = "prod.omnipodcloud.com";
    String serial = "010100-07450";
    long id = 35681384;
    String ltk = "X81i9fx5HwAfgKmmTDt3HA==";
    String lot = "L000147";

    // Realm
    boolean resetRealm = false;
    String iv = "3Yrgo/8cUA9ovOaD";
    String salt = "7b8e3jraRcPXFKqo9wxbufexAwi4TK4atyY9NZxff8dxZubFaIbuvfuHjMu1II9GdfsHtLsr9jfUvDswRxdYLmzLugY8dxIDjfOt9emdkCWfOJdh3OBLmOLzLVVv/efDPK9XC5PK9XC5RdAKElx27xDIpU2Z4ynCRCw0DEhuEh2OvdIro=";
    String encryptedKey = "TH6NNspBy9XJ9QlyFTd3u6TqS+7W5Hn8GSvHKqNilkjw/VvQTnGrbXGcCHyhyrZs9/2gF+MuaMIIMt3878d3UZnWUwxMm2ifFHH1MgjiG+A=";

    public void initSettingsToSP() {
        
    }

}
