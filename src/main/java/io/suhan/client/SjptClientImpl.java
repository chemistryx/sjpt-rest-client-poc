package io.suhan.client;

import com.google.gson.JsonObject;
import io.suhan.Constants;
import io.suhan.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

public class SjptClientImpl implements SjptClient {
    private final HttpClient client;
    private RunContext context;

    public SjptClientImpl(HttpClient client) {
        this.client = client;
    }

    @Override
    public void init() throws Exception {
        // 1. 사용자 정보 초기화
        String body = client.post(
                Constants.INIT_USER_INFO_PATH + "?addParam=" + new RunContext().encode(),
                "",
                headers("mf___subMainUserInfoInit")
        ).body().string();
        context = RunContext.from(body);

        // 2. 메뉴 불러오기
        JsonObject dmReqLeftMenu = new JsonObject();
        dmReqLeftMenu.addProperty("MENU_SYS_ID", Constants.MENU_SYS_ID);
        dmReqLeftMenu.addProperty("SYSTEM_DIV", Constants.SYS_KEY_SCH);
        dmReqLeftMenu.addProperty("MENU_SYS_NM", "학부생학사정보");

        JsonObject leftMenuPayload = new JsonObject();
        leftMenuPayload.add("dm_ReqLeftMenu", dmReqLeftMenu);

        client.post(
                Constants.LIST_USER_MENU_LIST_PATH + "?addParam=" + context.encode(),
                leftMenuPayload.toString(),
                headers("mf_subUserMenuListLeft")
        );
    }

    @Override
    public String getGradCertHistory() throws Exception {
        registerPgmRole(Constants.PGM_KEY_GRAD_CERT);

        RunContext ctx = context.pgmKey(Constants.PGM_KEY_GRAD_CERT).sysKey(Constants.SYS_KEY_SCH);

        // 2. 데이터 조회
        JsonObject dmSearch = new JsonObject();
        dmSearch.addProperty("ORGN_CLSF_CD", context.orgnClsfCd());
        dmSearch.addProperty("STUDENT_NO", context.usrNo());
        dmSearch.addProperty("PASS_CD", "");

        JsonObject payload = new JsonObject();
        payload.add("dm_search", dmSearch);

        return client.post(
                Constants.GRAD_CERT_LIST_PATH + "?addParam=" + ctx.encode(),
                payload.toString(),
                headers("mf_tabMainCon_contents_" + Constants.PGM_KEY_GRAD_CERT + "_body_sub_search")
        ).body().string();
    }

    @Override
    public String getAcademicRecord() throws Exception {
        registerPgmRole(Constants.PGM_KEY_ACADEMIC_RECORD);

        RunContext ctx = context.pgmKey(Constants.PGM_KEY_ACADEMIC_RECORD).sysKey(Constants.SYS_KEY_SCH);

        // 2. 데이터 조회
        JsonObject dmSearch = new JsonObject();
        dmSearch.addProperty("ORGN_CLSF_CD", context.orgnClsfCd());
        dmSearch.addProperty("YEAR", "");
        dmSearch.addProperty("SMT_CD", "");
        dmSearch.addProperty("RECORD_YN", "Y");
        dmSearch.addProperty("STUDENT_NO", context.usrNo());
        dmSearch.addProperty("STUDENT_NM", "");
        dmSearch.addProperty("YEAR_SMT", "");

        JsonObject payload = new JsonObject();
        payload.add("dm_search", dmSearch);

        return client.post(
                Constants.ACADEMIC_RECORD_LIST_PATH + "?addParam=" + ctx.encode(),
                payload.toString(),
                headers("mf_tabMainCon_contents_" + Constants.PGM_KEY_ACADEMIC_RECORD + "_body_sub_search")
        ).body().string();
    }

    private void registerPgmRole(String pgmKey) throws Exception {
        RunContext ctx = context.forceLog(false).pgmKey(pgmKey).sysKey(Constants.SYS_KEY_SCH);
        client.post(
                Constants.INIT_USER_ROLE_PATH + "?addParam=" + ctx.encode(),
                ctx.toJson(),
                headers("mf_tabMainCon_contents_" + pgmKey + "_body___subMainRoleInit")
        );
    }

    private Map<String, String> headers(String submissionId) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Origin", Constants.SJPT_BASE_URL);
        headers.put("Referer", Constants.SJPT_BASE_URL);
        headers.put("submissionid", submissionId);
        return headers;
    }
}
