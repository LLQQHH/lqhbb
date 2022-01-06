package com.lqh.testaidlapp

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.text.TextUtils
import android.widget.Toast
import com.lqh.jaxlinmaster.IMyAidlInterface
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var asInterface :IMyAidlInterface?=null
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_bindAIDl.setOnClickListener {
            var intent=Intent()
            intent.setPackage("com.lqh.jaxlinmaster")
            intent.action="com.lqh.jaxlinmaster.lqhtestaidlservice"
            bindService(intent,object :ServiceConnection{
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                     asInterface = IMyAidlInterface.Stub.asInterface(service)
                    asInterface?.apply {
                        tv_text.setText("绑定成功")
                    }
                }
                override fun onServiceDisconnected(name: ComponentName?) {
                    tv_text.setText("链接断开绑定失败")
                }

            }, BIND_AUTO_CREATE)
        }
        btn_show.setOnClickListener {
            if (asInterface!=null){
                if(!TextUtils.isEmpty(ed_input.text.toString())){
                    tv_text.setText("价格为："+asInterface!!.getFoodPrice(ed_input.text.toString()))
                }else{
                  Toast.makeText(this,"请输入食物名称",Toast.LENGTH_SHORT).show();
                }
            }else{
                tv_text.setText("服务器未连接")
            }
        }
    }
}