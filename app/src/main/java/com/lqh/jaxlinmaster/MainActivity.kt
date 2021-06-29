package com.lqh.jaxlinmaster

import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lqh.jaxlinmaster.bean.LqhtestBean
import com.lqh.jaxlinmaster.lqhbase.LqhBaseLazyFragmentForX
import com.lqh.jaxlinmaster.lqhcommon.lqhbottomtab.LqhBottomItemView
import com.lqh.jaxlinmaster.lqhcommon.lqhbottomtab.LqhBottomTab
import com.lqh.jaxlinmaster.lqhcommon.lqhbottomtab.LqhBottomTabMediator
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtils
import com.lqh.jaxlinmaster.lqhtest.TestFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LqhtestBean("aa")
        LogUtils.e("name==============")
        LqhtestBean("cc", 5)
        lqhBottomTab.addOnTabSelectedListener(object : LqhBottomTab.OnTabSelectedListener {
            override fun onTabSelected(position: Int) {
                LogUtils.e("选中" + position)
                if (position == 0) {
                    lqhBottomTab.setUnreadNum(0, 5)
                } else if (position == 1) {
                    lqhBottomTab.hideUnread(0)
                } else if (position == 2) {
                    lqhBottomTab.showDot(0)
                } else if (position == 3) {
                    lqhBottomTab.setUnreadNum(0, 100)
                } else if (position == 4) {
                    lqhBottomTab.setUnreadStr(0, "new")
                }
                if (position == 2) {
                    val positionTabView = lqhBottomTab.getPositionTabView(position)
                    if (positionTabView?.customLayout != null) {
                        val ivDesk =
                            positionTabView.customLayout!!.findViewById<ImageView>(R.id.iv_desk)
                        ivDesk.setImageResource(R.drawable.ic_main_desk_normalcy)
                    }
                } else if (position == 1) {
                    val positionTabView = lqhBottomTab.getPositionTabView(position)
                    if (positionTabView?.customLayout != null) {
                        val ivDesk =
                            positionTabView.customLayout!!.findViewById<ImageView>(R.id.iv_flag)
                        ivDesk.setImageResource(R.drawable.ic_main_index)
                    }
                }

            }

            override fun onTabUnselected(position: Int) {
                LogUtils.e("未选中" + position)
                if (position == 2) {
                    val positionTabView = lqhBottomTab.getPositionTabView(position)
                    if (positionTabView?.customLayout != null) {
                        val ivDesk =
                            positionTabView.customLayout!!.findViewById<ImageView>(R.id.iv_desk)
                        ivDesk.setImageResource(R.drawable.ic_main_desk)
                    }
                } else if (position == 1) {
                    val positionTabView = lqhBottomTab.getPositionTabView(position)
                    if (positionTabView?.customLayout != null) {
                        val ivDesk =
                            positionTabView.customLayout!!.findViewById<ImageView>(R.id.iv_flag)
                        ivDesk.setImageResource(R.drawable.ic_main_index_normalcy)
                    }
                }
            }

            override fun onTabReselected(position: Int) {
                LogUtils.e("重复选中" + position)
            }

        })
        val buildLqhBottomItemView = LqhBottomItemView.Builder(this)
            .setItemText("测试4")
            .setNormalIcon(ContextCompat.getDrawable(this, R.drawable.ic_main_my_normalcy))
            .setSelectedIcon(ContextCompat.getDrawable(this, R.drawable.ic_main_my_pitchon))
            .build()
        buildLqhBottomItemView.gravity = Gravity.CENTER
        lqhBottomTab.addTab(buildLqhBottomItemView)
        var count: Int = 5
        fragmentList.clear()
        for (index in 0 until count) {
            var lqhBaseLazyFragmentForX = TestFragment("标题$index")
            fragmentList.add(lqhBaseLazyFragmentForX)
        }
//        var testFragmentPagerAdapter=TestFragmentPagerAdapter(supportFragmentManager,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,fragmentList)
//        viewpager.adapter=testFragmentPagerAdapter
//        lqhBottomTab.setViewPager(viewpager)
        var testFragmentPagerAdapter2 = TestFragmentStateAdapter(this, fragmentList)

        viewpager2.adapter = testFragmentPagerAdapter2
        LqhBottomTabMediator(viewpager2, lqhBottomTab).attach()
        lqhBottomTab.setSelectItem(1)
    }

    private var fragmentList = mutableListOf<LqhBaseLazyFragmentForX>()

    internal class TestFragmentPagerAdapter(
        fm: FragmentManager,
        behavior: Int,
        fragments: MutableList<LqhBaseLazyFragmentForX>?
    ) :
        FragmentPagerAdapter(fm, behavior) {
        private var fragments: MutableList<LqhBaseLazyFragmentForX>? = null
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

    internal class TestFragmentStateAdapter(
        fragmentActivity: FragmentActivity,
        fragments: List<LqhBaseLazyFragmentForX>?
    ) :
        FragmentStateAdapter(fragmentActivity) {
        private var fragments: List<LqhBaseLazyFragmentForX>? = null
        override fun createFragment(position: Int): Fragment {
            return fragments!![position]
        }

        override fun getItemCount(): Int {
            return fragments?.size ?: 0
        }

        init {
            this.fragments = fragments
        }
    }
}