package com.lqh.jaxlinmaster.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StyleRes;

import com.lqh.jaxlinmaster.R;
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtils;

import java.lang.reflect.Field;

public class CenterDialog extends Dialog {
    public View layoutView;
    private Context context;
    private int height;
    private int width;
    private boolean setWidth;
    private boolean isCancelable = true;
    public CenterDialog(@NonNull Context context, int  viewId,int style) {
        super(context,style);
        this.layoutView = LayoutInflater.from(context).inflate(viewId,null);
        this.context = context;
    }
    public CenterDialog(@NonNull Context context, int viewId, int width, @StyleRes int themeResId) {
        super(context, themeResId);
        this.layoutView = LayoutInflater.from(context).inflate(viewId,null);
        this.context = context;
        this.width = width;
    }

    public CenterDialog(@NonNull Context context, View layoutView) {
        super(context, R.style.CommonDialog);
        this.layoutView = layoutView;
        this.context = context;
    }

    public CenterDialog(@NonNull Context context, View layoutView, int width) {
        super(context, R.style.CommonDialog);
        this.layoutView = layoutView;
        this.context = context;
        this.width = width;
    }

    public CenterDialog(@NonNull Context context, View layoutView, int width, int height) {
        super(context, R.style.Theme_Dialog_Scale);
        this.layoutView = layoutView;
        this.context = context;
        this.width = width;
        this.height = height;
    }

    public CenterDialog(@NonNull Context context, View layoutView, int width, int height, @StyleRes int themeResId) {
        super(context, themeResId);
        this.layoutView = layoutView;
        this.context = context;
        this.width = width;
        this.height = height;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_test);
        initValues();
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initValues() {
        Window window = getWindow();
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.getDecorView().setPadding(0,0,0,0);
        window.getDecorView().setBackgroundColor(Color.GREEN);
        window.setGravity(Gravity.TOP);
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 1000);
        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // 延伸显示区域到刘海
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            window.setAttributes(lp);
        }
        window.getDecorView().setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {

            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                LogUtils.e("间距左",""+insets.getSystemWindowInsetLeft());
                LogUtils.e("间距左Stable",""+insets.getStableInsetLeft());
                LogUtils.e("间距右",""+insets.getSystemWindowInsetRight());
                LogUtils.e("间距右Stable",""+insets.getStableInsetRight());
                WindowManager.LayoutParams  layoutParams = (WindowManager.LayoutParams) window.getDecorView().getLayoutParams();
                LogUtils.e("layoutParams_horizontalMargin",""+layoutParams.horizontalMargin);
                LogUtils.e("layoutParams_width",""+layoutParams.width);
                return insets;
            }
        });

// 设置页面全屏显示
//        final View decorView = window.getDecorView();
//        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//        );
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(Color.TRANSPARENT);
//        window.setNavigationBarColor(Color.BLUE);


//        DisplayMetrics dm = context.getResources().getDisplayMetrics();
//        if (setWidth) {
//            lp.width = dm.widthPixels;
//            lp.height=dm.heightPixels;
//        }
//        if (height > 0) {
//            lp.height = height;
//        }
//        if (width > 0) {
//            lp.width = width;
//        }
//        lp.gravity = Gravity.CENTER;
//        window.setAttributes(lp);
    }

    public View getLayoutView() {
        return layoutView;
    }

    public CenterDialog cancelable(boolean isCancel) {
        this.isCancelable = isCancel;
        this.setCancelable(isCancel);
        return this;
    }

    @Override
    public void show() {

        super.show();

    }

    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        super.setOnDismissListener(listener);
    }

    @Override
    public void dismiss() {
        if (beforeDismissListener != null) {
            beforeDismissListener.beforeDismiss();
        }
        super.dismiss();
    }

    public interface BeforeDismissListener {
        void beforeDismiss();
    }

    private BeforeDismissListener beforeDismissListener;

    public void setOnBeforeDismissListener(BeforeDismissListener listener) {
        this.beforeDismissListener = listener;
    }


    public void setRightBtnClickEnable(int viewId, boolean enable) {
        setViewEnable(viewId, enable, 1.0f, 0.5f);
    }

    public void setRightBtnClickEnable(View childView, boolean enable) {
        setViewEnable(childView, enable, 1.0f, 0.5f);
    }

    public void setViewEnable(int viewId, boolean enable, float enableAlpha, float unableAlpha) {
        View layoutView = getLayoutView();
        if (layoutView != null) {
            View childView = layoutView.findViewById(viewId);

            if (childView != null) {
                setViewEnable(childView, enable, enableAlpha, unableAlpha);
            }
        }
    }

    public void setViewEnable(View childView, boolean enable, float enableAlpha, float unableAlpha) {
        if (childView != null) {
            childView.setEnabled(enable);
            childView.setAlpha(enable ? enableAlpha : unableAlpha);
        }
    }
}
