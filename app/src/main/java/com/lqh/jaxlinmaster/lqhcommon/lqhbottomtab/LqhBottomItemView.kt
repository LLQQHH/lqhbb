package com.lqh.jaxlinmaster.lqhcommon.lqhbottomtab

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.lqh.jaxlinmaster.R
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtils

/**
 * Created by Linqh on 2021/5/27.
 *
 * @describe:
 */
//@CreateUidAnnotation(uid = "10100")
 class LqhBottomItemView @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(mContext, attrs, defStyleAttr) {
    constructor(build:Builder):this(build.context){
        //次构造方法可以初始化代码
        textColorNormal=build.textColorNormal
        textColorSelected=build.textColorSelected
        textSizeNormal=build.textSizeNormal
        textSizeSelected=build.textSizeSelected
        iconMargin=build.iconMargin
        normalIcon=build.normalIcon
        selectedIcon=build.selectedIcon
        itemText=build.itemText
        customLayout=build.customLayout
        LogUtils.e("执行顺序","次constructor")
    }
    init {
        LogUtils.e("执行顺序","init")
        init(attrs)
    }
    var textColorNormal: Int = 0
    var textColorSelected: Int = 0
    var textSizeNormal: Int = 0
    var textSizeSelected: Int = 0
    var iconMargin: Int = 0
    var normalIcon: Drawable? = null
    var selectedIcon: Drawable? = null
    var itemText: String? = null

    var customLayout: View? = null
    var defaultLayout: View? = null
    var config: LqhBottomTab.Config? = null

    var tvTitle: TextView? = null
    var ivIcon: ImageView? = null

    private fun init(attrs: AttributeSet?) {
        val ta = mContext.obtainStyledAttributes(attrs, R.styleable.LqhBottomItemView)
        textColorNormal = ta.getColor(
            R.styleable.LqhBottomItemView_lqhitem_textColorNormal,
            0
        )
        textColorSelected = ta.getColor(
            R.styleable.LqhBottomItemView_lqhitem_textColorSelected,
            0
        )
        textSizeNormal = ta.getDimensionPixelSize(
            R.styleable.LqhBottomItemView_lqhitem_textSizeNormal,
            0
        )
        textSizeSelected = ta.getDimensionPixelSize(
            R.styleable.LqhBottomItemView_lqhitem_textSizeSelected,
            0
        )
        iconMargin = ta.getDimensionPixelSize(R.styleable.LqhBottomItemView_lqhitem_iconMargin, 0)
        normalIcon = ta.getDrawable(R.styleable.LqhBottomItemView_lqhitem_iconNormal)
        selectedIcon = ta.getDrawable(R.styleable.LqhBottomItemView_lqhitem_iconSelected)
        itemText = ta.getString(R.styleable.LqhBottomItemView_lqhitem_itemText)
        var customLayoutId: Int =
            ta.getResourceId(R.styleable.LqhBottomItemView_lqhitem_customLayoutId, 0)
        if (customLayoutId != 0) {
            customLayout =
                LayoutInflater.from(mContext).inflate(customLayoutId, this, false)
        }
        defaultLayout =
            LayoutInflater.from(mContext).inflate(R.layout.layout_item_lqhbottom, this, false)
        ta.recycle()
        initView()
    }


    //这是初始化View
    private fun initView() {
        tvTitle = defaultLayout!!.findViewById<TextView>(R.id.tv_title)
        ivIcon = defaultLayout!!.findViewById<ImageView>(R.id.iv_icon)
        if (customLayout != null) {
            removeView(defaultLayout)
            if (this != customLayout!!.parent) {
                if (customLayout!!.parent != null) {
                    (customLayout!!.parent as ViewGroup).removeView(customLayout)
                }
                addView(customLayout)
            }
        } else {
            removeView(customLayout)
            if (this != defaultLayout!!.parent) {
                if (defaultLayout!!.parent != null) {
                    (defaultLayout!!.parent as ViewGroup).removeView(defaultLayout)
                }
                addView(defaultLayout)
            }
        }
        updateView()
    }

     fun updateView() {
        orientation = LinearLayout.VERTICAL
        //gravity = Gravity.CENTER
        if (!itemText.isNullOrEmpty()) {
            tvTitle?.text = itemText
            tvTitle?.visibility = View.VISIBLE
            if (normalIcon != null) {
                if (iconMargin != 0) {
                    (tvTitle?.layoutParams as MarginLayoutParams).topMargin = iconMargin
                } else {
                    if (config != null) {
                        (tvTitle?.layoutParams as MarginLayoutParams).topMargin =config!!.iconMargin
                    }
                }
            } else {
                (tvTitle?.layoutParams as MarginLayoutParams).topMargin = 0
            }
        } else {
            tvTitle?.visibility = View.GONE
        }
        if (textColorNormal != 0) {
            tvTitle?.setTextColor(textColorNormal)
        } else {
            if (config != null) {
                tvTitle?.setTextColor(config!!.textColorNormal)
            }
        }
        if (textSizeNormal != 0) {
            tvTitle?.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeNormal.toFloat())
        } else {
            if (config != null) {
                tvTitle?.setTextSize(TypedValue.COMPLEX_UNIT_PX, config!!.textSizeNormal.toFloat())
            }
        }
        if (normalIcon != null) {
            ivIcon?.setImageDrawable(normalIcon)
        }
    }

    fun refreshTab(isSelected: Boolean) {
        setSelected(isSelected)
        if (isSelected) {
            if (textColorSelected != 0) {
                tvTitle?.setTextColor(textColorSelected)
            } else {
                if (config != null) {
                    tvTitle?.setTextColor(config!!.textColorSelected)
                }
            }
            if (textSizeSelected != 0) {
                tvTitle?.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeSelected.toFloat())
            } else {
                if (config != null) {
                    tvTitle?.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        config!!.textSizeSelected.toFloat()
                    )
                }
            }
            if (selectedIcon != null) {
                ivIcon?.setImageDrawable(selectedIcon)
            }
        } else {
            if (textColorNormal != 0) {
                tvTitle?.setTextColor(textColorNormal)
            } else {
                if (config != null) {
                    tvTitle?.setTextColor(config!!.textColorNormal)
                }
            }
            if (textSizeNormal != 0) {
                tvTitle?.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeNormal.toFloat())
            } else {
                if (config != null) {
                    tvTitle?.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX,
                        config!!.textSizeNormal.toFloat()
                    )
                }
            }
            if (normalIcon != null) {
                ivIcon?.setImageDrawable(normalIcon)
            }
        }
    }
    class Builder(var context: Context) {
        var textColorNormal: Int = 0
        var textColorSelected: Int = 0
        var textSizeNormal: Int = 0
        var textSizeSelected: Int = 0
        var iconMargin: Int = 0
        var normalIcon: Drawable? = null
        var selectedIcon: Drawable? = null
        var itemText: String? = null
        var customLayout: View? = null
        fun build():LqhBottomItemView{
           return LqhBottomItemView(this)
        }
        fun setTextColorNormal(textColorNormal:Int):Builder{
            this.textColorNormal=textColorNormal
            return this
        }
        fun setTextColorSelected(textColorSelected:Int):Builder{
            this.textColorSelected=textColorSelected
            return this
        }
        fun setTextSizeNormal(textSizeNormal:Int):Builder{
            this.textSizeNormal=textSizeNormal
            return this
        }
        fun setTextSizeSelected(textSizeSelected:Int):Builder{
            this.textSizeSelected=textSizeSelected
            return this
        }
        fun setNormalIcon(normalIcon:Drawable?):Builder{
            this.normalIcon=normalIcon
            return this
        }
        fun setSelectedIcon(selectedIcon:Drawable?):Builder{
            this.selectedIcon=selectedIcon
            return this
        }
        fun setItemText(itemText:String):Builder{
            this.itemText=itemText
            return this
        }
        fun setCustomLayout(customLayout:View):Builder{
            this.customLayout=customLayout
            return this
        }
    }
}