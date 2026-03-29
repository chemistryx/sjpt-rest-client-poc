package io.suhan;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import io.suhan.auth.AuthService;
import io.suhan.client.SjptClient;
import io.suhan.client.SjptClientImpl;
import io.suhan.http.HttpClient;

public class Main {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws Exception {
        HttpClient httpClient = new HttpClient();
        AuthService authService = new AuthService(httpClient);
        SjptClient sjptClient = new SjptClientImpl(httpClient);

        String id = System.getenv("SJPT_ID");
        String password = System.getenv("SJPT_PASSWORD");
        authService.login(id, password);

        sjptClient.init();

        System.out.println(pretty(sjptClient.getGradCertHistory()));
        System.out.println(pretty(sjptClient.getAcademicRecord()));
    }

    private static String pretty(String json) {
        return GSON.toJson(JsonParser.parseString(json));
    }
}
