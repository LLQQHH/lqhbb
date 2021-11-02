package com.lqh.jaxlinmaster

import android.content.Context
import android.os.Bundle
import android.view.View
import com.lqh.jaxlinmaster.lqhbase.BaseLazyFragmentForX
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtil
import kotlinx.android.synthetic.main.fragment_test.*

/**
 * Created by Linqh on 2021/6/24.
@describe:
 *
 */
//@CreateUidAnnotation(uid = "10100")
class FragmentD() : BaseLazyFragmentForX() {

    private var title: String?=null
    companion object{
        fun newInstance(title: String): FragmentD {
            var testFragment = FragmentD()
            val bundle = Bundle()
            bundle.putString("title", title)
            testFragment.arguments = bundle
            return testFragment
        }
    }


    override fun initView(layout: View) {

        tv_title.text =title
    }

    override fun getLayoutId(): Int = R.layout.fragment_d




    override fun onAttach(context: Context) {
        super.onAttach(context)
        title=arguments?.getString("title")
        LogUtil.e("主当前$title", "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LogUtil.e("主当前$title", "onCreate")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtil.e("主当前$title", "onViewCreated")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogUtil.e("主当前$title", "onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        LogUtil.e("主当前$title", "onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.e("主当前$title", "在onResume中判断isHidden"+isHidden)
        LogUtil.e("主当前$title", "onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtil.e("主当前$title", "onPause")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.e("主当前$title", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtil.e("主当前$title", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.e("主当前$title", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        LogUtil.e("主当前$title", "onDetach")
    }
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        title=arguments?.getString("title")
        super.setUserVisibleHint(isVisibleToUser)
        LogUtil.e("主当前$title", "isVisibleToUser:$isVisibleToUser")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        LogUtil.e("主当前$title", "onHiddenChanged:$hidden")
        LogUtil.e("主当前$title", "在onHiddenChanged中判断isHidden"+isHidden)
    }
    override fun onFragmentPause() {
        LogUtil.e("主当前$title", "不可见onFragmentPause")
    }

    override fun onFragmentLazyInit(IsFirstVisible: Boolean) {
        LogUtil.e("主当前$title", "可见isFirstLoad:"+IsFirstVisible)
    }
}