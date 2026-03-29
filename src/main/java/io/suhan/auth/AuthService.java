package io.suhan.auth;

import io.suhan.Constants;
import io.suhan.http.HttpClient;
import okhttp3.FormBody;

public class AuthService {
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
        client.post(Constants.PORTAL_LOGIN_PATH, form, "https://portal.sejong.ac.kr/jsp/login/loginSSL.jsp");

        // 2. SSO 연동
        client.get(Constants.SSO_LOGIN_PATH, "https://portal.sejong.ac.kr/");
    }
}
