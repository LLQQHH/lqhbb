package com.lqh.jaxlinmaster.lqhtest

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.Window
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import com.lqh.jaxlinmaster.R
import com.lqh.jaxlinmaster.lqhbase.LqhBaseActivity
import kotlinx.android.synthetic.main.activity_lqh_test.*


class LqhTestActivity : LqhBaseActivity() {
    override fun initView(savedInstanceState: Bundle?) {
        //ViewCompat.setTransitionName(tv_test,"test")

    }

    override fun initData(savedInstanceState: Bundle?) {
        tv_test.setOnClickListener {
            ActivityCompat.finishAfterTransition(this)
        }
    }

    override fun getLayoutId(): Int=R.layout.activity_lqh_test
    override fun finish() {
        super.finish()
        android.R.anim.slide_in_left
        overridePendingTransition(R.anim.anim_activity_translate_y_no_anim,R.anim.anim_activity_translate_back_out)
    }
    override fun beforeContentView() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
    }


    override fun finishAfterTransition() {

        super.finishAfterTransition()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}