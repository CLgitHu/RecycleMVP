package application.android.com.mymvp.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
    private static HttpUtil httpUtil;
    //返回实例
    public static HttpUtil getInstance() {
        return httpUtil == null ? httpUtil = new HttpUtil() : httpUtil;
    }

    public  void sendOkHttpRequest(final String address, final okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

}
