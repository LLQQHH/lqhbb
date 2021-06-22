package com.lqh.jaxlinmaster.lqhcommon.lqhbottomtab

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.lqh.jaxlinmaster.R

/**
 * Created by Linqh on 2021/5/27.
 * @describe:
 */
//@CreateUidAnnotation(uid = "10100")
  class LqhBottomTab @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(
    mContext, attrs, defStyleAttr
) {
    private var config: Config? = null
    private fun init(attrs: AttributeSet?) {
        if (attrs == null) {
            return
        }
        val ta = mContext.obtainStyledAttributes(attrs, R.styleable.LqhBottomTab)
    }

    //这个是配置类,主要是用来配置item的
    class Config

    init {
        if (config == null) {
            config = Config()
        }
        init(attrs)
    }
}