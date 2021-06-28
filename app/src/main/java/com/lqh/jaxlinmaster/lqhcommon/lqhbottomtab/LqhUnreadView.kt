package com.lqh.jaxlinmaster.lqhcommon.lqhbottomtab

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.lqh.jaxlinmaster.R
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtils
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.RegexUtil

/**
 * Created by Linqh on 2021/6/25.
@describe:
 *
 */
//@CreateUidAnnotation(uid = "10100")
class LqhUnreadView @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(
    mContext, attrs, defStyleAttr
) {
    var unreadText: String? = null
    var unreadNumber: Int = 0
    var circleNumberCount: Int = 1//当子小于或者等于这个的时候显示圆形
    var maxNumber: Int = 99 //大于这个值显示maxNumber+
    var minNumber: Int = 0 //等于最小值显示小红点，小于最小值就隐藏控件

    init {
        LogUtils.e("LqhUnreadView", "init")
        val ta = mContext.obtainStyledAttributes(attrs, R.styleable.LqhUnreadView)
        unreadText = ta.getString(R.styleable.LqhUnreadView_unr_unreadText)
        circleNumberCount = ta.getInteger(R.styleable.LqhUnreadView_unr_circleNumberCount, 1)
        unreadNumber = ta.getInteger(R.styleable.LqhUnreadView_unr_unNumber, -2)
        maxNumber = ta.getInteger(R.styleable.LqhUnreadView_unr_maxNumber, Int.MAX_VALUE)
        minNumber = ta.getInteger(R.styleable.LqhUnreadView_unr_minNumber, Int.MIN_VALUE)
        setUnreadNum(unreadNumber)
        ta.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (!TextUtils.isEmpty(unreadText) && unreadText!!.length <= circleNumberCount) {
            val maxWidth = Math.max(measuredWidth, measuredHeight)
            val measureSpec = MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.EXACTLY)
            super.onMeasure(measureSpec, measureSpec)
        }
    }

    //设置数字
    public fun setUnreadNum(number: Int) {
        unreadNumber=number
        this.visibility = View.VISIBLE
        if (layoutParams != null) {
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        }
        var tempStr = number.toString()
        if (number> maxNumber) {
            tempStr = "$maxNumber+"
        } else if (number == minNumber) {
            if (layoutParams != null) {
                layoutParams.width = 20
                layoutParams.height = 20
                tempStr = ""
            }
        } else if (number < minNumber) {
            this.visibility = View.GONE
        }
        text = tempStr
    }

    //设置setUnreadStr就直接隐藏了
    fun setUnreadStr(unreadStr: String?) {
        this.unreadText=unreadStr
        if (TextUtils.isEmpty(unreadStr)) {
            this.visibility = View.GONE
            return
        }
        this.visibility = View.VISIBLE
        text = unreadText
    }


}