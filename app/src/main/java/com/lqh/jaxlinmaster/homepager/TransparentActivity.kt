package com.lqh.jaxlinmaster.homepager

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.lqh.jaxlinmaster.R
import com.lqh.jaxlinmaster.dialog.CenterDialog
import com.lqh.jaxlinmaster.lqhbase.LqhBaseActivity
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtil
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.StatusBarUtil
import com.lqh.jaxlinmaster.lqhtest.LqhTestAActivity
import com.lqh.jaxlinmaster.lqhtest.LqhTestBActivity
import kotlinx.android.synthetic.main.activity_state.*


class TransparentActivity : LqhBaseActivity() {

    override fun initView(savedInstanceState: Bundle?){

    }

    override fun initData(savedInstanceState: Bundle?) {
        Handler().post {
            var dialog= CenterDialog(this,R.layout.dialog_test,R.style.theme_Dialog_From_Bottom)
            dialog.show()
        }
    }

    override fun getLayoutId(): Int= com.lqh.jaxlinmaster.R.layout.activity_state
    override fun finish() {
        super.finish()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun initStatusBar() {
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}