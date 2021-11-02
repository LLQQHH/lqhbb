package com.lqh.jaxlinmaster.lqhcommon.lqhutils;

/**
 * Created by Linqh on 2019/1/24.
 *
 * @describe:
 */

import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lqh.jaxlinmaster.R;

/*
 * Android Build.VERSION_CODES.R(11 api30),后台不允许自定义弹出自定义的Toast，会弹出「"Background custom toast blocked for package [packageName] See g.co/dev/toast."」
 *对应普通的Text Toast，虽然可以正常使用，但是setGravity 和 setMargin会失效，而自定义View的不会
 * setView() 方法被标记弃用，getView()可能为null
 * 解决：1.用自定义View，然后判断当前是否在后台，在后台的话，就不弹Toast
 * 2.用开源库
 */
public class ToastUtil {
    private static final Handler TOAST_HANDLER = new Handler(Looper.getMainLooper());
    private static Toast toast;

    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void show(int resId) {
        show(Utils.getApp().getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration) {
        try {
            CharSequence text = Utils.getApp().getResources().getText(resId);
            show(text, duration);
        } catch (Exception ignore) {
            show(String.valueOf(resId), duration);
        }

    }


    public static void show(final CharSequence text, final int duration) {
        if (StringUtil.isEmpty(text)) {
            return;
        }
        runOnUiThread(() -> {
            if (toast != null) {
                toast.cancel();
            }
            toast = createToast();
            toast.setDuration(duration);
            ((TextView) toast.getView().findViewById(R.id.textView_content)).setText(text);
            toast.show();
        });
    }

    private static Toast createToast() {
        View view = LayoutInflater.from(Utils.getApp()).inflate(R.layout.item_layout_toast, null);
        Toast t = new Toast(Utils.getApp());
        t.setGravity(Gravity.CENTER, 0, 0);
        t.setView(view);
        return t;
    }

    public static void runOnUiThread(final Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            ToastUtil.TOAST_HANDLER.post(runnable);
        }
    }
}

