package com.lqh.jaxlinmaster.lqhwidget.lqhstateview

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.lqh.jaxlinmaster.R

/**
 * Created by Linqh on 2021/8/6.
 *
 * @describe:多状态布局
 */
//@CreateUidAnnotation(uid = "10100")
open class LqhStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    //private val views = SparseArray<View>(3)//保存View视图,为了可以加载多个布局,因为SparseArray不能保存key为负数的，会被转换为正数，这样可能会就冲突
    private val views :MutableMap<Int,View> = mutableMapOf()//保存View视图,为了可以加载多个布局
    var onInflateListener: OnInflateListener? = null
    var onRetryClickListener: OnRetryClickListener? = null
    @LayoutRes
    var emptyResource = 0

    @LayoutRes
    var retryResource = 0

    @LayoutRes
    var loadingResource = 0


    private fun initAttr(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.LqhStateView)
        emptyResource = ta.getResourceId(R.styleable.LqhStateView_Lqh_emptyResource, 0)
        retryResource = ta.getResourceId(R.styleable.LqhStateView_Lqh_retryResource, 0)
        loadingResource = ta.getResourceId(R.styleable.LqhStateView_Lqh_loadingResource, 0)
        ta.recycle()
        if (emptyResource == 0) {
            emptyResource = R.layout.lqh_base_empty
        }
        if (retryResource == 0) {
            retryResource = R.layout.lqh_base_retry
        }
        if (loadingResource == 0) {
            loadingResource = R.layout.lqh_base_loading
        }
    }

    init {
        initAttr(context, attrs)
    }

    companion object {
        internal const val TAG = "LqhStateView"
        //这个是内容的key,不能占用
        const val ContentKey = -2
        @JvmStatic
        fun inject(view: View): LqhStateView {
            val parent = view.parent
            if (parent is ViewGroup) {
                val oldLayoutParams = view.layoutParams
                val oldChildIndex = parent.indexOfChild(view)
                parent.removeView(view)
                val stateView = LqhStateView(parent.context)
                stateView.layoutParams = oldLayoutParams
                stateView.addView(view)
                //添加内容View
                stateView.views.put(ContentKey, view)
                parent.addView(stateView, oldChildIndex)
                return stateView
            }
            throw ClassCastException("view.getParent() must be ViewGroup")
        }

        @JvmStatic
        fun inject(activity: Activity): LqhStateView {
            val parent = activity.findViewById<ViewGroup?>(android.R.id.content)
            if (parent is ViewGroup) {
                var oldChildIndex: Int = 0
                val oldContent = parent.getChildAt(oldChildIndex)//我们setContentView时候的View
                return inject(oldContent)
            }
            throw ClassCastException("view.getParent() must be ViewGroup")
        }

        //这个一定要是已经存在的id
        @JvmStatic
        fun inject(viewId: Int, activity: Activity): LqhStateView {
            val oldView = activity.findViewById<View?>(viewId)
            if (oldView != null) {
                return inject(oldView)
            }
            throw ClassCastException("viewId must be available")
        }
        @JvmStatic
        fun inject(viewId: Int, fragment: Fragment): LqhStateView {
            val oldView = fragment.view?.findViewById<View?>(viewId)
            if (oldView != null) {
                return inject(oldView)
            }
            throw ClassCastException("viewId must be available")
        }

    }

    fun showEmpty(contentVisibility: Int) = showView(emptyResource, View.VISIBLE)
    fun showEmpty() = showEmpty(View.VISIBLE)

    fun showRetry(contentVisibility: Int) = showView(retryResource, View.VISIBLE)
    fun showRetry() = showRetry(View.VISIBLE)

    fun showLoading(contentVisibility: Int) = showView(loadingResource, View.VISIBLE)
    fun showLoading() = showLoading(View.VISIBLE)

    fun showContent() = showView(ContentKey, View.VISIBLE)

    /**
     * 添加View,这些初始化应该在show之前防止,出现其他异常
     * @param viewType Int
     * @param view View
     */
    fun setView(viewType: Int, view: View){
        views[viewType] = view
    }

    private fun showView(@LayoutRes layoutResource: Int, contentVisibility: Int): View {
        var view = views[layoutResource]
        if (view == null) {
            view = inflateChildView(layoutResource)
            views[layoutResource] = view!!
        }
        setViewVisibility(view, View.VISIBLE)
        hideViews(view, contentVisibility)
        return view
    }

    private fun hideViews(showView: View, contentVisibility: Int) {
        views.forEach {
            if (it.value != showView) {
                if (it.key == ContentKey) {
                    setViewVisibility(it.value, contentVisibility)
                } else {
                    setViewVisibility(it.value, View.GONE)
                }
            }
        }
    }

    private fun setViewVisibility(view: View?, visibility: Int) {
        if (view != null && view.visibility != visibility) {
            view.visibility = visibility
        }
    }


    private fun inflateChildView(layoutResource: Int): View? {
        if (layoutResource != 0) {
            var view = LayoutInflater.from(context).inflate(layoutResource, this, true)
            //如果是重试的按钮
            if (layoutResource==retryResource){
                view.setOnClickListener {
                   onRetryClickListener?.onRetryClick(view)
                }
            }
                //第一次初始化的时候会调用
                onInflateListener?.onInflate(layoutResource,view)
            return view
        } else {
            throw IllegalArgumentException("StateView must have a valid layoutResource")
        }
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 1) {
            throw IllegalArgumentException("LqhStateView must have only have one child in xml")
        }
        //这边初始化,只有一个的时候当做content
        if (childCount == 1) {
            views.put(ContentKey, getChildAt(0))
        }
    }


    /**
     * Listener used to receive a notification after the RetryView is clicked.
     *
     * @see onRetryClickListener
     */
    interface OnRetryClickListener {
        fun onRetryClick(view: View)
    }

    /**
     * Listener used to receive a notification after a StateView has successfully
     * inflated its layout resource.
     *
     * @see onInflateListener
     */
    interface OnInflateListener {
        /**
         * @param layoutResource Equivalent viewType, the [view] of key
         * @param view The inflated View.
         */
        fun onInflate(@LayoutRes layoutResource: Int, view: View)
    }

}