package com.lqh.jaxlinmaster.lqhbase

import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import com.lqh.jaxlinmaster.constants.Constants
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.KeyboardUtil
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtil
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
        //getWindow().setBackgroundDrawableResource(R.drawable.shape_activity_bg);
        LogUtil.e("当前打开Activity:", javaClass.simpleName)
        beforeOnCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        beforeContentView()
        setContentView(getLayoutId())
        initStatusBar()
        bind = ButterKnife.bind(this)
        initView(savedInstanceState)
        initData(savedInstanceState)
    }

    open fun beforeContentView() {

    }

    open fun initStatusBar() {
        //StatusBarUtil.setStatusBarColor(this,ContextCompat.getColor(this,R.color.red_beautiful_color))
    }

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun initData(savedInstanceState: Bundle?)

    protected fun beforeOnCreate(savedInstanceState: Bundle?) {
    }

    abstract fun getLayoutId():Int
    override fun onDestroy() {
        LogUtil.e("当前关闭Activity:", javaClass.simpleName)
        bind?.unbind()
        super.onDestroy()
    }
    open fun jumpToClass(activity: Class<*>?, bundle: Bundle?) {
        val intent = Intent(this, activity)
        if (bundle != null) {
            intent.putExtra(Constants.Strings.BUNDLE, bundle)
        }
        startActivity(intent)
    }

    open fun jumpToClassForResult(activity: Class<*>?, bundle: Bundle?, requestCode: Int) {
        val intent = Intent(this, activity)
        if (bundle != null) {
            intent.putExtra(Constants.Strings.BUNDLE, bundle)
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
            intent.putExtra(Constants.Strings.BUNDLE, bundle)
        }
        startActivityForResult(intent, requestCode)
    }


    override fun startActivityForResult(intent: Intent?, requestCode: Int, options: Bundle?) {
        // 隐藏软键，避免内存泄漏
        KeyboardUtil.hideSoftInput(this)
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
        KeyboardUtil.hideSoftInput(this)
        super.finish()
    }



    override fun onStart() {
        super.onStart()
        LogUtil.e("生命周期",this.javaClass.name+":"+"onStart")
    }

    override fun onRestart() {
        super.onRestart()
        LogUtil.e("生命周期",this.javaClass.name+":"+"onRestart")
    }
    override fun onResume() {
        super.onResume()
        LogUtil.e("生命周期",this.javaClass.name+":"+"onResume")
    }
    override fun onPause() {
        super.onPause()
        LogUtil.e("生命周期",this.javaClass.name+":"+"onPause")
    }

    override fun onStop() {
        super.onStop()
        LogUtil.e("生命周期",this.javaClass.name+":"+"onStop")
    }
}