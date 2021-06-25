package com.lqh.jaxlinmaster.lqhcommon.lqhbottomtab

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import com.lqh.jaxlinmaster.R

/**
 * Created by Linqh on 2021/6/25.
@describe:
 *
 */
//@CreateUidAnnotation(uid = "10100")
class LqhUnreadView(private val mContext: Context,
                    attrs: AttributeSet? = null,
                    defStyleAttr: Int = 0) :androidx.appcompat.widget.AppCompatTextView(
    mContext, attrs, defStyleAttr
){
    var unreadText:String?=null
    var circleNumberCount:Int=1
           init {
               val ta = mContext.obtainStyledAttributes(attrs, R.styleable.LqhUnreadView)
               unreadText = ta.getString(R.styleable.LqhUnreadView_unreadText)
               circleNumberCount=  ta.getInteger(R.styleable.LqhUnreadView_circleNumberCount,1)
               if (!TextUtils.isEmpty(unreadText)){
                   text = unreadText
               }
               ta.recycle()
           }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (!TextUtils.isEmpty(unreadText)&&unreadText!!.length<=circleNumberCount){
            val maxWidth = Math.max(measuredWidth,measuredHeight)
            val measureSpec = MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.EXACTLY)
            super.onMeasure(measureSpec, measureSpec)
        }
    }
}