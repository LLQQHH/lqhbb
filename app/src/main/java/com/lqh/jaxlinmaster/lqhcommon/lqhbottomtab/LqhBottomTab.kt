package com.lqh.jaxlinmaster.lqhcommon.lqhbottomtab

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
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
), ViewPager.OnPageChangeListener {
    private var config: Config? = null

    //保存所有item
    private var mItemViews: MutableList<LqhBottomItemView> = mutableListOf()

    //监听器
    private var selectedListeners: MutableList<OnTabSelectedListener> = mutableListOf()
    private var onInterruptListener: OnInterruptListener? = null
    var mCurrentItem = -1 //当前条目的索引
    private var mViewPager: ViewPager? = null


    private fun init(attrs: AttributeSet?) {
        if (attrs == null) {
            return
        }
        val ta = mContext.obtainStyledAttributes(attrs, R.styleable.LqhBottomTab)
        var textColorNormal = ta.getColor(
            R.styleable.LqhBottomTab_lqhtab_item_textColorNormal,
            UIUtils.getColor(mContext, R.color.bbl_999999)
        )
        var textColorSelected = ta.getColor(
            R.styleable.LqhBottomTab_lqhtab_item_textColorSelected,
            UIUtils.getColor(mContext, R.color.bbl_ff0000)
        )
        var textSizeNormal = ta.getDimensionPixelSize(
            R.styleable.LqhBottomTab_lqhtab_item_textSizeNormal,
            UIUtils.dp2Px(mContext, 14)
        )
        var textSizeSelected = ta.getDimensionPixelSize(
            R.styleable.LqhBottomTab_lqhtab_item_textSizeSelected,
            UIUtils.dp2Px(mContext, 14)
        )
        var iconMargin =
            ta.getDimensionPixelSize(R.styleable.LqhBottomTab_lqhtab_item_iconMargin, 0)
        config?.textColorNormal = textColorNormal
        config?.textColorSelected = textColorSelected
        config?.textSizeNormal = textSizeNormal
        config?.textSizeSelected = textSizeSelected
        config?.iconMargin = iconMargin
        ta.recycle()
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        addConfigToTab(child)
        super.addView(child, index, params)
    }

    private fun addConfigToTab(child: View?) {
        if (child is LqhBottomItemView) {
            if (child.config == null) {
                child.config = this.config
                child.updateView()
            }
        }
    }

    public fun addTab(child: LqhBottomItemView) {
        val layoutParams = LayoutParams(0, LayoutParams.MATCH_PARENT)
        layoutParams.weight = 1f
        child.layoutParams = layoutParams
        addConfigToTab(child)
        addView(child)
        initChild()
    }

    private fun removetab(child: LqhBottomItemView) {
        removeView(child)
        initChild()
    }

    //这个是配置类,主要是用来配置item的
    class Config {
        var textColorNormal: Int = 0
        var textColorSelected: Int = 0
        var textSizeNormal: Int = 0
        var textSizeSelected: Int = 0
        var iconMargin: Int = 0
    }

    init {
        if (config == null) {
            config = Config()
        }
        init(attrs)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initChild()
    }

    //初始化开始
    private fun initChild() {
        mItemViews.clear()
        if (childCount == 0) {
            return
        }
        for (i in 0 until childCount) {
            var childView = getChildAt(i)
            if (getChildAt(i) is LqhBottomItemView) {
                mItemViews.add(childView as LqhBottomItemView)
                childView.setOnClickListener(LqhOnClickListener(i))
            }
        }
        if (mCurrentItem == -1) {
            changeSelected(mCurrentItem, 0)
        }
    }

    inner class LqhOnClickListener(private val clickPosition: Int) : OnClickListener {
        override fun onClick(v: View) {
            //判断有没有打断
            if (judgeCanSelected(mCurrentItem, clickPosition)) {
                if (mViewPager != null) {
                    if (clickPosition == mCurrentItem) {
                        //如果还是同个页签，使用setCurrentItem不会回调OnPageSelecte(),所以在此处需要回调点击监听
                        changeSelected(mCurrentItem, clickPosition)
                    } else {
                        mViewPager!!.setCurrentItem(clickPosition, false)
                    }
                } else {
                    changeSelected(mCurrentItem, clickPosition)
                }
            }
        }
    }

    interface OnTabSelectedListener {
        fun onTabSelected(position: Int)
        fun onTabUnselected(position: Int)
        fun onTabReselected(position: Int)
    }

    //这个用来是否打断点击事件，触发的后续事件，这个无法截断Viewpager通过手势的界面转换
    interface OnInterruptListener {
        fun onInterrupt(position: Int): Boolean
    }


    private fun judgeCanSelected(oldPosition: Int, newPosition: Int): Boolean {
        return onInterruptListener == null || !onInterruptListener!!.onInterrupt(newPosition)
    }

    fun changeSelected(oldPosition: Int, newPosition: Int) {
        //重置样式
        resetStateUI()
        //更改选中样式
        updateSelectedTabUI(newPosition)
        //重新赋值
        mCurrentItem = newPosition
        //监听都发出去
        dispatchTabListener(oldPosition, newPosition)

    }

    //分发监听
    private fun dispatchTabListener(oldPosition: Int, newPosition: Int) {
        if (selectedListeners.size > 0) {
            for (listener in selectedListeners) {
                if (oldPosition == newPosition) {
                    listener.onTabReselected(newPosition)
                } else {
                    if (oldPosition != -1) {
                        listener.onTabUnselected(oldPosition)
                    }
                    listener.onTabSelected(newPosition)
                }
            }
        }
    }

    private fun updateSelectedTabUI(newPosition: Int) {
        if (mItemViews.size > newPosition) {
            mItemViews[newPosition].refreshTab(true)
        }
    }

    /**
     * 重置当前按钮的状态
     */
    private fun resetStateUI() {
        if (mCurrentItem > -1 && mCurrentItem < mItemViews.size) {
            if (mItemViews[mCurrentItem].isSelected) {
                mItemViews[mCurrentItem].refreshTab(false)
            }
        }
    }

    fun addOnTabSelectedListener(listener: OnTabSelectedListener) {
        if (!selectedListeners.contains(listener)) {
            selectedListeners.add(listener)
        }
    }

    fun removeOnTabSelectedListener(listener: OnTabSelectedListener) {
        selectedListeners.remove(listener)
    }

    fun getPositionTabView(position: Int): LqhBottomItemView? {
        if (position >= 0 && position < mItemViews.size - 1) {
            return mItemViews[position]
        }
        return null
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        changeSelected(mCurrentItem, position)
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    fun setViewPager(viewPager: ViewPager) {
        if (this.mViewPager != null) {
            this.mViewPager?.removeOnPageChangeListener(this)
        }
        this.mViewPager = viewPager
        if (mViewPager != null) {
            val adapter: PagerAdapter? = mViewPager?.adapter
            require(!(adapter != null && adapter.count != childCount)) { "子View数量必须和ViewPager条目数量一致" }
        }
        if (mViewPager != null) {
            this.mViewPager?.removeOnPageChangeListener(this)
            this.mViewPager?.addOnPageChangeListener(this)
        }
    }

    fun setSelectItem(clickPosition: Int) {
        if (judgeCanSelected(mCurrentItem, clickPosition)) {
            if (mViewPager != null) {
                if (clickPosition == mCurrentItem) {
                    //如果还是同个页签，使用setCurrentItem不会回调OnPageSelecte(),所以在此处需要回调点击监听
                    changeSelected(mCurrentItem, clickPosition)
                } else {
                    mViewPager!!.setCurrentItem(clickPosition, false)
                }
            } else {
                changeSelected(mCurrentItem, clickPosition)
            }
        }
    }
}