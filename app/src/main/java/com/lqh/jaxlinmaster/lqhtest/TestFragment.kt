package com.lqh.jaxlinmaster.lqhtest

import android.content.Context
import android.os.Bundle
import android.view.View
import com.lqh.jaxlinmaster.R
import com.lqh.jaxlinmaster.lqhbase.BaseLazyFragmentForX
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtils
import kotlinx.android.synthetic.main.fragment_test.*

/**
 * Created by Linqh on 2021/6/24.
@describe:
 *
 */
//@CreateUidAnnotation(uid = "10100")
class TestFragment() : BaseLazyFragmentForX() {

    private var title: String?=null
    companion object{
        fun newInstance(title: String): TestFragment {
            var testFragment = TestFragment()
            val bundle = Bundle()
            bundle.putString("title", title)
            testFragment.arguments = bundle
            return testFragment;
        }
    }


    override fun lazyInit(isFirstLoad: Boolean) {
        LogUtils.e("当前$title", "isFirstLoad"+isFirstLoad);
    }

    override fun initView(layout: View) {
        title=arguments?.getString("title")
        tv_title.text =title
    }

    override fun getLayoutId(): Int = R.layout.fragment_test
    override fun onAttach(context: Context) {
        super.onAttach(context)
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
        LogUtils.e("当前$title", "onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.e("当前$title", "onPause")
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
}