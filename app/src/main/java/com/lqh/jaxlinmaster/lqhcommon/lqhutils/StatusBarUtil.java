package com.lqh.jaxlinmaster.lqhcommon.lqhutils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

/**
 * Created by Linqh on 2021/8/10.
 *
 * @describe:说明一下为什么我要用
 */
//@CreateUidAnnotation(uid = "10100")
public class StatusBarUtil {
    //这个是库里面自动添加的和状态栏一样高的View的Tag
    private static final String TAG_STATUS_BAR = "TAG_STATUS_BAR";
    //这个是标记自己添加的和状态栏一样高的View
    private static final int TAG_KEY_HAVE_SET_OFFSET = -123;

    public static void setStatusBarColor(Activity activity, @ColorInt int color) {
        setStatusBarColor(activity, color, true);
    }

    /**
     * 这个方法布局没有入侵到状态栏,adjustSize有效不用适配
     *
     * @param activity
     * @param color    状态栏的颜色,区分版本
     * @param isDecor  和状态栏大小一样的View的父布局在哪里
     */
    public static void setStatusBarColor(Activity activity, @ColorInt int color, boolean isDecor) {
        activity.getWindow().getDecorView().setSystemUiVisibility(0);
        fitsNotchScreen(activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            addStatusBarView(activity, color, isDecor);
            setRootView(activity);
        }
    }

    public static void setStatusBarColorForOffset(Activity activity, @ColorInt int color, View needOffsetView) {
        setStatusBarColorForOffset(activity, color, needOffsetView, true);
    }

    public static void setStatusBarColorForOffset(Activity activity, @ColorInt int color, View needOffsetView, boolean isPadding) {
        setStatusBarColorForOffset(activity, color, needOffsetView, isPadding, true);
    }

    /**
     * 这个是为了真正入侵到状态栏而做的,但是会导致adjustSize失效，这个时候得去适配
     *
     * @param activity
     * @param color          跟状态栏一样大的那个View的颜色
     * @param isDecor        跟状态栏一样大的那个View添加到那个父布局,默认android.R.id.content
     * @param needOffsetView 要要偏移的View
     * @param isPadding      何种方式偏移
     */

    public static void setStatusBarColorForOffset(Activity activity, @ColorInt int color, View needOffsetView, boolean isPadding, final boolean isDecor) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        //刘海屏适配
        fitsNotchScreen(activity);
        //状态栏透明，并且完全入侵
        transparentStatusBar(activity);
        //增加一个完全一样的View不区分4.4和5.0!
        addStatusBarView(activity, color, isDecor);
        //要偏移的View
        setViewOffsetStatusBarHeight(needOffsetView, isPadding);
    }

    /**
     * @param activity
     * @param color
     * @param isInvade 是否要入侵到导航栏
     */
    //为什么只做这个而不再去添加一个跟View跟导航栏一样，是因为导航栏有没有存在，不好判断
    public static void setNavBarColor(@NonNull final Activity activity, @ColorInt final int color, boolean isInvade) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(color);
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION ;
//            int vis = window.getDecorView().getSystemUiVisibility();
//            window.getDecorView().setSystemUiVisibility(option | vis);
//            if (isInvade) {
//                window.getDecorView().setSystemUiVisibility(option | vis);
//            } else {
//                window.getDecorView().setSystemUiVisibility(option & ~vis);
//            }

        }
    }

    /**
     * Return the status bar's height.
     *
     * @return the status bar's height
     */
    public static int getStatusBarHeight() {
        Resources resources = Utils.getApp().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId != 0) {
            return resources.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    /**
     * Return the navigation bar's height.
     *
     * @return the navigation bar's height
     */
    public static int getNavBarHeight() {
        Resources res = Utils.getApp().getResources();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId != 0) {
            return res.getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }
    }

    /**
     * Set the status bar's light mode.
     *
     * @param activity    The activity.
     * @param isLightMode True to set status bar light mode, false otherwise.
     */
    public static void setStatusBarLightMode(@NonNull final Activity activity,
                                             final boolean isLightMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = activity.getWindow().getDecorView();
            int vis = decorView.getSystemUiVisibility();
            if (isLightMode) {
                vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decorView.setSystemUiVisibility(vis);
        }
    }

    /**
     * Is the status bar light mode.
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isStatusBarLightMode(@NonNull final Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = activity.getWindow().getDecorView();
            int vis = decorView.getSystemUiVisibility();
            return (vis & View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) != 0;
        }
        return false;
    }

    /**
     * Set the nav bar's light mode.
     *
     * @param activity    The activity.
     * @param isLightMode True to set nav bar light mode, false otherwise.
     */
    public static void setNavBarLightMode(@NonNull final Activity activity,
                                          final boolean isLightMode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            View decorView = activity.getWindow().getDecorView();
            int vis = decorView.getSystemUiVisibility();
            if (isLightMode) {
                vis |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            } else {
                vis &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            }
            decorView.setSystemUiVisibility(vis);
        }
    }

    /**
     * Is the nav bar light mode.
     *
     * @param activity The window.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isNavBarLightMode(@NonNull final Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            View decorView = activity.getWindow().getDecorView();
            int vis = decorView.getSystemUiVisibility();
            return (vis & View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR) != 0;
        }
        return false;
    }
    //这里说明一下为什么不在NavBar加View,因为我们很判断NavBar是否被隐藏,所以这里只做更给系统的NavBar颜色


    /**
     * 设置状态栏透明,而且布局会入侵到状态栏,记得去增加间隔
     * //这里说一下5.0以上,为什么不直接和4.4一样直接用,因为这样会直接有一个半透明效果那是系统绘制的,我们想要的是直接透明，然后画什么我们自己加一个View去画
     *
     * @param activity
     */
    private static void transparentStatusBar(@NonNull final Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            int vis = window.getDecorView().getSystemUiVisibility();
            window.getDecorView().setSystemUiVisibility(option|vis);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    /**
     * 整整全屏！
     *
     * @param activity
     * @param needOffsetView
     */
    public static void setFullScreenReal(Activity activity, View needOffsetView) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        fitsNotchScreen(activity);
        Window window = activity.getWindow();
        //这个等效于下面flag的第一个第二个
        //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        int option =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |View.INVISIBLE
                 | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        window.getDecorView().setSystemUiVisibility(option);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        hideCreateStatusBarView(activity);
        setViewOffsetStatusBarHeight(needOffsetView, true);
    }

    /**
     * 适配刘海屏
     * Fits notch screen.
     */
    private static void fitsNotchScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Window mWindow = activity.getWindow();
            WindowManager.LayoutParams lp = mWindow.getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            mWindow.setAttributes(lp);
        }
    }


    public static void setView2StatusBarHeight(View view) {
        setView2StatusBarHeight(view, null);
    }

    /**
     * 设置需要和状态栏一样高度的View
     *
     * @param view 使用场景,某个View需要和状态栏一样大
     */
    public static void setView2StatusBarHeight(View view, Integer color) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        if (view == null) {
            return;
        }
        int statusBarHeight = getStatusBarHeight();
        view.setVisibility(View.VISIBLE);
        if (view.getLayoutParams().height != statusBarHeight) {
            view.getLayoutParams().height = statusBarHeight;
        }
        view.getLayoutParams().height = statusBarHeight;
        if (color != null) {
            view.setBackgroundColor(color);
        }
    }

    /**
     * 设置需要和状态栏一样高度的View
     *
     * @param needOffsetView 使用场景,某个View需要和状态栏一样大
     */
    public static void setViewOffsetStatusBarHeight(View needOffsetView, boolean isPadding) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        if (needOffsetView != null) {
            Object haveSetOffset = needOffsetView.getTag(TAG_KEY_HAVE_SET_OFFSET);
            if (haveSetOffset != null && (Boolean) haveSetOffset) {
                return;
            }
            int statusBarHeight = getStatusBarHeight();
            if (isPadding) {
                int paddingLeft = needOffsetView.getPaddingLeft();
                int paddingTop = needOffsetView.getPaddingTop();
                int paddingRight = needOffsetView.getPaddingRight();
                int paddingBottom = needOffsetView.getPaddingBottom();
                needOffsetView.setPadding(paddingLeft, paddingTop + statusBarHeight, paddingRight, paddingBottom);
                needOffsetView.setTag(TAG_KEY_HAVE_SET_OFFSET, true);
            } else {
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) needOffsetView.getLayoutParams();
                layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin + statusBarHeight,
                        layoutParams.rightMargin, layoutParams.bottomMargin);
                needOffsetView.setTag(TAG_KEY_HAVE_SET_OFFSET, true);
            }
        }
    }


    private static View addStatusBarView(Activity activity, @ColorInt int color, boolean isDecor) {
        ViewGroup parent = isDecor ?
                (ViewGroup) activity.getWindow().getDecorView() :
                (ViewGroup) activity.getWindow().findViewById(android.R.id.content);
        View fakeStatusBarView = parent.findViewWithTag(TAG_STATUS_BAR);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(color);
        } else {
            parent.addView(createStatusBarView(activity, color));
        }
        return fakeStatusBarView;
    }

    /**
     * 专门为drawLayout做的
     *
     * @param drawerLayout
     */
    public static void setStatusBarColor4Drawer(Activity activity, DrawerLayout drawerLayout, final View fakeStatusBar, @ColorInt final int color
            , final boolean isTop) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        fitsNotchScreen(activity);
        //直接沉浸式
        transparentStatusBar(activity);
        drawerLayout.setFitsSystemWindows(false);
        for (int i = 0, count = drawerLayout.getChildCount(); i < count; i++) {
            drawerLayout.getChildAt(i).setFitsSystemWindows(false);
        }
        //把传进来的占位设置大小
        setView2StatusBarHeight(fakeStatusBar, null);
        //左边抽屉是否要在第0个子View状态栏颜色的上面
        if (isTop) {
            //要在状态栏上面，就要隐藏
            hideCreateStatusBarView(activity);
            setView2StatusBarHeight(fakeStatusBar, color);
        } else {
            //不要的话，就直接一个状态栏
            addStatusBarView(activity, color, true);
        }
    }

    public static void hideCreateStatusBarView(@NonNull final Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewWithTag(TAG_STATUS_BAR);
        if (fakeStatusBarView == null) return;
        fakeStatusBarView.setVisibility(View.GONE);
    }


    /**
     * 创建一个和状态栏一样大的View
     *
     * @param activity
     * @param color
     * @return
     */
    private static View createStatusBarView(Activity activity, @ColorInt int color) {
        // 绘制一个和状态栏一样高的矩形
        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight());
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(color);
        statusBarView.setTag(TAG_STATUS_BAR);
        return statusBarView;
    }

    /**
     * 用在4.4和5.0之间 这个设置setFitsSystemWindows=true,让布局能往下空出状态栏间距
     * 设置根布局参数
     */
    private static void setRootView(Activity activity) {
        ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(true);
                ((ViewGroup) childView).setClipToPadding(true);
            }
        }
    }

    //计算混合的颜色
    public static int calculateStatusColor(@ColorInt int color, int alpha) {
        if (alpha == 0) {
            return color;
        }
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    /**
     * 产生一个带透明度的颜色
     *
     * @param baseColor The color.初始颜色
     * @param alpha     Alpha component \([0..1]\) of the color.透明度，Alpha越大越不透明
     * @return the {@code color} with {@code alpha} component
     */
    public static int getColorWithAlpha(@ColorInt int baseColor,
                                        @FloatRange(from = 0, to = 1) float alpha) {
        return (baseColor & 0x00ffffff) | ((int) (alpha * 255.0f + 0.5f) << 24);
    }
}
