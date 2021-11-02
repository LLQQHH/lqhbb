package com.lqh.jaxlinmaster.homepager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.core.app.NotificationCompat
import com.lqh.jaxlinmaster.MainActivity
import com.lqh.jaxlinmaster.R
import com.lqh.jaxlinmaster.lqhbase.LqhBaseActivity
import com.lqh.jaxlinmaster.lqhcommon.NotificationUtil
import kotlinx.android.synthetic.main.activity_lqh_notification.*


class LqhNotificationActivity : LqhBaseActivity() {
    override fun initView(savedInstanceState: Bundle?) {
        tv_notification.setOnClickListener {
            var intent=Intent(this,LqhNotificationActivity::class.java)
            NotificationUtil.showNotification(this,1,"通知0","通知的内容0",intent,R.mipmap.ic_launcher_round,
                BitmapFactory.decodeResource(getResources(), R.drawable.default_avatar),0)
//            showNotification(this,1,"10","测试通知","通知","通知的内容0",
//            0)
        }
        tv_notification1.setOnClickListener {
            var intent=Intent(this,LqhNotificationActivity::class.java)
            NotificationUtil.showNotification(this,2,"通知1","通知的内容1",intent,R.mipmap.ic_launcher_round,
                BitmapFactory.decodeResource(getResources(), R.drawable.default_avatar),1)
//            showNotification(this,2,"11","测试通知1","通知","通知的内容1",
//                1)
        }
        tv_notification2.setOnClickListener {
            tv_notification2.postDelayed(
                {
                    var intent=Intent(this,LqhNotificationActivity::class.java)
                    NotificationUtil.showNotification(this,3,"通知2","通知的内容2",intent,R.mipmap.ic_launcher_round,
                        BitmapFactory.decodeResource(getResources(), R.drawable.default_avatar),2)
//                    var notifyId =14
//                    var channelId ="14"
//                    var channelName ="14name"
//                    val nm = LqhNotificationActivity@this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//                    //这个id和NotificationChannel的id一样
//                    val builder = NotificationCompat.Builder(LqhNotificationActivity@this, channelId!!)
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        //渠道id，和渠道名字，可以设置一样
//                        val mChannel =
//                            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
//                        mChannel.enableVibration(true)
//                        mChannel.enableLights(true)
//                        nm.createNotificationChannel(mChannel)
//                    }
//                    //====
//                    //设置标题
//                    builder.setContentTitle(title)
//                    //设置内容
//                    builder.setContentText("测试哈哈")
//                    //设置小图标
//                    builder.setSmallIcon(R.mipmap.ic_launcher_round)
//                    builder.setLargeIcon(BitmapFactory.decodeResource( LqhNotificationActivity@this.getResources(), R.drawable.default_avatar))
//                    //设置通知时间
//                    builder.setWhen(System.currentTimeMillis())
//                    builder.setPriority(NotificationCompat.PRIORITY_MAX)
//                    /**
//                     * 设置点击后触发的事件
//                     */
//                    val intent = Intent(LqhNotificationActivity@this, LqhNotificationActivity::class.java)
//                    val contentIntent =
//                        PendingIntent.getActivity(LqhNotificationActivity@this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//                    //设置点击触发的事件
//                    builder.setContentIntent(contentIntent)
//                    builder.setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE or NotificationCompat.DEFAULT_LIGHTS)
//                    nm.notify(notifyId, builder.build())
//                    showNotification(this,3,"10","测试通知","通知","通知的内容2",
//                2)

                                         },1500)


        }
        tv_notification3.setOnClickListener {
            var NOTIFICATION_ID=200
            val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                //渠道id，和渠道名字，可以设置一样,8.0以上代码中会不会产生悬浮是由importance决定的，但是不同厂商会屏蔽这个设定，还是乖乖去设置中设置
                val mChannel =
                    NotificationChannel(NotificationUtil.SILENT_NOTIFY_CHANNELID+"apk", NotificationUtil.SILENT_NOTIFY_CHANNELNAME+"apk", NotificationManager.IMPORTANCE_LOW)
                mChannel.description = NotificationUtil.SILENT_NOTIFY_CHANNELDESCRIPTION+"apk"
                mNotificationManager.createNotificationChannel(mChannel);
            }
            val mProgressBuilder =
                NotificationCompat.Builder(this, NotificationUtil.SILENT_NOTIFY_CHANNELID + "apk")
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            resources,
                            R.drawable.default_avatar
                        )
                    )
                    .setProgress(100, 0, false)
                    .setContentTitle("模糊进度条通知")
                    .setContentText("这是一条模糊进度条通知")
            object : Thread() {
                override fun run() {
                    for (i in 0..100) {
                        mProgressBuilder.setProgress(100, i, false)
                        if (i==100){
                            val intent = Intent(this@LqhNotificationActivity, MainActivity::class.java)
                            val pendingIntent =
                                PendingIntent.getActivity(this@LqhNotificationActivity, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT)
                            mProgressBuilder.setContentIntent(pendingIntent)
                            mProgressBuilder.setAutoCancel(true)
                        }
                        mNotificationManager.notify(NOTIFICATION_ID, mProgressBuilder.build())
                        try {
                            sleep(50)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                }
            }.start()

        }
        tv_notification4.setOnClickListener {
            try {
                // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
                val intent = Intent()
                intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, applicationInfo.uid)

                //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
                intent.putExtra("app_package", packageName)
                intent.putExtra("app_uid", applicationInfo.uid)

                // 小米6 -MIUI9.6-8.0.0系统，是个特例，通知设置界面只能控制"允许使用通知圆点"——然而这个玩意并没有卵用，我想对雷布斯说：I'm not ok!!!
                //  if ("MI 6".equals(Build.MODEL)) {
                //      intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                //      Uri uri = Uri.fromParts("package", getPackageName(), null);
                //      intent.setData(uri);
                //      // intent.setAction("com.android.settings/.SubSettings");
                //  }
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                // 出现异常则跳转到应用设置界面：锤子坚果3——OC105 API25
                val intent = Intent()

                //下面这种方案是直接跳转到当前应用的设置界面。
                //https://blog.csdn.net/ysy950803/article/details/71910806
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
        }
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getLayoutId(): Int =R.layout.activity_lqh_notification
    fun showNotification(
        mContext: Context,
        notifyId: Int,
        channelId: String?,
        channelName: String?,
        title: String?,
        content: String?,showType:Int) {
        val nm = mContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        //这个id和NotificationChannel的id一样
        val builder = NotificationCompat.Builder(mContext, channelId!!)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //渠道id，和渠道名字，可以设置一样
            val mChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            mChannel.enableVibration(true)
            mChannel.enableLights(true)
            mChannel.setSound(null,null)
            nm.createNotificationChannel(mChannel)
        }
        //====
        //设置标题
        builder.setContentTitle(title)
        //设置内容
        builder.setContentText(content)
        //设置小图标
        builder.setSmallIcon(R.mipmap.ic_launcher_round)
        //设置通知时间
        builder.setWhen(System.currentTimeMillis())
        /**
         * 设置点击后触发的事件
         */
        val intent = Intent(mContext, LqhNotificationActivity::class.java)
        val contentIntent =
            PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        //设置点击触发的事件
        builder.setContentIntent(contentIntent)
        //设置通知方式，声音，震动，呼吸灯等效果，这里通知方式为全部,多种用|叠加
        if (showType==0){
            builder.setDefaults(NotificationCompat.DEFAULT_ALL)
        }else if(showType==1){
            builder.setDefaults(NotificationCompat.DEFAULT_SOUND)
        }else if(showType==2){
            builder.setDefaults(Notification.DEFAULT_VIBRATE)
        }else{
            builder.setDefaults(NotificationCompat.DEFAULT_ALL)
        }
        builder.setAutoCancel(true) //设置点击是否直接关闭
        //===
        nm.notify(notifyId, builder.build())
    }

}