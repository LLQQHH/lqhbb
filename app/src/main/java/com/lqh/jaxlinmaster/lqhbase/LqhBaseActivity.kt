package com.lqh.jaxlinmaster.lqhbase

import android.os.Bundle
import android.os.PersistableBundle
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

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        beforeOnCreate(savedInstanceState)
        super.onCreate(savedInstanceState, persistentState)
        setContentView(getLayoutId())
        //setSwipeAnyWhere(true);
        bind = ButterKnife.bind(this)
        initOnCreate()
    }

     abstract fun initOnCreate()

    protected fun beforeOnCreate(savedInstanceState: Bundle?) {
    }

    abstract fun getLayoutId():Int
    override fun onDestroy() {
        bind?.unbind()
        super.onDestroy()
    }
}