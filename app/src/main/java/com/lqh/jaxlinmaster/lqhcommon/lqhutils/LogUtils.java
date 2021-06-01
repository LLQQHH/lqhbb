package com.lqh.jaxlinmaster.lqhcommon.lqhutils;

import android.util.Log;

/**
 * Created by linqh on 2016-12-26.
 */
public class LogUtils {
    private LogUtils()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isLqhDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    public static final String TAG = "lqhApp";

    // 下面四个是默认tag的函数
    public static void i(String msg)
    {
        if (isLqhDebug)
            Log.i(TAG, msg);
    }

    public static void d(String msg)
    {
        if (isLqhDebug)
            Log.d(TAG, msg);
    }

    public static void e(String msg)
    {
        if (isLqhDebug)
            Log.e(TAG, msg);
    }

    public static void v(String msg)
    {
        if (isLqhDebug)
            Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg)
    {
        if (isLqhDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg)
    {
        if (isLqhDebug)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg)
    {
        if (isLqhDebug)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg)
    {
        if (isLqhDebug)
            Log.v(tag, msg);
    }
}
