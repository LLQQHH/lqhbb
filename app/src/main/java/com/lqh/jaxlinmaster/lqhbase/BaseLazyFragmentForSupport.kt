package com.lqh.jaxlinmaster.lqhbase

import android.os.Bundle
import android.os.Handler
import android.os.Looper

/**
 * @author wangshijia
 * @date 2018/2/2
 * 对于可见状态的生命周期调用顺序，父 Fragment总是优先于子 Fragment，如onresume总是父Fragment先调用
 * 而对于不可见事件，内部的 Fragment 生命周期总是先于外层 Fragment，如onPause总是子Fragment先调用
 */
abstract class BaseLazyFragmentForSupport : LqhBaseFragment() {
    private var mIsFirstVisible = true
    private var isViewCreated = false

    //表示当前的状态
    private var isSupportVisible = false
    private val TAG = this.javaClass.name
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        // 对于默认 tab 和 间隔 checked tab 需要等到 isViewCreated = true 后才可以通过此通知用户可见
        // 这种情况下第一次可见不是在这里通知 因为 isViewCreated = false 成立,等从别的界面回到这里后会使用 onFragmentResume 通知可见
        // 对于非默认 tab mIsFirstVisible = true 会一直保持到选择则这个 tab 的时候，因为在 onActivityCreated 会返回 false
        if (isViewCreated) {
            if (isVisibleToUser && !isSupportVisible && isAdded) {
                //切换可见，但是之前的状态是不可见的
                dispatchUserVisibleHint(true)
            } else if (!isVisibleToUser && isSupportVisible) {
                //切换不可见,但是之前的状态是可见的
                dispatchUserVisibleHint(false)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewCreated = true
        // !isHidden() 默认为 true  在调用 hide show 的时候可以使用
        if (!isHidden && userVisibleHint) {
            // 这里的限制只能限制 A - > B 两层嵌套
            dispatchUserVisibleHint(true)
        }
    }

    /**
     * 该方法与 setUserVisibleHint 对应，调用时机是 show，hide 控制 Fragment 隐藏的时候，
     * 注意的是，只有当 Fragment 被创建后再次隐藏显示的时候才会调用，第一次 show 的时候是不会回调的。与其他生命周期不会同时被调用
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            dispatchUserVisibleHint(false)
        } else {
            dispatchUserVisibleHint(true)
        }
    }

    /**
     * 需要再 onResume 中通知用户可见状态的情况是在当前页面再次可见的状态 !mIsFirstVisible 可以保证这一点，
     * 而当前页面 Activity 可见时所有缓存的 Fragment 都会回调 onResume
     * 所以我们需要区分那个Fragment 位于可见状态
     * (!isHidden() && !currentVisibleState && getUserVisibleHint()）可条件可以判定哪个 Fragment 位于可见状态
     */
    override fun onResume() {
        super.onResume()
        //非第一次不可见的时候
        if (!mIsFirstVisible) {
            if (!isHidden && !isSupportVisible && userVisibleHint) {
                dispatchUserVisibleHint(true)
            }
        }
    }

    /**
     * 当用户进入其他界面的时候所有的缓存的 Fragment 都会 onPause
     * 但是我们想要知道只是当前可见的的 Fragment 不可见状态，
     * currentVisibleState && getUserVisibleHint() 能够限定是当前可见的 Fragment
     */
    override fun onPause() {
        super.onPause()
        // 当前 Fragment 包含子 Fragment 的时候 dispatchUserVisibleHint 内部本身就会通知子 Fragment 不可见
        // 子 fragment 走到这里的时候自身又会调用一遍 ？
        if (isSupportVisible && userVisibleHint) {
            dispatchUserVisibleHint(false)
        }
    }

    /**
     * 统一处理 显示隐藏
     *
     * @param visible
     */
    private fun dispatchUserVisibleHint(visible: Boolean) {
        //当前 Fragment 是 child 时候 作为缓存 Fragment 的子 fragment getUserVisibleHint = true
        //但当父 fragment 不可见所以 currentVisibleState = false 直接 return 掉
        // 这里限制则可以限制多层嵌套的时候子 Fragment 的分发
        //典型情况是双层Viewpager嵌套,第二个人tab的Viewpager，第一个fragment走getUserVisibleHint位true,但是实际上这个fragment是不可见的
        if (visible && isParentInvisible) {
            return
        }
        //此处是对子 Fragment 不可见的限制，因为 子 Fragment 先于父 Fragment回调本方法 currentVisibleState 置位 false
        // 当父 dispatchChildVisibleState 的时候第二次回调本方法 visible = false 所以此处 visible 将直接返回
        if (isSupportVisible == visible) {
            return
        }
        isSupportVisible = visible
        if (visible) {
            if (!isAdded) {
                return
            }
            if (mIsFirstVisible) {
                onFragmentLazyInit(true)
                mIsFirstVisible = false
            } else {
                onFragmentLazyInit(false)
            }
            //分发给子Fragment，告知可见性，因为双层嵌套的时候，父切换tab不会导致子Fragment生命周期的变化
            enqueueDispatchVisible()
        } else {
            //分发给子Fragment，告知可见性，因为双层嵌套的时候，父切换tab不会导致子Fragment生命周期的变化
            dispatchChildVisibleState(false)
            onFragmentPause()
        }
    }

    /**
     * 由于下 onFirstVisible 中添加ViewPager Adapter 的时候由于异步提交导致 先派发了 子fragment 的 onFirstVisible
     * 造成空指针 所以将可见事件派发主线成
     */
    private fun enqueueDispatchVisible() {
        mHandler.post { dispatchChildVisibleState(true) }
    }


    private val mHandler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

    /**
     * 用于分发可见时间的时候父获取 fragment 是否隐藏
     *
     * @return true fragment 不可见， false 父 fragment 可见
     */
    private val isParentInvisible: Boolean
        private get() {
            val parentFragment = parentFragment
            return if (parentFragment is BaseLazyFragmentForSupport) {
                !parentFragment.isSupportVisible
            } else {
                false
            }
        }

    /**
     * 当前 Fragment 是 child 时候 作为缓存 Fragment 的子 fragment 的唯一或者嵌套 VP 的第一 fragment 时 getUserVisibleHint = true
     * 但是由于父 Fragment 还进入可见状态所以自身也是不可见的， 这个方法可以存在是因为庆幸的是 父 fragment 的生命周期回调总是先于子 Fragment
     * 所以在父 fragment 设置完成当前不可见状态后，需要通知子 Fragment 我不可见，你也不可见，
     *
     *
     * 因为 dispatchUserVisibleHint 中判断了 isParentInvisible 所以当 子 fragment 走到了 onActivityCreated 的时候直接 return 掉了
     *
     *
     * 当真正的外部 Fragment 可见的时候，走 setVisibleHint (VP 中)或者 onActivityCreated (hide show) 的时候
     * 从对应的生命周期入口调用 dispatchChildVisibleState 通知子 Fragment 可见状态
     *
     *
     * //bug fix Fragment has not been attached yet
     *
     * @param visible
     */
    private fun dispatchChildVisibleState(visible: Boolean) {
        if (!isAdded) {
            return
        }
        val childFragmentManager = childFragmentManager
        val fragments = childFragmentManager.fragments
        if (!fragments.isEmpty()) {
            for (child in fragments) {
                //只有当前子Fragment可见才去分发
                if (child is BaseLazyFragmentForSupport && child.isAdded() && !child.isHidden() && child.getUserVisibleHint()) {
                    child.dispatchUserVisibleHint(visible)
                }
            }
        }
    }

    /**
     * 是否已经初始化 View 完毕
     * 这里是指如果在 onFirstVisible 中去初始化 View 的时候
     * 而 initView(View view) 初始化View 时机是在 inflate 布局后
     *
     * @return 是否已经初始化 View 完毕
     */
    fun hasFirstVisible(): Boolean {
        return !mIsFirstVisible
    }




   abstract fun onFragmentPause()
    /**
     * 添加是否是第一次可见的标识
     *
     * @param IsFirstVisible true 是第一次可见 == onFirstVisible  false 去除第一次回调
     */
    abstract fun onFragmentLazyInit(IsFirstVisible: Boolean)
    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        isViewCreated = false
        mIsFirstVisible = true
    }
}