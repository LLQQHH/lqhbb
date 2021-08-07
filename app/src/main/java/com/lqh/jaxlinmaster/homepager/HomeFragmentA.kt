package com.lqh.jaxlinmaster.homepager

import android.content.Context
import android.os.Bundle
import android.view.View
import com.lqh.jaxlinmaster.R
import com.lqh.jaxlinmaster.lqhbase.BaseLazyFragmentForSupport
import com.lqh.jaxlinmaster.lqhbase.BaseLazyFragmentForX
import com.lqh.jaxlinmaster.lqhbase.LqhBaseFragment
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtils
import kotlinx.android.synthetic.main.fragment_a.*

/**
 * Created by Linqh on 2021/6/24.
@describe:
 *
 */
//@CreateUidAnnotation(uid = "10100")
class HomeFragmentA() : BaseLazyFragmentForX() {

    private var title: String?=null
    companion object{
        fun newInstance(title: String): HomeFragmentA {
            var testFragment = HomeFragmentA()
            val bundle = Bundle()
            bundle.putString("title", title)
            testFragment.arguments = bundle
            return testFragment;
        }
    }




    override fun initView(layout: View) {

        tv_title.text =title

    }

    override fun getLayoutId(): Int = R.layout.fragment_b
    override fun onAttach(context: Context) {
        super.onAttach(context)
        title=arguments?.getString("title")
        LogUtils.e("当前$title", "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LogUtils.e("当前$title", "onCreate")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtils.e("当前$title", "onViewCreated")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogUtils.e("当前$title", "onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        LogUtils.e("当前$title", "onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.e("当前$title", "在onResume中判断isHidden"+isHidden)
        LogUtils.e("当前$title", "onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.e("当前$title", "onPause")
        LogUtils.e("当前$title", "在onPause中判断isHidden"+isHidden)
    }

    override fun onStop() {
        super.onStop()
        LogUtils.e("当前$title", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtils.e("当前$title", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.e("当前$title", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        LogUtils.e("当前$title", "onDetach")
    }
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        title=arguments?.getString("title")
        //居然有时候获取不到
        LogUtils.e("当前$title", "isVisibleToUser:$isVisibleToUser")
        LogUtils.e("当前$title", "在setUserVisibleHint判断isHidden"+isHidden)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        LogUtils.e("当前$title", "onHiddenChanged:$hidden")
        LogUtils.e("当前$title", "在onHiddenChanged中判断isHidden"+isHidden)
    }
    override fun onFragmentPause() {
        LogUtils.e("当前$title", "不可见onFragmentPause")
    }

    override fun onFragmentLazyInit(IsFirstVisible: Boolean) {
        LogUtils.e("当前$title", "可见isFirstLoad:"+IsFirstVisible)
    }


}