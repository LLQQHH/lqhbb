package com.lqh.jaxlinmaster.lqhwidget.lqhbottomtab

import androidx.viewpager2.widget.ViewPager2


/**
 * Created by Linqh on 2021/6/25.
@describe:
 *
 */
//@CreateUidAnnotation(uid = "10100")
class LqhBottomTabMediator(var viewPager2: ViewPager2,var lqhBottomTab: LqhBottomTab) : ViewPager2.OnPageChangeCallback(),LqhBottomTab.OnTabSelectedListener {

    override fun onPageSelected(position: Int) {
        if (lqhBottomTab.mCurrentItem!=position){
            lqhBottomTab.setSelectItem(position)
        }
    }

    override fun onTabSelected(position: Int) {
        //自己判断 position是否相等
        viewPager2.currentItem = position
    }

    override fun onTabUnselected(position: Int) {

    }

    override fun onTabReselected(position: Int) {

    }
    fun attach(){
        viewPager2.registerOnPageChangeCallback(this)
        lqhBottomTab.addOnTabSelectedListener(this)
    }
}