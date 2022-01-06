package com.lqh.jaxlinmaster.lqhtest

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.gson.Gson
import com.lqh.jaxlinmaster.R
import com.lqh.jaxlinmaster.constants.Constants
import com.lqh.jaxlinmaster.lqhbase.LqhBaseActivity
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtil
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.StringUtil
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.ToastUtil
import kotlinx.android.synthetic.main.activity_lqh_test_a.*


class LqhTestAActivity : LqhBaseActivity() {
    override fun initView(savedInstanceState: Bundle?) {
        //ViewCompat.setTransitionName(tv_test,"test")

    }
    private var change:Boolean=false
    var currentCount:Int=0;
    override fun initData(savedInstanceState: Bundle?) {
        tv_testToast.setOnClickListener {
            currentCount++
            show(""+currentCount)
        }
        tv_test.setOnClickListener {
            ActivityCompat.finishAfterTransition(this)
        }
        tv_test1.setOnClickListener {
            //        String s = testMap.toString();
            //val dataJson = "{\"Rate\" : 1.0, \"extend\" : {\"number\" : 30, \"amount\" : 120.3}}"
//            val dataJson = "{\"Rate\" : 1.0, \"extend\" : {\"number\" : 30, \"amount\" : 120.3, \"extendChild\" : {\"numberChild\" : 10, \"amountChild\" : 110.3}}}"
//            val gson = Gson()
//            val data = gson.fromJson(dataJson, Lqhtest.Data::class.java)
//            var bundle=Bundle()
//            bundle.putSerializable(Constants.Strings.DATA, data)
//            jumpToClass(LqhTestBActivity::class.java,bundle)
            jumpToClass(LqhTestBActivity::class.java,null)
        }
        tv_test2.setOnClickListener {
            jumpToClass(LqhTestJavaActivity::class.java,null)
        }
        materialCardView.setOnClickListener {
            materialCardView.isCheckable=true
            ToastUtil.show("点击materialCardView")
        }
        shapeableImageView.setOnClickListener {
            ToastUtil.show("点击shapeableImageView")
        }
        btn_changeImageView.setOnClickListener {
            change=!change
            if (change){
                shapeableImageView.isSelected=true
            }else{
                shapeableImageView.isSelected=false
            }

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
    private var mToast: Toast? = null
    fun show(text: CharSequence?) {
        if (StringUtil.isEmpty(text)) {
            return
        }
/*            if (mToast != null) {
                mToast!!.cancel()
                mToast!!.setText(text)
                mToast!!.setDuration(Toast.LENGTH_SHORT)
            }else{
                mToast =Toast.makeText(this,text,Toast.LENGTH_SHORT)
            }
            mToast!!.show()*/
//            if(mToast == null) {
//                mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
//            } else {
//                mToast!!.setText(text)
//                mToast!!.setDuration(Toast.LENGTH_SHORT)
//            }
//        mToast!!.show()
        }
}