package com.lqh.jaxlinmaster.lqhcommon.lqhutils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.drawerlayout.widget.DrawerLayout;

/**
 * @Author: david.lvfujiang
 * @Date: 2019/10/10
 * @Describe:这个类用来解决软键盘弹出来把界面覆盖的问题
 */
public class LqhSoftHideKeyBoardUtil {
    public static void assistActivity(Activity activity) {
        new LqhSoftHideKeyBoardUtil(activity);
    }

    //跟视图
    private ViewGroup rootView;
    private View changeView;
    private int initPaddingLeft;
    private int initPaddingTop;
    private int initPaddingRight;
    private int initPaddingBottom;
    private int lastVisiableRootLayoutHeight;//纪录上一次的可见布局

    private LqhSoftHideKeyBoardUtil(Activity activity) {
        this(activity, null);
    }

    private LqhSoftHideKeyBoardUtil(Activity activity, View changeView) {
        //找到Activity的contentView，它其实是一个DecorView的子View,它是FrameLayout
        rootView = (activity.getWindow().getDecorView()).findViewById(android.R.id.content);
        this.changeView = changeView;
        if (this.changeView == null) {
            this.changeView = rootView;
            initPaddingLeft = this.changeView.getPaddingLeft();
            initPaddingTop = this.changeView.getPaddingTop();
            initPaddingRight = this.changeView.getPaddingRight();
            initPaddingBottom = this.changeView.getPaddingBottom();
        }
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!checkFitsSystemWindows(rootView)) {
                    int currentVisibleRootLayoutHeight = computeCanVisibleHeight();
                    if (lastVisiableRootLayoutHeight != currentVisibleRootLayoutHeight) {
                        //获取rootView高度
                        int rootLayoutHeight = rootView.getHeight();
                        //取差值得到差值,认为是弹起键盘的高度
                        int keyBoardHeight = rootLayoutHeight - currentVisibleRootLayoutHeight;
                        if (keyBoardHeight > rootLayoutHeight / 4) {
                            if (changeView != null) {
                                changeView.setPadding(
                                        initPaddingLeft,
                                        initPaddingTop,
                                        initPaddingRight,
                                        keyBoardHeight + initPaddingBottom
                                );
                            }

                        } else {
                            if (changeView != null) {
                                changeView.setPadding(
                                        initPaddingLeft,
                                        initPaddingTop,
                                        initPaddingRight,
                                        initPaddingBottom
                                );
                            }

                        }
                    }
                    lastVisiableRootLayoutHeight = currentVisibleRootLayoutHeight;
                }
            }
        });
    }


    private int computeCanVisibleHeight() {
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        // rect.top其实是状态栏的高度，如果是全屏主题，直接 r.top就是0所以直接return rect.bottom就可以了
        return r.bottom - r.top; // r.height()
    }

    /**
     * 检查布局根节点是否使用了android:fitsSystemWindows="true"属性
     * Check fits system windows boolean.
     *
     * @param view the view
     * @return the boolean
     */
    public static boolean checkFitsSystemWindows(View view) {
        if (view == null) {
            return false;
        }
        if (view.getFitsSystemWindows()) {
            return true;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0, count = viewGroup.getChildCount(); i < count; i++) {
                View childView = viewGroup.getChildAt(i);
                if (childView instanceof DrawerLayout) {
                    if (checkFitsSystemWindows(childView)) {
                        return true;
                    }
                }
                if (childView.getFitsSystemWindows()) {
                    return true;
                }
            }
        }
        return false;
    }
}