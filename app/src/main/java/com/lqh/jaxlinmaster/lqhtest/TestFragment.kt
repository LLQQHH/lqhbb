package com.lqh.jaxlinmaster.lqhtest

import android.view.View
import com.lqh.jaxlinmaster.R
import com.lqh.jaxlinmaster.lqhbase.LqhBaseLazyFragmentForX
import kotlinx.android.synthetic.main.fragment_test.*

/**
 * Created by Linqh on 2021/6/24.
@describe:
 *
 */
//@CreateUidAnnotation(uid = "10100")
class TestFragment(var title:String):LqhBaseLazyFragmentForX(){
    override fun requestData(isFirstLoad: Boolean) {

    }

    override fun initView(layout: View) {
        tv_title.text = title
    }
    override fun getLayoutId(): Int= R.layout.fragment_test
}