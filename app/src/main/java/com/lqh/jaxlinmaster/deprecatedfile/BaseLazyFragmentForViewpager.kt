package com.lqh.jaxlinmaster.deprecatedfile

import android.os.Bundle
import com.lqh.jaxlinmaster.lqhbase.LqhBaseFragment
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtils

//这个只兼容Viewpager,模式为BEHAVIOR_SET_USER_VISIBLE_HINT
//因为setUserVisibleHint有可能在其他生命周期前执行,而且fragment第一次初始化也会调用,所以要先判断有没有初始化
@Deprecated("无法兼容多层嵌套")
abstract class BaseLazyFragmentForViewpager : LqhBaseFragment(), IPareVisibilityObserver {
    protected var isCanVisible = false
    private var isLayoutInitialized = false
    private var isFirst = true


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isLayoutInitialized = true
    }

    override fun onResume() {
        super.onResume()
        judgeLazyLoad()
    }

    override fun onPause() {
        super.onPause()
        invisibleInit(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isLayoutInitialized=false
    }
    override fun onDestroy() {
        super.onDestroy()
        isLayoutInitialized = false
        isCanVisible=false
        isFirst = true //有待商榷
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isCanVisible = isVisibleToUser
        judgeLazyLoad()
        //准备好,但是不可见的时候
        if (isLayoutInitialized&&!isCanVisible){
            invisibleInit(true)
        }
        dispatchVisibleToChild(isCanVisible)
    }

    private fun dispatchVisibleToChild(isCanVisible: Boolean) {
        if (isDetached || !isAdded) {
            return
        }
        val fragments = childFragmentManager.fragments
        if (fragments.isEmpty()) {
            return
        }
        fragments.forEach {
            if (it is IPareVisibilityObserver){
                it.onParentFragmentHiddenChanged(isCanVisible)
            }
        }
    }

    //懒加载
    private fun judgeLazyLoad() {
        if (isLayoutInitialized&&isCanVisible){
            lazyInit(isFirst)
            isFirst = false
        }

    }

    protected abstract fun lazyInit(isFirstLoad: Boolean)

    protected open fun invisibleInit(isSetUserVisibleHint: Boolean){
        LogUtils.e("当前", "invisibleInit:"+isSetUserVisibleHint)
    }
    //记得重写这个然后调用super
    override fun onParentFragmentHiddenChanged(expected: Boolean) {
        if (isDetached || !isAdded) {
            return
        }
        val superVisible = super.isVisible()
        val hintVisible = userVisibleHint
        val visible = superVisible && hintVisible
        if (visible!=expected){
            dispatchVisibleToChild(expected)
        }
    }
}