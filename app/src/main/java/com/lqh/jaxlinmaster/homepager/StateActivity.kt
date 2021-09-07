package com.lqh.jaxlinmaster.homepager

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.lqh.jaxlinmaster.R
import com.lqh.jaxlinmaster.lqhbase.LqhBaseActivity
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtils
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.StatusBarUtil
import com.lqh.jaxlinmaster.lqhtest.LqhTestActivity
import kotlinx.android.synthetic.main.activity_state.*


class StateActivity : LqhBaseActivity() {

    override fun initView(savedInstanceState: Bundle?){
        StatusBarUtil.setStatusBarLightMode(this,false)
//        val lqhStateView = LqhStateView.inject(R.id.lin_content, this)
//        Handler().postDelayed(Runnable {
//            lqhStateView.showLoading()
//        },1000)
//        Handler().postDelayed(Runnable {
//            lqhStateView.showRetry()
//        },2000)
//        Handler().postDelayed(Runnable {
//            lqhStateView.showEmpty()
//        },3000)

        LogUtils.e("当前systemUiVisibility",""+window.decorView.systemUiVisibility)
        tv_showFull.setOnClickListener {
            StatusBarUtil.setFullScreenReal(this,null)
        }
        tv_showStatusBarIn.setOnClickListener {
            StatusBarUtil.setStatusBarColorForOffset(this,ContextCompat.getColor(this,R.color.teal_200),null,true)
            LogUtils.e("当前systemUiVisibility1",""+window.decorView.systemUiVisibility)
        }
        tv_showStatusBarNotIn.setOnClickListener {
            StatusBarUtil.setStatusBarColor(this,ContextCompat.getColor(this,R.color.green_beautiful_color))
            StatusBarUtil.hideCreateStatusBarView(this)
            LogUtils.e("当前systemUiVisibility2",""+window.decorView.systemUiVisibility)
        }
        tv_showNav.setOnClickListener {
            StatusBarUtil.setNavBarColor(this,ContextCompat.getColor(this,R.color.blue_beautiful_color),false)
            LogUtils.e("当前systemUiVisibility3",""+window.decorView.systemUiVisibility)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        tv_anim.setOnClickListener {
            var intent=Intent(this,LqhTestActivity::class.java)
            //val toBundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle()
            ActivityCompat.startActivity(this,intent,null)
            overridePendingTransition(R.anim.anim_activity_translate_to_in,R.anim.anim_activity_translate_y_no_anim)
        }
    }
    override fun beforeContentView() {
        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
//        val enterTransition = TransitionInflater.from(this).inflateTransition(R.transition.slidebottom)
//        val exitTransition = TransitionInflater.from(this).inflateTransition(R.transition.slidetop)
//        val reenterTransition = TransitionInflater.from(this).inflateTransition(R.transition.slidebottom)
//        val returnTransition = TransitionInflater.from(this).inflateTransition(R.transition.slidetop)
//        //A中的View退出场景
////        getWindow().setExitTransition(enterTransition)
//        //B中的View进入场景的
////        getWindow().setEnterTransition(enterTransition)
//        //当B 返回 A时，使A中的View进入场景的transition
//        getWindow().setReenterTransition(null)
////        //当B 返回 A时，使B中的View退出场景的transition
//        getWindow().setReturnTransition(returnTransition)
    }

    override fun getLayoutId(): Int=R.layout.activity_state
    override fun finish() {
        super.finish()
        //overridePendingTransition(R.anim.anim_activity_translate_back_in,R.anim.anim_activity_translate_back_out)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun initStatusBar() {
    //StatusBarUtil.setStatusBarColor(this,ContextCompat.getColor(this,R.color.red_color))
//        window.decorView.setOnApplyWindowInsetsListener(object: View.OnApplyWindowInsetsListener{
//            override fun onApplyWindowInsets(v: View, insets: WindowInsets): WindowInsets {
//                val defaultWindowInsets = v.onApplyWindowInsets(insets)
//                Log.e("大小","Left"+insets.systemWindowInsetLeft)
//                Log.e("大小","Top"+insets.systemWindowInsetTop)
//                Log.e("大小","Right"+insets.systemWindowInsetRight)
//                Log.e("大小","Bottom"+insets.systemWindowInsetBottom)
//                return  defaultWindowInsets.replaceSystemWindowInsets(
//                    defaultWindowInsets.systemWindowInsetLeft,
//                    0,
//                    defaultWindowInsets.systemWindowInsetRight,
//                    defaultWindowInsets.systemWindowInsetBottom)
//
//            }
//        })
    }

}