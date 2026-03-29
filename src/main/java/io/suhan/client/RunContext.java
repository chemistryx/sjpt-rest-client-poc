package io.suhan.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RunContext {
    private final String usrNo;
    private final String loginDt;
    private final String sejong;
    private final String orgnClsfCd;
    private final String pgmKey;
    private final String sysKey;
    private final String pbForceLog;

    public RunContext() {
        this("", "", "", "");
    }

    private RunContext(String usrNo, String loginDt, String sejong, String orgnClsfCd) {
        this(usrNo, loginDt, sejong, orgnClsfCd, null, null, null);
    }

    private RunContext(String usrNo, String loginDt, String sejong, String orgnClsfCd,
                       String pgmKey, String sysKey, String pbForceLog) {
        this.usrNo = usrNo;
        this.loginDt = loginDt;
        this.sejong = sejong;
        this.orgnClsfCd = orgnClsfCd;
        this.pgmKey = pgmKey;
        this.sysKey = sysKey;
        this.pbForceLog = pbForceLog;
    }

    public static RunContext from(String initResponse) {
        JsonObject root = JsonParser.parseString(initResponse).getAsJsonObject();
        JsonObject userInfo = root.getAsJsonObject("dm_UserInfo");
        JsonObject userInfoGam = root.getAsJsonObject("dm_UserInfoGam");
        JsonObject userInfoSch = root.getAsJsonObject("dm_UserInfoSch");

        return new RunContext(
                userInfo.get("INTG_USR_NO").getAsString(),
                userInfoGam.get("LOGIN_DT").getAsString(),
                userInfo.get("RUNNING_SEJONG").getAsString(),
                userInfoSch.get("ORGN_CLSF_CD").getAsString()
        );
    }

    public String usrNo() {
        return usrNo;
    }

    public String orgnClsfCd() {
        return orgnClsfCd;
    }

    public RunContext forceLog(boolean value) {
        return new RunContext(usrNo, loginDt, sejong, orgnClsfCd, pgmKey, sysKey, String.valueOf(value));
    }

    public RunContext pgmKey(String pgmKey) {
        return new RunContext(usrNo, loginDt, sejong, orgnClsfCd, pgmKey, sysKey, pbForceLog);
    }

    public RunContext sysKey(String newSysKey) {
        return new RunContext(usrNo, loginDt, sejong, orgnClsfCd, pgmKey, newSysKey, pbForceLog);
    }

    public String toJson() {
        JsonObject json = new JsonObject();
        if (pbForceLog != null) json.addProperty("pbForceLog", pbForceLog);
        if (pgmKey != null) json.addProperty("_runPgmKey", pgmKey);
        if (sysKey != null) json.addProperty("_runSysKey", sysKey);
        json.addProperty("_runIntgUsrNo", usrNo);
        json.addProperty("_runPgLoginDt", loginDt);
        json.addProperty("_runningSejong", sejong);
        return json.toString();
    }

    public String encode() throws UnsupportedEncodingException {
        String urlEncoded = URLEncoder.encode(toJson(), StandardCharsets.UTF_8.name());
        return Base64.getEncoder().encodeToString(urlEncoded.getBytes(StandardCharsets.UTF_8));
    }
}
