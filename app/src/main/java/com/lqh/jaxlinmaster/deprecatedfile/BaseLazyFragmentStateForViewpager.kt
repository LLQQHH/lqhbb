package com.lqh.jaxlinmaster.deprecatedfile
import android.os.Bundle
import android.util.Log
import androidx.annotation.Nullable
import com.lqh.jaxlinmaster.lqhbase.LqhBaseFragment

//这个只兼容Viewpager,模式为BEHAVIOR_SET_USER_VISIBLE_HINT，有多种状态
@Deprecated("无法兼容多层嵌套")
abstract class BaseLazyFragmentStateForViewpager : LqhBaseFragment() {
    protected var TAG = BaseLazyFragmentStateForViewpager::class.java.simpleName + this.toString()

    //布局是否初始化完成
    private var isLayoutInitialized = false

    //懒加载完成
    private var isLazyLoadFinished = false

    //记录页面可见性
    private var isVisibleToUser = false

    //不可见时释放部分资源
    private var isInVisibleRelease = false




    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, javaClass.simpleName + "  onDestroyView")

        //页面释放后，重置布局初始化状态变量
        isLayoutInitialized = false
        //这边看情况置空,因为为了不重复加载View所以这边不置空
        //this.layout = null
    }

    override  fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, javaClass.simpleName + "  onActivityCreated")
        //此方法是在第一次初始化时onCreateView之后触发的
        //onCreateView和onActivityCreated中分别应该初始化哪些数据可以参考：
        //https://stackoverflow.com/questions/8041206/android-fragment-oncreateview-vs-onactivitycreated
        isLayoutInitialized = true
        //第一次初始化后需要处理一次可见性事件
        //因为第一次初始化时setUserVisibleHint方法的触发要先于onCreateView
        dispatchVisibleEvent()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, javaClass.simpleName + "  onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, javaClass.simpleName + "  onResume")

        //页面从其他Activity返回时，重新加载被释放的资源
        if (isLazyLoadFinished && isLayoutInitialized && isInVisibleRelease && isVisibleToUser) {
            resume()
            isInVisibleRelease = false
        }
    }

    override  fun onPause() {
        super.onPause()
        Log.d(TAG, javaClass.simpleName + "  onPause")

        //当从Fragment切换到其他Activity释放部分资源
        if (isLazyLoadFinished && isVisibleToUser) {
            //页面从可见切换到不可见时触发，可以释放部分资源，配合reload方法再次进入页面时加载
//            inVisibleRelease();
            pause()
            isInVisibleRelease = true
        }
    }

    override  fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, javaClass.simpleName + "  onDestroy")

        //重置所有数据
        this.layout = null
        isLayoutInitialized = false
        isLazyLoadFinished = false
        isVisibleToUser = false
        isInVisibleRelease = false
    }

    override    fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.d(
            TAG,
            javaClass.simpleName + "  setUserVisibleHint isVisibleToUser = " + isVisibleToUser
        )
        dispatchVisibleEvent()
    }

    /**
     * 处理可见性事件
     */
    private fun dispatchVisibleEvent() {
        Log.d(
            TAG,
            javaClass.simpleName + "  dispatchVisibleEvent isVisibleToUser = " + getUserVisibleHint()
                    + " --- isLayoutInitialized = " + isLayoutInitialized + " --- isLazyLoadFinished = " + isLazyLoadFinished
        )
        if (getUserVisibleHint() && isLayoutInitialized) {
            //可见
            if (!isLazyLoadFinished) {
                //第一次可见，懒加载
                lazyLoad()
                isLazyLoadFinished = true
            } else {
                //非第一次可见，刷新数据
                visibleReLoad()
            }
        } else {
            if (isLazyLoadFinished && isVisibleToUser) {
                //页面从可见切换到不可见时触发，可以释放部分资源，配合reload方法再次进入页面时加载
                inVisibleRelease()
            }
        }

        //处理完可见性事件之后修改isVisibleToUser状态
        isVisibleToUser = getUserVisibleHint()
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