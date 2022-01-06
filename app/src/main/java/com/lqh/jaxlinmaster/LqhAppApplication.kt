package com.lqh.jaxlinmaster

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.Utils

/**
 * Created by Linqh on 2021/5/25.
 *
 * @describe:
 */
//@CreateUidAnnotation(uid = "10100")
 class LqhAppApplication:Application(){
    override fun attachBaseContext(context: Context?) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }
    override fun onCreate() {
        super.onCreate()
        initData()
    }

    private fun initData() {
        Utils.init(this)
    }
}