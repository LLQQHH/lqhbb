package com.lqh.jaxlinmaster

import android.os.Bundle
import android.util.SparseArray
import android.view.Gravity
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lqh.*
import com.lqh.jaxlinmaster.lqhbase.BaseLazyFragmentForViewpagerX
import com.lqh.jaxlinmaster.lqhbase.LqhBaseActivity
import com.lqh.jaxlinmaster.lqhbase.LqhBaseFragment
import com.lqh.jaxlinmaster.lqhcommon.lqhbottomtab.LqhBottomItemView
import com.lqh.jaxlinmaster.lqhcommon.lqhbottomtab.LqhBottomTab
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtils
import com.lqh.jaxlinmaster.lqhtest.TestFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : LqhBaseActivity() {
    var currentPosition: Int = 0
    val TAG_CURPOS = "tag_curpos"
    private val TAG_POSITONSTR = arrayOf("A", "B", "C", "D", "E")
    private val titles = arrayOf("A标题", "B标题", "C标题", "D标题", "E标题")
    private var fragmentSparseArray = SparseArray<Fragment>()
    private var fragmentList = mutableListOf<LqhBaseFragment>()

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        LogUtils.e("当前activity", "onCreate");
//        if (savedInstanceState != null) {
//            currentPosition = savedInstanceState.getInt(TAG_CURPOS)
//            TAG_POSITONSTR.forEachIndexed { index, s ->
//                val findFragmentByTag =
//                    supportFragmentManager.findFragmentByTag(TAG_POSITONSTR[index])
//                if (findFragmentByTag != null) {
//                    fragmentSparseArray.put(index, findFragmentByTag)
//                }
//                //防止其他界面fragment还没有开始添加到supportFragmentManager中
//                titles.forEachIndexed { index, s ->
//                    if (fragmentSparseArray.get(index) == null) {
//                        fragmentSparseArray.put(index, TestFragment.newInstance(s))
//                    }
//                }
//            }
//        } else {
//            titles.forEachIndexed { index, s ->
//                var lqhBaseLazyFragmentForX = TestFragment.newInstance(s)
//                //fragmentList.add(lqhBaseLazyFragmentForX)
//                fragmentSparseArray.put(index, lqhBaseLazyFragmentForX)
//            }
//        }
        initFragment()
        val buildLqhBottomItemView = LqhBottomItemView.Builder(this)
            .setItemText("测试4")
            .setNormalIcon(ContextCompat.getDrawable(this, R.drawable.ic_main_my_normalcy))
            .setSelectedIcon(ContextCompat.getDrawable(this, R.drawable.ic_main_my_pitchon))
            .build()
        buildLqhBottomItemView.gravity = Gravity.CENTER
        lqhBottomTab.addTab(buildLqhBottomItemView)
        lqhBottomTab.setSelectItem(currentPosition)
        //changeFragment(currentPosition)
        lqhBottomTab.addOnTabSelectedListener(object : LqhBottomTab.OnTabSelectedListener {
            override fun onTabSelected(position: Int) {
                LogUtils.e("选中" + position)
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
                //changeFragment(position)
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

        var testFragmentPagerAdapter = TestFragmentPagerAdapter(
            supportFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
            fragmentList
        )
        viewpager.adapter = testFragmentPagerAdapter
        lqhBottomTab.setViewPager(viewpager)
//        var testFragmentPagerAdapter2 = TestFragmentStateAdapter(this, fragmentList)
//
//        viewpager2.adapter = testFragmentPagerAdapter2
//        LqhBottomTabMediator(viewpager2, lqhBottomTab).attach()
//        lqhBottomTab.setSelectItem(1)
//        indicator_view?.apply {
//            setIndicatorStyle(IndicatorStyle.CIRCLE)
//            setSlideMode(IndicatorSlideMode.WORM)
//            setSliderWidth(SizeUtils.dp2px(10.0f).toFloat(),SizeUtils.dp2px(10.0f).toFloat())
//            setSliderColor(Color.BLACK,Color.RED)
//            setSliderGap(SizeUtils.dp2px(20.0f).toFloat())
//            setupWithViewPager(viewpager2)
//        }
    }

    private fun initFragment() {
            fragmentList.add(FragmentA.newInstance(titles[0]))
            fragmentList.add(FragmentB.newInstance(titles[1]))
            fragmentList.add(FragmentC.newInstance(titles[2]))
            fragmentList.add(FragmentD.newInstance(titles[3]))
            fragmentList.add(FragmentE.newInstance(titles[4]))
    }

    //动态替换fragment
//    private fun changeFragment(showPosition: Int) {
//        val beginTransaction = supportFragmentManager.beginTransaction()
//        var fromFragment = fragmentList.get(currentPosition)
//        if (fromFragment != null && fromFragment.isAdded) {
//            LogUtils.e("fragment","添加过")
//            beginTransaction.hide(fromFragment)
//        }
//        val toFragment = fragmentList.get(showPosition)
//        if (toFragment != null) {
//            if (!toFragment.isAdded) {
//                beginTransaction.add(R.id.frame_layout, toFragment, TAG_POSITONSTR[showPosition]).commitAllowingStateLoss()
//            } else {
//                beginTransaction.show(toFragment).commitAllowingStateLoss()
//            }
//        }
//        currentPosition = showPosition
//        lqhBottomTab.post {
//            LogUtils.e("当前有几个Fragment", "" + supportFragmentManager.fragments.size)
//        }
//
//
//    }

    override fun initData(savedInstanceState: Bundle?) {
    }


    internal class TestFragmentPagerAdapter(
        fm: FragmentManager,
        behavior: Int,
        fragments: MutableList<LqhBaseFragment>?
    ) :
        FragmentStatePagerAdapter(fm, behavior) {
        private var fragments: MutableList<LqhBaseFragment>? = null
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
        fragments: List<BaseLazyFragmentForViewpagerX>?
    ) :
        FragmentStateAdapter(fragmentActivity) {
        private var fragments: List<BaseLazyFragmentForViewpagerX>? = null
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

    override fun onSaveInstanceState(outState: Bundle) {
        //第一种方案防止重建,直接取消的保存的fragment
//        val beginTransaction = supportFragmentManager.beginTransaction()
//            fragmentSparseArray.forEach { key, value ->
//                beginTransaction.remove(value)
//            }
//        beginTransaction.commitAllowingStateLoss()
//第二种方案
//        outState.putInt(TAG_CURPOS, currentPosition);
        super.onSaveInstanceState(outState)
        LogUtils.e("当前activity", "onSaveInstanceState");
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        LogUtils.e("当前activity", "onRestoreInstanceState");
    }
}