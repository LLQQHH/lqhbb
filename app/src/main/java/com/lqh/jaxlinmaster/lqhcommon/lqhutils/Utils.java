package com.lqh.jaxlinmaster.lqhcommon.lqhutils;

import android.annotation.SuppressLint;
import android.app.Application;

/**
 * blog  : http://blankj.com
 */
public final class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Application sApp;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Init utils.
     * <p>Init it in the class of UtilsFileProvider.</p>
     *
     * @param app application
     */
    public static void init(final Application app) {
        sApp = app;
    }

    public static Application getApp() {
        return sApp;
    }
}