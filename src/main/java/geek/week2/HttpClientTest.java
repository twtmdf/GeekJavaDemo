package geek.week2;

import okhttp3.*;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class HttpClientTest {
    public static void main(String[] args) {
        OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();
        try {
            Request request = new Request.Builder()
                    .url(" http://localhost:8801 ")
                    .get()
                    .header("Content-Type", "text/html;charset=utf-8")
                    .build();
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            ResponseBody body = response.body();
            if (Objects.nonNull(body)) {
                System.out.println(body.string());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
