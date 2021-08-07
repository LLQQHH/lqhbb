package com.lqh.jaxlinmaster.lqhwidget.lqhbottomtab

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.lqh.jaxlinmaster.R

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
    var unreadTextReal:String?=null//你设置什么就是什么
    var unreadText: String? = null//转换过的文字
    var circleNumberCount: Int = 1//当子小于或者等于这个的时候显示圆形
    var maxNumber: Int = 99 //大于这个值显示maxNumber+
    var minNumber: Int = 0 //小于等于最小值显示小红点
    var dotWidth: Int = 0

    init {
        val ta = mContext.obtainStyledAttributes(attrs, R.styleable.LqhUnreadView)
        unreadText = ta.getString(R.styleable.LqhUnreadView_unr_unreadText)
        circleNumberCount = ta.getInteger(R.styleable.LqhUnreadView_unr_circleNumberCount, 1)
        maxNumber = ta.getInteger(R.styleable.LqhUnreadView_unr_maxNumber, 99)
        minNumber = ta.getInteger(R.styleable.LqhUnreadView_unr_minNumber, 0)
        dotWidth = UIUtils.dp2Px(mContext, 8)
        setUnreadStr(unreadText)
        ta.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (!UIUtils.isEmpty(unreadText) && unreadText!!.length <= circleNumberCount) {
            val maxWidth = Math.max(measuredWidth, measuredHeight)
            val measureSpec = MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.EXACTLY)
            super.onMeasure(measureSpec, measureSpec)
        }
    }

    //设置数字
    public fun setUnreadNum(number: Int) {
        this.setUnreadStr(number.toString())
    }

    //设置未读消息
    fun setUnreadStr(unreadStr: String?) {
        this.unreadTextReal = unreadStr
         unreadText = unreadStr
        if (UIUtils.isEmpty(unreadText)) {
            text = unreadText
            this.visibility = View.GONE
            return
        }
        this.visibility = View.VISIBLE
        var tempLayoutParams = layoutParams
        if (tempLayoutParams != null) {
            tempLayoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            tempLayoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        }
        if (UIUtils.isNumber(unreadStr)) {
            var number = unreadStr!!.toInt()
            if (number > maxNumber) {
                unreadText = "$maxNumber+"
            } else if (number <= minNumber) {
                if (tempLayoutParams != null) {
                    tempLayoutParams.width = dotWidth
                    tempLayoutParams.height = dotWidth
                    unreadText = ""
                }
            }
        }
        if (tempLayoutParams != null) {
            this.layoutParams = tempLayoutParams
        }
        text = unreadText
    }

    //隐藏未读
    fun hideUnread() {
        this.visibility = View.GONE
    }

    //强制显示红点
    fun showDot() {
        showDot(0)
    }

    fun showDot(dotWidth: Int) {
        if (dotWidth != 0) {
            this.dotWidth = dotWidth
        }
        setUnreadNum(minNumber)
    }
}