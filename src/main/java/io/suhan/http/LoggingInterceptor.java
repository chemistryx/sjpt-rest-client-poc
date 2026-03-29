package io.suhan.http;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;

public class LoggingInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        System.out.println(request.method() + " " + request.url().encodedPath() + " -> " + response.code());

        ResponseBody responseBody = response.body();
        String bodyString = responseBody.string();

        return response.newBuilder()
                .body(ResponseBody.create(bodyString, responseBody.contentType()))
                .build();
    }
}
