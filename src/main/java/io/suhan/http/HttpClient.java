package io.suhan.http;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.time.Duration;
import java.util.Map;
import okhttp3.FormBody;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpClient {
    private final OkHttpClient client;

    public HttpClient() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        this.client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .followRedirects(true)
                .connectTimeout(Duration.ofSeconds(10))
                .readTimeout(Duration.ofSeconds(10))
                .addInterceptor(new LoggingInterceptor())
                .build();
    }

    public Response get(String url) throws IOException {
        return get(url, null);
    }

    public Response get(String url, String referer) throws IOException {
        Request.Builder builder = new Request.Builder().url(url).get();
        if (referer != null) {
            builder.header("Referer", referer);
        }
        return client.newCall(builder.build()).execute();
    }

    public Response post(String url, String jsonBody, Map<String, String> headers) throws IOException {
        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json; charset=\"UTF-8\""));
        Request.Builder builder = new Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .post(body);
        headers.forEach(builder::header);

        return client.newCall(builder.build()).execute();
    }

    public Response post(String url, FormBody requestBody, String referer) throws IOException {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(requestBody)
                .header("Content-Type", "application/x-www-form-urlencoded");
        if (referer != null) {
            builder.header("Referer", referer);
        }

        return client.newCall(builder.build()).execute();
    }
}
