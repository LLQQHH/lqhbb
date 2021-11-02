package com.lqh.jaxlinmaster.homepager

import android.os.Bundle
import com.lqh.jaxlinmaster.R
import com.lqh.jaxlinmaster.bean.LqhParentTestBean
import com.lqh.jaxlinmaster.bean.LqhTestBean
import com.lqh.jaxlinmaster.lqhbase.LqhBaseActivity
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtil
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.sputils.SpUtil
import kotlinx.android.synthetic.main.activity_sp.*

class SpActivity : LqhBaseActivity() {


    override fun initView(savedInstanceState: Bundle?) {
        putInt.setOnClickListener {
            SpUtil.getInstance(this).putBoolean("lqhint",true,10)
        }
        getInt.setOnClickListener {

            val getint = SpUtil.getInstance(this).getBoolean("lqhint")
            LogUtil.e("lqhParentTestBean的基本类型",""+getint)
        }
        putobject.setOnClickListener {
            var tempBean= LqhParentTestBean("2",2)
            var tempList= mutableListOf<LqhTestBean>()
            for (index in 0 until 3){
                var tempBean=LqhTestBean("child$index",index)
                tempList.add(tempBean)
            }
            tempBean.parentList=tempList
            SpUtil.getInstance(this).putObjectUseGson("lqhobject",tempBean)
        }
        getobject.setOnClickListener {
            var lqhParentTestBean = SpUtil.getInstance(this).getObjectUseGson<LqhParentTestBean>("lqhobject",LqhParentTestBean::class.java)
            if (lqhParentTestBean!=null){
                LogUtil.e("lqhParentTestBean",":name=${lqhParentTestBean.nameParent},age=${lqhParentTestBean.ageParent}")
                if (lqhParentTestBean.parentList!=null){
                    lqhParentTestBean.parentList!!.forEach {
                        LogUtil.e("lqhParentTestBean的list",":name=${it.name},age=${it.age}")
                    }
                }
            }
        }
        putList.setOnClickListener {
            var tempList= mutableListOf<LqhTestBean>()
//            for (index in 3 until 6){
//                var tempBean=LqhTestBean("child$index",index)
//                tempList.add(tempBean)
//            }
            SpUtil.getInstance(this).putDataList("lqhList",null)
        }
        getList.setOnClickListener {
            val dataList = SpUtil.getInstance(this).getDataList("lqhList", LqhTestBean::class.java,null)
            if (dataList!=null){
                dataList!!.forEach {
                    LogUtil.e("lqhParentTestBean的list",":name=${it.name},age=${it.age}")
                }
            }

        }
        putMap.setOnClickListener {
                var tempMap= mutableMapOf<String,LqhTestBean>()
//            for (index in 6 until 9){
//                var tempBean=LqhTestBean("child$index",index)
//                tempMap.put(index.toString(),tempBean)
//            }
            SpUtil.getInstance(this).putHashMapData("lqhmap",null)
        }
        getMap.setOnClickListener {
            val hashMapData = SpUtil.getInstance(this).getHashMapData( "lqhmap", String::class.java,LqhTestBean::class.java,null)
            if (hashMapData!=null){
                hashMapData.entries.forEach {
                    LogUtil.e("lqhParentTestBean的map,LqhTestBean",":name=${it.value.name},age=${it.value.age}")
                }
            }

        }
        tv_test.setOnClickListener {

        }
    }


    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getLayoutId(): Int=R.layout.activity_sp
}