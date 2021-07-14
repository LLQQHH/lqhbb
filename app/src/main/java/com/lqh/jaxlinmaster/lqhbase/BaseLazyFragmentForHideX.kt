package com.lqh.jaxlinmaster.lqhbase

import android.os.Bundle

//这个只兼容使用show和hide方法加载fragment,模式为BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
//外面的Transaction执行操作的时候要发生变化！
abstract class BaseLazyFragmentForHideX : LqhBaseFragment() {
    private var isFirstLoad = true // 是否第一次加载
    override fun onResume() {
        super.onResume()
        // 将数据加载逻辑放到onResume()方法中
        if (!isHidden){//加这个判断是因为fragment嵌套fragment会导致所以子Fragment调用onResume,所以添加isHidden判断
            lazyInit(isFirstLoad)
            isFirstLoad = false
        }

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