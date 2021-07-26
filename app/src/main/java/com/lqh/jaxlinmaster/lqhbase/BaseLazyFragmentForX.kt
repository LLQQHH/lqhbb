package com.lqh.jaxlinmaster.lqhbase

import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtils

/**
 * Created by Linqh on 2021/6/24.
@describe:
 *此方案是针对Androidx的懒加载,就是使用BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT这种方式
 *
 * 若是Viewpager以及2,这个切换切换相邻tabitem,最开始的tabitem会执行onpause。而BEHAVIOR_SET_USER_VISIBLE_HINT不会
 * 若是hide，show模式,跟上面一样，但是会回调onHiddenChanged
 * 对于当前显示的fragment切入后台会调用onpause和onStop,重后台切回来会调用onResume，切换到别tab的的时候会调用onpause
 * 对于已经add过得非当前显示的fragment切换后台会调用onStop，重后台切换回来会调用onstart
 * 实践发现对于嵌套的fragment也适用上面的逻辑
 * 可以再onpause里面执行暂停操作,因为切换tab和切到后台走回走onpause,所以无法区分是哪个操作导致的,得外面辅助判断
 */

abstract class BaseLazyFragmentForX :LqhBaseFragment(){
    private var isFirstLoad = true // 是否第一次加载
    override fun onResume() {
        super.onResume()
        // 将数据加载逻辑放到onResume()方法中
        onFragmentLazyInit(isFirstLoad)
        isFirstLoad = false
    }

    override fun onDestroy() {
        isFirstLoad = true
        super.onDestroy()
    }
    //可以再onpause里面执行暂停操作
    override fun onPause() {
        super.onPause()
        onFragmentPause()
    }
    //懒加载
    protected abstract fun onFragmentLazyInit(isFirstLoad: Boolean)
    protected abstract  fun onFragmentPause()
}