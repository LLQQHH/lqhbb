package com.lqh.jaxlinmaster.lqhbase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Created by Linqh on 2021/6/24.
@describe:
 *
 */
//@CreateUidAnnotation(uid = "10100")
abstract class LqhBaseFragment: Fragment() {
    protected var layout: View? = null
    private  var hasLayout:Boolean=false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (layout == null){
            layout = inflater.inflate(getLayoutId(), container, false)
            hasLayout=true
        }
        val parent = layout?.parent as ViewGroup?
        parent?.removeView(layout)
        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //挪到这边为了是kotlin控件好找
        if (hasLayout){
            hasLayout=false
            initView(layout!!)
            initData()
        }
    }

    abstract fun initView(layout: View)

    private fun initData() {
    }

    //初始化布局
    abstract fun getLayoutId(): Int

}