package com.lqh.jaxlinmaster.lqhtest

import android.os.Bundle
import android.view.Window
import androidx.core.app.ActivityCompat
import com.google.gson.Gson
import com.lqh.jaxlinmaster.R
import com.lqh.jaxlinmaster.constants.Constants
import com.lqh.jaxlinmaster.lqhbase.LqhBaseActivity
import kotlinx.android.synthetic.main.activity_lqh_test_a.*
import java.io.Serializable


class LqhTestAActivity : LqhBaseActivity() {
    override fun initView(savedInstanceState: Bundle?) {
        //ViewCompat.setTransitionName(tv_test,"test")

    }

    override fun initData(savedInstanceState: Bundle?) {
        tv_test.setOnClickListener {
            ActivityCompat.finishAfterTransition(this)
        }
        tv_test1.setOnClickListener {
            //        String s = testMap.toString();
            //val dataJson = "{\"Rate\" : 1.0, \"extend\" : {\"number\" : 30, \"amount\" : 120.3}}"
            val dataJson = "{\"Rate\" : 1.0, \"extend\" : {\"number\" : 30, \"amount\" : 120.3, \"extendChild\" : {\"numberChild\" : 10, \"amountChild\" : 110.3}}}"
            val gson = Gson()
            val data = gson.fromJson(dataJson, Lqhtest.Data::class.java)
            var bundle=Bundle()
            bundle.putSerializable(Constants.Strings.DATA, data)
            jumpToClass(LqhTestBActivity::class.java,bundle)
        }
    }

    override fun getLayoutId(): Int=R.layout.activity_lqh_test_a
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