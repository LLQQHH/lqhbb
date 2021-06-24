package com.lqh.jaxlinmaster.lqhbase

/**
 * Created by Linqh on 2021/6/24.
@describe:
 *
 */
//@CreateUidAnnotation(uid = "10100")
abstract class LqhBaseLazyFragmentForX :LqhBaseFragment(){
    private var isFirstLoad = true // 是否第一次加载
    override fun onResume() {
        super.onResume()
        // 将数据加载逻辑放到onResume()方法中
        requestData(isFirstLoad)
        isFirstLoad = false
    }

    override fun onDestroy() {
        isFirstLoad = true
        super.onDestroy()
    }
    //懒加载
    protected abstract fun requestData(isFirstLoad: Boolean)
}