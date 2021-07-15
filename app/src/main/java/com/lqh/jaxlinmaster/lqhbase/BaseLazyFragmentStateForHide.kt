package com.lqh.jaxlinmaster.lqhbase

import android.os.Bundle
import android.util.Log

//这个只兼容使用show和hide方法加载fragment,模式为BEHAVIOR_SET_USER_VISIBLE_HINT
//第一次add然后show这个fragment并不会执行onHiddenChanged但是isHide是false
//通过实验知道onHiddenChanged和其他声明周期不会同时执行,而且执行onHiddenChanged肯定当前fragment已经实例化过了
abstract class BaseLazyFragmentStateForHide : LqhBaseFragment() {
    //布局是否初始化完成
    private var isLayoutInitialized = false

    //懒加载完成
    private var isLazyLoadFinished = false
    //不可见时释放部分资源
    private var isInVisibleRelease = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isLayoutInitialized = true
        judgeLazyLoad()
    }

    override fun onResume() {
        super.onResume()
        //页面从其他Activity返回时，重新加载被释放的资源
        if (isLazyLoadFinished&&isInVisibleRelease) {
            resume()
            isInVisibleRelease = false
        }
    }

    override  fun onPause() {
        super.onPause()
        //当从Fragment切换到其他Activity释放部分资源
        if (isLazyLoadFinished) {
            pause()
            isInVisibleRelease = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        //重置所有数据
        this.layout = null
        isLayoutInitialized = false
        isLazyLoadFinished = false
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        judgeLazyLoad()
    }




    //懒加载
    private fun judgeLazyLoad() {
        if (!isHidden && isLayoutInitialized) {
            if (!isLazyLoadFinished) {
                lazyLoad()
                isLazyLoadFinished = true
            } else {
                visibleReLoad()
            }
        } else {
            //不是第一次了
            if (isLazyLoadFinished && isHidden) {
                //页面从可见切换到不可见时触发，可以释放部分资源，配合reload方法再次进入页面时加载
                inVisibleRelease()
            }
        }

    }

    /**
     * 懒加载<br></br>
     * 只会在初始化后第一次可见时调用一次。
     */
    protected abstract fun lazyLoad()

    /**
     * 刷新数据加载<br></br>
     * 配合[.lazyLoad]，在页面非第一次可见时刷新数据
     * 左右切换Fragment时触发
     */
    protected abstract fun visibleReLoad()

    /**
     * 当页面从可见变为不可见时，释放部分数据和资源。<br></br>
     * 比如页面播放器的释放或者一些特别占资源的数据的释放
     * 左右切换Fragment时触发
     */
    protected abstract fun inVisibleRelease()

    /**
     * 当从其他页面返回，重新可见
     */
    protected abstract fun resume()

    /**
     * 进入其他页面触发
     */
    protected abstract fun pause()

}