package com.lqh.jaxlinmaster.lqhcommon.lqhbottomtab;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Linqh on 2021/5/27.
 *
 * @describe:
 */
//@CreateUidAnnotation(uid = "10100")
class LqhBottomItemView extends FrameLayout {
    public LqhBottomItemView(@NonNull Context context) {
        this(context,null);
    }

    public LqhBottomItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LqhBottomItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
