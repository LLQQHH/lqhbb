package com.lqh.jaxlinmaster.lqhbase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * Created by Linqh on 2021/5/25.
 *
 * @describe:
 */
//@CreateUidAnnotation(uid = "10100")
abstract class LqhBaseActivity:AppCompatActivity(){

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
            intent.putExtra("bundle", bundle)
        }
        startActivity(intent)
    }

    open fun jumpToClassForResult(activity: Class<*>?, bundle: Bundle?, requestCode: Int) {
        val intent = Intent(this, activity)
        if (bundle != null) {
            intent.putExtra("bundle", bundle)
        }
        startActivityForResult(intent, requestCode)
    }
}