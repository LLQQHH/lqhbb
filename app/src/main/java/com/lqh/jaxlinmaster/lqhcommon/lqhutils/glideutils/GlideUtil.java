package com.lqh.jaxlinmaster.lqhcommon.lqhutils.glideutils;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.lqh.jaxlinmaster.R;
import com.lqh.jaxlinmaster.constants.Constants;


public class GlideUtil {
    //申明静态成员变量
    private static GlideUtil glideImageLoader;

    //私有构造方法
    private GlideUtil() {
    }

    //加线程锁，实现线程安全
    public static synchronized GlideUtil getInstance() {
        if (glideImageLoader == null) {
            glideImageLoader = new GlideUtil();
        }
        return glideImageLoader;
    }

    public void displayImage(Context context, String url, ImageView imageView) {
        this.displayImage(context, url, imageView, R.drawable.default_avatar);
    }

    public void displayImage(Context context, String url, ImageView imageView, int placeholderId) {
        RequestOptions mGlideOptions = new RequestOptions().placeholder(placeholderId).dontAnimate();
        if (!isValidContextForGlide(context) || imageView == null) {
            return;
        }
        Glide.with(context).load(url).apply(mGlideOptions).into(imageView);
    }

    public void displayCompanyImage(Context context, String url, ImageView imageView) {
        RequestOptions mGlideOptions = new RequestOptions().error(R.drawable.default_avatar);
        Glide.with(context).load(getSourceGlideUrl(url)).apply(mGlideOptions).into(imageView);
    }
    public void displayAppCompanyImage(Context context, String url, ImageView imageView) {
        RequestOptions mGlideOptions = new RequestOptions().error(R.drawable.default_avatar);
        GlideApp.with(context).load(getSourceGlideUrl(url)).apply(mGlideOptions).into(imageView);
    }

    public static GlideUrl getSourceGlideUrl(String url) {
        return new GlideUrl(url, new LazyHeaders.Builder()
                .addHeader(Constants.Strings.HEAD_KEY, Constants.Strings.HEAD_KEY_VALUE_PRE + Constants.Strings.LQHTOKEN)
                .build());
    }


    public static boolean isValidContextForGlide(final Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            if (activity.isDestroyed() || activity.isFinishing()) {
                return false;
            }
        }
        return true;

    }
}
