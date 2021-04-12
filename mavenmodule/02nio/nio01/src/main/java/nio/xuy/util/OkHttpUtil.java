package nio.xuy.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class OkHttpUtil {
    private static OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build();

    /**
     * String url = "http://localhost:8801";
     * @param url
     * @return
     */
    public Optional<String> executeRequestGet(String url) throws IOException {
        try {
            Optional responseStr = Optional.empty();
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .header("Content-Type", "text/html;charset=utf-8")
                    .build();
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            ResponseBody body = response.body();

            if (Objects.nonNull(body)) {
                responseStr = Optional.ofNullable(body.string());
            }
            return responseStr;
        } catch (Exception e) {
            throw e;
        }
    }
}
