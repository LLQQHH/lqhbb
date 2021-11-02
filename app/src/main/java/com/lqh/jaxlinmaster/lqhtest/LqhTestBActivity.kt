package com.lqh.jaxlinmaster.lqhtest

import android.os.Bundle
import android.view.Window
import androidx.core.app.ActivityCompat
import com.google.gson.internal.LinkedTreeMap
import com.lqh.jaxlinmaster.R
import com.lqh.jaxlinmaster.constants.Constants
import com.lqh.jaxlinmaster.lqhbase.LqhBaseActivity
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtil
import kotlinx.android.synthetic.main.activity_lqh_test_b.*
import org.apache.commons.text.StringEscapeUtils


class LqhTestBActivity : LqhBaseActivity() {
    override fun initView(savedInstanceState: Bundle?) {
        //ViewCompat.setTransitionName(tv_test,"test")

    }

    override fun initData(savedInstanceState: Bundle?) {
        val bundleExtra = intent.getBundleExtra(Constants.Strings.BUNDLE)
        if (bundleExtra!=null){
            val serializable = bundleExtra.getSerializable(Constants.Strings.DATA)
            if (serializable!=null){
                LogUtil.e("数据",serializable.toString())
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_lqh_test_b




}
