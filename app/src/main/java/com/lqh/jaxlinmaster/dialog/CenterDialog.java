package com.lqh.jaxlinmaster.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

import com.lqh.jaxlinmaster.R;

public class CenterDialog extends Dialog {
    public View layoutView;
    private Context context;
    private int height;
    private int width;
    private boolean setWidth;
    public CenterDialog(@NonNull Context context, int  viewId,int style) {
        super(context, R.style.CommonDialog);
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
        setContentView(layoutView);
        initValues();
    }


    private void initValues() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        //宽度充满,这样宽度就会和xml一样,边距要多少，自己在xml里面写死
        //lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        if (setWidth) {
            lp.width = dm.widthPixels;
        }
        if (height > 0) {
            lp.height = height;
        }
        if (width > 0) {
            lp.width = width;
        }
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }

    public View getLayoutView() {
        return layoutView;
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


}
