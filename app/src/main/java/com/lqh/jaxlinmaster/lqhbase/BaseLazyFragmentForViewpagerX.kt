package com.lqh.jaxlinmaster.lqhbase

/**
 * Created by Linqh on 2021/6/24.
@describe:
 *此方案是针对Androidx之后的Viewpager以及2的懒加载,就是使用BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT这种方式
 * 这个切换切换相邻tabitem,最开始的tabitem会执行onpause,而BEHAVIOR_SET_USER_VISIBLE_HINT不会
 */

abstract class BaseLazyFragmentForViewpagerX :LqhBaseFragment(){
    private var isFirstLoad = true // 是否第一次加载
    override fun onResume() {
        super.onResume()
        // 将数据加载逻辑放到onResume()方法中
        lazyInit(isFirstLoad)
        isFirstLoad = false
    }

    override fun onDestroy() {
        isFirstLoad = true
        super.onDestroy()
    }
    //懒加载
    protected abstract fun lazyInit(isFirstLoad: Boolean)
    //可以再onpause里面执行暂停操作
    override fun onPause() {
        super.onPause()
    }
}