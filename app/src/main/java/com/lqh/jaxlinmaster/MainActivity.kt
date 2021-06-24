package com.lqh.jaxlinmaster

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.lqh.jaxlinmaster.lqhbase.LqhBaseLazyFragmentForX
import com.lqh.jaxlinmaster.lqhcommon.lqhbottomtab.LqhBottomTab
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtils
import com.lqh.jaxlinmaster.lqhtest.TestFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lqhBottomTab.addOnTabSelectedListener(object :LqhBottomTab.OnTabSelectedListener{
            override fun onTabSelected(position: Int) {
                LogUtils.e("选中"+position)
                if (position==1){
                    val positionTabView = lqhBottomTab.getPositionTabView(position)
                    if (positionTabView?.customLayout != null){
                        val ivDesk = positionTabView.customLayout!!.findViewById<ImageView>(R.id.iv_desk)
                        ivDesk.setImageResource(R.drawable.ic_main_desk_normalcy)
                    }
                }

            }
            override fun onTabUnselected(position: Int) {
                LogUtils.e("未选中"+position)
                if (position==1){
                    val positionTabView = lqhBottomTab.getPositionTabView(position)
                    if (positionTabView?.customLayout != null){
                        val ivDesk = positionTabView.customLayout!!.findViewById<ImageView>(R.id.iv_desk)
                        ivDesk.setImageResource(R.drawable.ic_main_desk)
                    }
                }
            }

            override fun onTabReselected(position: Int) {
                LogUtils.e("重复选中"+position)
            }

        })
        var count:Int=3
        fragmentList.clear()
        for (index in 0 until count){
            var lqhBaseLazyFragmentForX=TestFragment("标题$index")
            fragmentList.add(lqhBaseLazyFragmentForX)
        }
        var testFragmentPagerAdapter=TestFragmentPagerAdapter(supportFragmentManager,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,fragmentList)
        viewpager.adapter=testFragmentPagerAdapter
        lqhBottomTab.setViewPager(viewpager)
        lqhBottomTab.setSelectItem(1)
    }
    private var fragmentList= mutableListOf<LqhBaseLazyFragmentForX>()
    internal class TestFragmentPagerAdapter(
        fm: FragmentManager,
        behavior: Int,
        fragments: MutableList<LqhBaseLazyFragmentForX>?
    ) :
        FragmentPagerAdapter(fm, behavior) {
        private var fragments: MutableList<LqhBaseLazyFragmentForX>?=null
        override fun getItem(position: Int): Fragment {
            return fragments!![position]
        }

        override fun getCount(): Int {
            return fragments?.size ?: 0
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return super.getPageTitle(position)
        }

        init {
            this.fragments = fragments
        }
    }
}