package com.lqh.jaxlinmaster.lqhbase

import android.os.Bundle

//这个只兼容使用show和hide方法加载fragment,模式为BEHAVIOR_SET_USER_VISIBLE_HINT
//第一次add然后show这个fragment并不会执行onHiddenChanged但是isHide是false
//通过实验知道onHiddenChanged和其他声明周期不会同时执行,而且执行onHiddenChanged肯定当前fragment已经实例化过了
//onHiddenChange切换的时候才会调用
abstract class BaseLazyFragmentForHide : LqhBaseFragment() {
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

    override fun onDestroy() {
        isLayoutInitialized = false
        isFirst = true //有待商榷
        super.onDestroy()
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        judgeLazyLoad()
        if (hidden){
          invisibleInit(hidden)
        }
    }



    override fun onPause() {
        super.onPause()
        invisibleInit(false)
    }
    //当前fragment不可见,
    // isOnHiddenChanged:不可见的原因是isOnHiddenChanged导致的还是onpause导致的
    protected  fun invisibleInit(isOnHiddenChanged: Boolean){

    }

    //懒加载
    private fun judgeLazyLoad() {
        if (!isHidden&&isLayoutInitialized){
            lazyInit(isFirst)
            isFirst = false
        }

    }
    //懒加载,isFirstLoad表示第一次界面显示
    protected abstract fun lazyInit(isFirstLoad: Boolean)

}