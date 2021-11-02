package com.lqh.jaxlinmaster.homepager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lqh.jaxlinmaster.R
import com.lqh.jaxlinmaster.constants.Constants
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.glideutils.GlideUtil
import kotlinx.android.synthetic.main.activity_glide.*

class GlideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glide)
        tv_Glide.setOnClickListener {
            var url =Constants.Strings.RESOURCE_FRONT+"7"
            GlideUtil.getInstance().displayCompanyImage(this@GlideActivity,url,iv_glide)
        }
        tv_glideApp.setOnClickListener {
            var url =Constants.Strings.RESOURCE_FRONT+"8"
            GlideUtil.getInstance().displayAppCompanyImage(this@GlideActivity,url,iv_glideApp)
        }
    }
}