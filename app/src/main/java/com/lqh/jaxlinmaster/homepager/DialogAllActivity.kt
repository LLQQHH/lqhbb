package com.lqh.jaxlinmaster.homepager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lqh.jaxlinmaster.R
import com.lqh.jaxlinmaster.lqhbase.LqhBaseActivity

class DialogAllActivity : LqhBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_all)
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getLayoutId(): Int =R.layout.activity_dialog_all
}