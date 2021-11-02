package com.lqh.jaxlinmaster.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Linqh on 2020/3/2.
 *
 * @describe:
 */
public class GlideInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        // 获取请求对象
        Request request = chain.request();
        Response proceed = chain.proceed(request);
        if (proceed != null && proceed.request() != null) {
            HttpUrl url = proceed.request().url();
            if (url != null) {
                Log.e("Glide请求地址", url.toString());
            }
        }
        return proceed;
    }

    private static boolean isPlaintext(MediaType mediaType) {
        if (mediaType == null) return false;
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        String subtype = mediaType.subtype();
        if (subtype != null) {
            subtype = subtype.toLowerCase();
            if (subtype.contains("x-www-form-urlencoded") || subtype.contains("json") || subtype.contains("xml") || subtype.contains("html")) //
                return true;
        }
        return false;
    }
}
