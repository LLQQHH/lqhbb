package com.lqh.jaxlinmaster.lqhcommon.lqhbottomtab;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * Created by Linqh on 2021/5/27.
@describe:
 *
 */
//@CreateUidAnnotation(uid = "10100")
public class LqhBottomTab extends LinearLayout{
     private Context mContext;
     private int sex555;
    public LqhBottomTab(Context context) {
        this(context,null);
    }

    public LqhBottomTab(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LqhBottomTab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
    }
    public static class TabView extends LinearLayout{

        public TabView(Context context) {
            super(context);
        }
    }
}