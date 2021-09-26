package com.lqh.jaxlinmaster.lqhcommon.lqhutils;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import static android.view.View.NO_ID;

public class ScreenUtil {
    public static int getKeyBordHeight(Activity activity){
        //获取整个屏幕的高度（比如：1920*1080的手机。该方法返回的值就是1920）
        int height = activity.getWindow().getDecorView().getRootView().getHeight();//1920
        int displayHeight = getWindowDisplayHeight(activity);//997
        int daoHangHeight = getDaoHangHeight(activity);//126
        int statusBarHeight = getStatusBarHeight(activity);//72
        int h = getScreenHeight(activity);//1794
        int h4 = h - displayHeight;
        return h4;
    }

    public static int getFullScreenHeight(Activity activity){
        return activity.getWindow().getDecorView().getRootView().getHeight();
    }

    /**
     * 获取屏幕高度
     * 去除导航栏的高度
     * @return
     */
    public static int getScreenHeight(Context context){
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth(Context context){
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获取当前屏幕显示的高度（没有被遮挡）
     * @param activity
     * @return
     */
    public static int getWindowDisplayHeight(Activity activity){
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.bottom;
    }

    /**
     * 获取导航栏的高度
     * @param activity
     * @return
     */
    public static int getDaoHangHeight(Activity activity) {
        int rid = activity.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            int resourceId = activity.getResources().
                    getIdentifier("navigation_bar_height", "dimen", "android");
            return activity.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    /**
     * 获取状态栏高度
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Activity activity){
        int statusBarHeight = -1;
        //获取status_bar_height资源的ID
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public static int getWindowHeight(Context context){
        Point point = new Point(0,0);
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        display.getSize(point);
        return point.y;
    }
    public static int getWindowRealHeight(Context context){
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //noinspection ConstantConditions
            // 可能有虚拟按键的情况
            manager.getDefaultDisplay().getRealSize(point);
        } else {
            //noinspection ConstantConditions
// 不可能有虚拟按键
            manager.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    public static int getWindowWidth(Context context){
        Point point = new Point(0,0);
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        display.getSize(point);
        return point.x;
    }

    public static void lightWindow(Activity activity){
        setWindowAlpha(activity,0.5f,1.0f);
    }

    public static void darkWindow(Activity activity){
        setWindowAlpha(activity,1.0f,0.5f);
    }

    private static void setWindowAlpha(Activity activity,final float from, final float to){
        if (activity == null){
            return;
        }
        final Window window = activity.getWindow();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
        valueAnimator.setDuration(100);
        valueAnimator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                            WindowManager.LayoutParams params = window.getAttributes();
                            params.alpha = (Float) animation.getAnimatedValue();
                            window.setAttributes(params);
                     }
                    });
        valueAnimator.start();
        };


    public static  boolean isNavigationBarExist(@NonNull Activity activity){
        final String NAVIGATION= "navigationBarBackground";
        ViewGroup vp = (ViewGroup) activity.getWindow().getDecorView();
        if (vp != null) {
            for (int i = 0; i < vp.getChildCount(); i++) {
                vp.getChildAt(i).getContext().getPackageName();
                if (vp.getChildAt(i).getId()!= NO_ID && NAVIGATION.equals(activity.getResources().getResourceEntryName(vp.getChildAt(i).getId()))) {
                    return true;
                }
            }
        }
        return false;
    }

}
