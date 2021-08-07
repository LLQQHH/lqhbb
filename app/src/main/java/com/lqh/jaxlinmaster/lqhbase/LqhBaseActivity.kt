package com.lqh.jaxlinmaster.lqhbase

import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import com.lqh.jaxlinmaster.constants.Constants
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.KeyboardUtils
import java.util.*
import kotlin.math.pow

/**
 * Created by Linqh on 2021/5/25.
 *
 * @describe:
 */
//@CreateUidAnnotation(uid = "10100")
abstract class LqhBaseActivity:AppCompatActivity(){
    /** Activity 回调结果集合，一般用于控件想获取结果请求的回调结果,而activity不需要*/
    private var mActivityCallbacks: SparseArray<OnActivityCallback>? = null
    private  var bind: Unbinder?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        beforeOnCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        bind = ButterKnife.bind(this)
        initView(savedInstanceState)
        initData(savedInstanceState)
    }

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun initData(savedInstanceState: Bundle?)

    protected fun beforeOnCreate(savedInstanceState: Bundle?) {
    }

    abstract fun getLayoutId():Int
    override fun onDestroy() {
        bind?.unbind()
        super.onDestroy()
    }
    open fun jumpToClass(activity: Class<*>?, bundle: Bundle?) {
        val intent = Intent(this, activity)
        if (bundle != null) {
            intent.putExtra(Constants.String.BUNDLE, bundle)
        }
        startActivity(intent)
    }

    open fun jumpToClassForResult(activity: Class<*>?, bundle: Bundle?, requestCode: Int) {
        val intent = Intent(this, activity)
        if (bundle != null) {
            intent.putExtra(Constants.String.BUNDLE, bundle)
        }
        startActivityForResult(intent, requestCode)
    }

    open fun jumpToClassForResult(activity: Class<*>?, bundle: Bundle?,onActivityCallback:OnActivityCallback?) {
        // 请求码必须在 2 的 16 次方以内
        val requestCode = Random().nextInt(2.0.pow(16.0).toInt())
        if (onActivityCallback!=null){
            if (mActivityCallbacks==null){
                mActivityCallbacks= SparseArray()
            }
            mActivityCallbacks?.put(requestCode, onActivityCallback)
        }
        val intent = Intent(this, activity)
        if (bundle != null) {
            intent.putExtra(Constants.String.BUNDLE, bundle)
        }
        startActivityForResult(intent, requestCode)
    }


    override fun startActivityForResult(intent: Intent?, requestCode: Int, options: Bundle?) {
        // 隐藏软键，避免内存泄漏
        KeyboardUtils.hideSoftInput(this)
        // 查看源码得知 startActivity 最终也会调用 startActivityForResult
        super.startActivityForResult(intent, requestCode, options)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var callback: OnActivityCallback?=null
        if (mActivityCallbacks != null && mActivityCallbacks!![requestCode].also {
                callback = it } != null) {
            callback?.onActivityResult(resultCode, data)
            mActivityCallbacks!!.remove(requestCode)
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    interface OnActivityCallback {
        /**
         * 结果回调
         *
         * @param resultCode        结果码
         * @param data              数据
         */
        fun onActivityResult(resultCode: Int, data: Intent?)
    }

    override fun finish() {
        //隐藏软键，避免内存泄漏
        KeyboardUtils.hideSoftInput(this)
        super.finish()
    }
}