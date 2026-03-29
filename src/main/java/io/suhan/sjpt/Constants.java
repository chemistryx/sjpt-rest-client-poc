package io.suhan.sjpt;

public class Constants {
    public static final String PORTAL_BASE_URL = "https://portal.sejong.ac.kr";
    public static final String SJPT_BASE_URL = "https://sjpt.sejong.ac.kr";

    public static final String PORTAL_LOGIN_PATH = PORTAL_BASE_URL + "/jsp/login/login_action.jsp";
    public static final String SSO_LOGIN_PATH = SJPT_BASE_URL + "/main/view/Login/doSsoLogin.do";

    public static final String INIT_USER_INFO_PATH = SJPT_BASE_URL + "/main/sys/UserInfo/initUserInfo.do";
    public static final String INIT_USER_ROLE_PATH = SJPT_BASE_URL + "/main/sys/UserRole/initUserRole.do";
    public static final String LIST_USER_MENU_LIST_PATH = SJPT_BASE_URL + "/main/view/Menu/doListUserMenuListLeft.do";

    public static final String GRAD_CERT_LIST_PATH = SJPT_BASE_URL + "/sch/sch/suf/SufGdtExamQ/doList.do";
    public static final String ACADEMIC_RECORD_LIST_PATH = SJPT_BASE_URL + "/sch/sch/sug/SugRecordQ/doList.do";

    public static final String PGM_KEY_GRAD_CERT = "SELF_STUDSELF_SUB_20SCH_SUH_STUDSufGdtExamQ";
    public static final String PGM_KEY_ACADEMIC_RECORD = "SELF_STUDSELF_SUB_30SCH_SUG05_STUDSugRecordQ";

    public static final String SYS_KEY_SCH = "SCH";
    public static final String MENU_SYS_ID = "SELF_STUD";
}
