package com.lqh.jaxlinmaster.lqhcommon.lqhbottomtab

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

/**
 * Created by Linqh on 2021/6/22.
@describe:
 *
 */
//@CreateUidAnnotation(uid = "10100")
class UIUtils {
    companion object {
        /**
         * dip-->px
         */
        fun dp2Px(context: Context, dip: Int): Int {
            // px/dip = density;
            // density = dpi/160
            // 320*480 density = 1 1px = 1dp
            // 1280*720 density = 2 2px = 1dp
            val density: Float = context.getResources().getDisplayMetrics().density
            return (dip * density + 0.5f).toInt()
        }
        fun px2dp(context: Context, pxValue: Float): Int {
            val scale: Float = context.getResources().getDisplayMetrics().density
            return (pxValue / scale + 0.5f).toInt()
        }

        /**
         * 将sp值转换为px值，保证文字大小不变
         *
         * @param spValue
         * @return
         */
        fun sp2px(context: Context, spValue: Float): Int {
            val fontScale: Float = context.getResources().getDisplayMetrics().scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }


        fun getDrawable(context: Context, resId: Int): Drawable? {
            return    ContextCompat.getDrawable(context,resId)
        }
        fun getColor(context: Context, colorId: Int): Int {
            return ContextCompat.getColor(context,colorId)
        }
    }

}