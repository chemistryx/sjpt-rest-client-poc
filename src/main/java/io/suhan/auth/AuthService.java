package io.suhan.auth;

import io.suhan.http.HttpClient;
import okhttp3.FormBody;

public class AuthService {
    private static final String PORTAL_LOGIN_URL = "https://portal.sejong.ac.kr/jsp/login/login_action.jsp";
    private static final String SSO_LOGIN_URL = "https://sjpt.sejong.ac.kr/main/view/Login/doSsoLogin.do";
    private final HttpClient client;

    public AuthService(HttpClient client) {
        this.client = client;
    }

    public void login(String id, String password) throws Exception {
        FormBody form = new FormBody.Builder()
                .add("mainLogin", "Y")
                .add("id", id)
                .add("password", password)
                .build();

        // 1. 포탈 로그인
        client.post(PORTAL_LOGIN_URL, form, "https://portal.sejong.ac.kr/jsp/login/loginSSL.jsp");

        // 2. SSO 연동
        client.get(SSO_LOGIN_URL, "https://portal.sejong.ac.kr/");
    }
}
