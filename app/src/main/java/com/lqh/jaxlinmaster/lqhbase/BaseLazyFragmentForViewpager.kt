package com.lqh.jaxlinmaster.lqhbase

import android.os.Bundle
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtils

//这个只兼容Viewpager,模式为BEHAVIOR_SET_USER_VISIBLE_HINT
//因为setUserVisibleHint有可能在其他生命周期前执行,所以要先判断有没有初始化
abstract class BaseLazyFragmentForViewpager : LqhBaseFragment() {
    protected var isCanVisible = false
    private var isPrepared = false
    private var isFirst = true

    override fun onResume() {
        super.onResume()
        isPrepared = true
        judgeLazyLoad()
    }

    override fun onPause() {
        super.onPause()
        invisibleInit(false)
    }
    override fun onDestroy() {
        super.onDestroy()
        isPrepared = false
        isCanVisible=false
        isFirst = true //有待商榷
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isCanVisible = isVisibleToUser
        judgeLazyLoad()
        //准备好,但是不可见的时候
        if (isPrepared&&!isCanVisible){
            invisibleInit(true)
        }
    }

    //懒加载
    private fun judgeLazyLoad() {
        if (!isPrepared || !isCanVisible) {
            return
        }
        lazyInit(isFirst)
        isFirst = false
    }

    protected abstract fun lazyInit(isFirstLoad: Boolean)

    protected open fun invisibleInit(isSetUserVisibleHint: Boolean){
        LogUtils.e("当前", "invisibleInit:"+isSetUserVisibleHint)
    }
}