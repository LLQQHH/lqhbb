package com.lqh.jaxlinmaster.lqhbase

import android.os.Bundle

//这个只兼容Viewpager
abstract class LqhBaseLazyFragmentForSupport : LqhBaseFragment() {
    protected var isCanVisible = false
    private var isPrepared = false
    private var isFirst = true
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isPrepared = true
        judgeLazyLoad()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isCanVisible = isVisibleToUser
        judgeLazyLoad()
    }

    //懒加载
    private fun judgeLazyLoad() {
        if (!isPrepared || !isCanVisible) {
            return
        }
        requestData(isFirst)
        isFirst = false
    }

    protected abstract fun requestData(isFirstLoad: Boolean)
    override fun onDestroyView() {
        isPrepared = false
        isFirst = true //有待商榷
        super.onDestroyView()
    }
}