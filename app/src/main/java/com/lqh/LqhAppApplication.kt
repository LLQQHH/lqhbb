package com.lqh

import android.app.Application
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.Utils

/**
 * Created by Linqh on 2021/5/25.
 *
 * @describe:
 */
//@CreateUidAnnotation(uid = "10100")
 class LqhAppApplication:Application(){
    override fun onCreate() {
        super.onCreate()
        initData()
    }

    private fun initData() {
        Utils.init(this)
    }
}