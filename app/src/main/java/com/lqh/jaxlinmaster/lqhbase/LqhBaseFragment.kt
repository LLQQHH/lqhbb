package com.lqh.jaxlinmaster.lqhbase

import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lqh.jaxlinmaster.constants.Constants
import java.util.*
import kotlin.math.pow

/**
 * Created by Linqh on 2021/6/24.
@describe:
 *
 */
//@CreateUidAnnotation(uid = "10100")
abstract class LqhBaseFragment: Fragment() {
    protected var layout: View? = null
    private  var hasLayout:Boolean=false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (layout == null){
            layout = inflater.inflate(getLayoutId(), container, false)
            hasLayout=true
        }
        val parent = layout?.parent as ViewGroup?
        parent?.removeView(layout)
        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //挪到这边为了是kotlin控件好找,加hasLayout=false,是在Viewpager某些情况下，有时候执行一次onCreateView但是会多次执行onViewCreated
        if (hasLayout){
            hasLayout=false
            initView(layout!!)
            initData()
        }
    }

    abstract fun initView(layout: View)

    private fun initData() {
    }

    //初始化布局
    abstract fun getLayoutId(): Int
    open fun jumpToClass(activity: Class<*>?, bundle: Bundle?) {
        val intent = Intent(context, activity)
        if (bundle != null) {
            intent.putExtra(Constants.String.BUNDLE, bundle)
        }
        startActivity(intent)
    }

    open fun jumpToClassForResult(activity: Class<*>?, bundle: Bundle?, requestCode: Int) {
        val intent = Intent(context, activity)
        if (bundle != null) {
            intent.putExtra(Constants.String.BUNDLE, bundle)
        }
        startActivityForResult(intent, requestCode)
    }
    //为了防止混乱用activity来做
    open fun jumpToClassForResult(activity: Class<*>?, bundle: Bundle?,onActivityCallback: LqhBaseActivity.OnActivityCallback?) {
        var parentActivity = getActivity()
        if (parentActivity is LqhBaseActivity){
            parentActivity.jumpToClassForResult(activity,bundle,onActivityCallback)
        }
    }
}