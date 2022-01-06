package com.lqh.jaxlinmaster.lqhtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lqh.jaxlinmaster.R
import com.lqh.jaxlinmaster.bean.LqhTestBean
import com.lqh.jaxlinmaster.bean.LqhTestBeanNew
import com.lqh.jaxlinmaster.constants.Constants
import com.lqh.jaxlinmaster.lqhbase.LqhBaseActivity
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtil
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.ToastUtil
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.gsonutils.GsonUtil
import com.lqh.jaxlinmaster.lqhwidget.lqhflowlayout.FlowAdapter
import com.lqh.jaxlinmaster.lqhwidget.lqhflowlayout.TagFlowAdapter
import kotlinx.android.synthetic.main.activity_lqh_test_b.*
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.util.*


class LqhTestBActivity : LqhBaseActivity() {
    override fun initView(savedInstanceState: Bundle?) {
        val sourceJsonStr = "{\"Rate\" : 1.0, \"duck\" : {\"number\" : 30, \"amount\" : 120.3}}"
        val toJsonStr = "{\"Rate\" : 2.0,\"name\" : \"测试\", \"dog\" : {\"number\" : 31, \"amount\" : 122.3}}"
        var hebingJson="{\"Rate\":1,\"name\":\"测试\",\"dog\":{\"number\":31,\"amount\":122.3},\"duck\":{\"number\":30,\"amount\":120.3}}"
        try {
            val sourceJson = JSONObject(sourceJsonStr)
            val toJson = JSONObject(toJsonStr)
            val keys = sourceJson.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                val o = sourceJson.opt(key)
                toJson.put(key,o)
            }
            LogUtil.e("合并后",toJson.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        var tempBean= LqhTestBean("测试")
        tempBean.surname="林"
        tempBean.familyAddress="测试地址"
        tempBean.emailAddress="123456789@qq.com"
        LogUtil.e("tempBean前", tempBean.toString())
        val toJson = GsonUtil.toJson(tempBean)
       LogUtil.e("tempBean用GsontoJson", toJson)
        val tempBeanNew = GsonUtil.parseJsonObject(toJson, LqhTestBean::class.java);
        tempBeanNew?.javaClass?.declaredFields?.forEach {
            it.setAccessible(true);//开放权限
            LogUtil.e("变量名字",it.name)
        }
        try {
            val declaredField = tempBeanNew?.javaClass?.getDeclaredField("age")
            if (declaredField!=null){
                declaredField.setAccessible(true);//开放权限
                LogUtil.e("变量名字age获取","成功"+declaredField.name)
            }else{
                LogUtil.e("变量名字age获取","失败");
            }
        }catch (e:Exception){
            LogUtil.e("变量名字age获取","失败"+e.message);
        }

        LogUtil.e("tempBean后", tempBeanNew.toString())
        var isTestSelected=false
        tv_title.isDuplicateParentStateEnabled=true
        lin_test.isEnabled=false
        lin_test.setOnClickListener {
            isTestSelected=!isTestSelected;
            lin_test.isSelected=isTestSelected
        }
        var set= TreeSet<Int>()
        set.add(1)
        set.add(2)
        set.add(3)
        set.add(4)
        var set1= TreeSet<Int>(set)

        var set2= TreeSet<Int>()
        set2.addAll(set.subSet(0,set.size))
        LogUtil.e("set2前",""+set2.size+set2.toString())
        set.remove(1)
        LogUtil.e("set",""+set.size+set.toString())
        LogUtil.e("set1",""+set1.size+set1.toString())
        LogUtil.e("set2后",""+set2.size+set2.toString())
        //ViewCompat.setTransitionName(tv_test,"test")
        var tempList= mutableListOf<String>("1","2222","333333333","44444","555555","6","77777","88888888888"
        ,"9999999999999999999","aaaaaaaaaaaaaa","bbbbbbbbb","ccccccccccccccc","dddddddddddddddddd","eeeeeeeeeeeeeeeeee","ffffffffffffffffff"
            ,"ggggggggg","hhhhhhhhhh","iii")
        var lqhFlowAdapter=object :FlowAdapter<String>(this,tempList){
            override fun onCreateView(parent: ViewGroup, position: Int, itemData: String?): View {
              return  LayoutInflater.from(parent.context).inflate(R.layout.lqh_test_item,parent,false)
            }

            override fun onBindView(view: View, position: Int, itemData: String?) {
                val tvTitle = view.findViewById<TextView>(R.id.tv_title)
                tvTitle.setText(itemData)
            }
        }
        lqhFlowAdapter.setOnItemClickListener { flowAdapter, view, position ->
            ToastUtil.show("点击了"+position)
            if (position==2){
                flowAdapter.list[position]="3"
                flowAdapter.notifyDataSetChanged(position)
            }
        }
        lqhFlowAdapter.setOnItemLongClickListener { flowAdapter, view, position ->
            ToastUtil.show("长按"+position)
            return@setOnItemLongClickListener true
        }
        lqhFlowLayout.setAdapter(lqhFlowAdapter)
        lqhFlowLayout.setOnOverLineChangeListener {
            if (it){
                expand.visibility=View.VISIBLE
            }else{
                expand.visibility=View.GONE
            }
        }
        lqhFlowLayout.setOnExpandStateChangeListener {
            if (it){
                expand.setText("展开")
            }else{
                expand.setText("收起")
            }
        }
        expand.setOnClickListener {
            lqhFlowLayout.isCollapsed=!lqhFlowLayout.isCollapsed
        }

        var tempList1= mutableListOf<String>("1","2222","333333333","44444","555555","6","77777","88888888888"
            ,"9999999999999999999","aaaaaaaaaaaaaa","bbbbbbbbb","ccccccccccccccc","dddddddddddddddddd","eeeeeeeeeeeeeeeeee","ffffffffffffffffff"
            ,"ggggggggg","hhhhhhhhhh","iii")
        var lqhFlowAdapter1=object :TagFlowAdapter<String>(this,tempList1){
            override fun onCreateView(parent: ViewGroup, position: Int, itemData: String?): View {
                return  LayoutInflater.from(parent.context).inflate(R.layout.lqh_test_item_with_selector,parent,false)
            }

            override fun onBindTagView(view: View, position: Int, itemData: String) {
                val tvTitle = view.findViewById<TextView>(R.id.tv_title)
                tvTitle.setText(itemData)
            }

            override fun onSelected(view: View, position: Int, itemData: String, isClick: Boolean) {
               if (isClick){
                   val tv_title = view.findViewById<TextView>(R.id.tv_title);

                   LogUtil.e("lqhFlowAdapter1","选中"+position)
               }
            }

            override fun unSelected(view: View, position: Int, itemData: String, isClick: Boolean) {
                if (isClick){
                    tv_title.isEnabled=false
                    LogUtil.e("lqhFlowAdapter1","未选中"+position)
                }
            }

            //点击了不能被选中的View的回调
            override fun clickCannotSelectedView(view: View?, position: Int, itemData: String, isClick: Boolean) {
                if (isClick){
                    LogUtil.e("lqhFlowAdapter1","不可点击的"+position)
                }
            }
            //超过最大个数点击后的回调
            override fun clickOverLimitCount(view: View, position: Int, itemData: String, maxSelectCount: Int
            ) {
                LogUtil.e("lqhFlowAdapter1","最多选"+maxSelectCount)
            }

            override fun isCanSelected(view: View?, position: Int, itemData: String?): Boolean {
               if (position==0){
                   return false;
               }
                return true
            }
        }
        lqhFlowAdapter1.setOnTagClickListener{ flowAdapter, view, position ->
            LogUtil.e("lqhFlowAdapter1","点击"+position)
        }
        lqhFlowAdapter1.setOnSelectListener {
            LogUtil.e("lqhFlowAdapter1","一共选了${it.size},分别为:${it}")
        }
        lqhFlowAdapter1.isMul=true
        lqhFlowAdapter1.maxSelectCount=3
        lqhFlowLayout1.setAdapter(lqhFlowAdapter1)
        lqhFlowLayout1.setOnOverLineChangeListener {
            if (it){
                expand1.visibility=View.VISIBLE
            }else{
                expand1.visibility=View.GONE
            }
        }
        lqhFlowLayout1.setOnExpandStateChangeListener {
               if (it){
                   expand1.setText("收起")
               }else{
                   expand1.setText("展开")
               }
        }
        expand1.setOnClickListener {
                lqhFlowLayout1.isCollapsed=!lqhFlowLayout1.isCollapsed
        }
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
