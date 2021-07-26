package com.lqh

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import com.google.android.material.tabs.TabLayout
import com.lqh.jaxlinmaster.R
import com.lqh.jaxlinmaster.homepager.*
import com.lqh.jaxlinmaster.lqhbase.*
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtils
import kotlinx.android.synthetic.main.fragment_a.*

/**
 * Created by Linqh on 2021/6/24.
@describe:
 *
 */
//@CreateUidAnnotation(uid = "10100")
class FragmentA() : LqhBaseFragment() {
    var currentPosition: Int = 0
    val TAG_CURPOS = "tag_curpos"
    private val TAG_POSITONSTR = arrayOf("subA", "subB", "subC", "subD", "subE")
    private val titles = arrayOf("FA_subA标题", "FA_subB标题", "FA_subC标题", "FA_subD标题")
     var tabTitles = arrayOf("tabA", "tabB", "tabC", "tabD")
    private var title: String?=null
    private var fragmentList = mutableListOf<LqhBaseFragment>()
    companion object{
        fun newInstance(title: String): FragmentA {
            var testFragment = FragmentA()
            val bundle = Bundle()
            bundle.putString("title", title)
            testFragment.arguments = bundle
            return testFragment;
        }
    }


//    override fun lazyInit(isFirstLoad: Boolean) {
//        LogUtils.e("主当前$title", "isFirstLoad:"+isFirstLoad)
//    }

//    override fun invisibleInit(isSetUserVisibleHint: Boolean) {
//        super.invisibleInit(isSetUserVisibleHint)
//        LogUtils.e("主当前$title", "invisibleInit是否由isSetUserVisibleHint触发:"+isSetUserVisibleHint)
//    }
    override fun initView(layout: View) {

        tv_title.text =title
        tv_go.setOnClickListener {
            jumpToClass(DialogAllActivity::class.java,null)
        }
        initFragment()
        for (item in tabTitles){
            tab.addTab(tab.newTab().setText(item))
        }
        //changeFragment(0,false)
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(p0: TabLayout.Tab) {
                //changeFragment(p0.position,false)
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

        })
        var testFragmentPagerAdapter = TestFragmentPagerAdapter(
            childFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT,
            fragmentList
        )
        viewpager.adapter = testFragmentPagerAdapter
        tab.setupWithViewPager(viewpager)
    }
//    private fun changeFragment(showPosition: Int,isforX:Boolean) {
//        val beginTransaction = childFragmentManager.beginTransaction()
//        beginTransaction.apply {
//            var fromFragment = fragmentList[currentPosition]
//            var toFragment = fragmentList[showPosition]
//            if (fromFragment != null&&fromFragment!=toFragment && fromFragment.isAdded) {
//                LogUtils.e("fragment","添加过")
//                hide(fromFragment)
//                if (isforX){
//                    setMaxLifecycle(fromFragment, Lifecycle.State.STARTED)
//                }
//            }
//            if (toFragment != null) {
//                if (!toFragment.isAdded) {
//                    add(R.id.frame_layout, toFragment, TAG_POSITONSTR[showPosition])
//
//                } else {
//                    show(toFragment)
//                }
//                if (isforX){
//                    setMaxLifecycle(toFragment, Lifecycle.State.RESUMED)
//                }
//            }
//
//        }.commitAllowingStateLoss()
//
//
//
//        currentPosition = showPosition
//        tab.post {
//            LogUtils.e("主当前子fragment有几个Fragment", "" + childFragmentManager.fragments.size)
//        }
//    }
    private fun initFragment() {
        fragmentList.add(HomeFragmentA.newInstance(titles[0]))
        fragmentList.add(HomeFragmentB.newInstance(titles[1]))
        fragmentList.add(HomeFragmentC.newInstance(titles[2]))
        fragmentList.add(HomeFragmentD.newInstance(titles[3]))

    }

    override fun getLayoutId(): Int = R.layout.fragment_a
    override fun onAttach(context: Context) {
        super.onAttach(context)
        title=arguments?.getString("title")
        LogUtils.e("主当前$title", "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LogUtils.e("主当前$title", "onCreate")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtils.e("主当前$title", "onViewCreated")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogUtils.e("主当前$title", "onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        LogUtils.e("主当前$title", "onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.e("主当前$title", "在onResume中判断isHidden"+isHidden)
        LogUtils.e("主当前$title", "onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.e("主当前$title", "onPause")
        LogUtils.e("主当前$title", "在onPause中判断isHidden"+isHidden)
    }

    override fun onStop() {
        super.onStop()
        LogUtils.e("主当前$title", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtils.e("主当前$title", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.e("主当前$title", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        LogUtils.e("主当前$title", "onDetach")
    }
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        title=arguments?.getString("title")
        //居然有时候获取不到
        LogUtils.e("主当前$title", "isVisibleToUser:$isVisibleToUser")
        LogUtils.e("主当前$title", "在setUserVisibleHint中判断isHidden"+isHidden)
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        LogUtils.e("主当前$title", "onHiddenChanged:$hidden")
        LogUtils.e("主当前$title", "在onHiddenChanged中判断isHidden"+isHidden)
    }

//     override fun invisibleInit(isSetUserVisibleHint: Boolean){
//        LogUtils.e("主当前$title", "invisibleInit:"+isSetUserVisibleHint)
//    }

    inner class TestFragmentPagerAdapter(
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
        return tabTitles[position]
    }

    init {
        this.fragments = fragments
    }
}
}