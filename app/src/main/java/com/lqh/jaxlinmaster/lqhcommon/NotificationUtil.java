package com.lqh.jaxlinmaster.lqhcommon;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.lqh.jaxlinmaster.R;
import com.lqh.jaxlinmaster.homepager.LqhNotificationActivity;

/**
 * Created by Linqh on 2021/10/14.
 *
 * @describe:
 */
//@CreateUidAnnotation(uid = "10100")
public class NotificationUtil {
    //====用在普通推送
    public static final String ALL_NOTIFY_CHANNELID="all_notify_channelid";
    public static final String ALL_NOTIFY_CHANNELNAME="普通通知";
    public static final String ALL_NOTIFY_CHANNELDESCRIPTION="震动加响铃";
    //===其他
    public static final String VIBRATION_NOTIFY_CHANNELID="vibration_notify_channelid";
    public static final String VIBRATION_NOTIFY_CHANNELNAME="震动通知";
    public static final String VIBRATION_NOTIFY_CHANNELDESCRIPTION="仅震动";
    //===如用在下载apk
    public static final String SILENT_NOTIFY_CHANNELID="silent_notify_channelid";
    public static final String SILENT_NOTIFY_CHANNELNAME="静默通知";
    public static final String SILENT_NOTIFY_CHANNELDESCRIPTION="无感知";

    /**
     * @param mContext
     * @param notifyId
     * @param title
     * @param content
     * @param smallIconId
     * @param bigIcon
     * @param notifyType 0,1,2
     */
    public static void showNotification(Context mContext, int notifyId, String title, String content,Intent intent,
                                        int smallIconId, Bitmap bigIcon,int notifyType){
        //创建通知栏管理工具
        NotificationManager nm = (NotificationManager)  mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        //这个id和NotificationChannel的id一样
        String channelId=ALL_NOTIFY_CHANNELID;
        String channelName=ALL_NOTIFY_CHANNELNAME;
        String channelDescription=ALL_NOTIFY_CHANNELDESCRIPTION;
        if(notifyType==0){
            channelId=ALL_NOTIFY_CHANNELID;
            channelName=ALL_NOTIFY_CHANNELNAME;
            channelDescription=ALL_NOTIFY_CHANNELDESCRIPTION;
        }else if(notifyType==1){
            channelId=VIBRATION_NOTIFY_CHANNELID;
            channelName=VIBRATION_NOTIFY_CHANNELNAME;
            channelDescription=VIBRATION_NOTIFY_CHANNELDESCRIPTION;
        }else if(notifyType==2){
            channelId=SILENT_NOTIFY_CHANNELID;
            channelName=SILENT_NOTIFY_CHANNELNAME;
            channelDescription=SILENT_NOTIFY_CHANNELDESCRIPTION;
        }
        /**
         *  实例化通知栏Builder,channelId 渠道id需要唯一
         */

        NotificationCompat.Builder builder=new NotificationCompat.Builder(mContext,channelId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //渠道id，和渠道名字，可以设置一样,8.0以上代码中会不会产生悬浮是由importance决定的，但是不同厂商会屏蔽这个设定，还是乖乖去设置中设置
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            mChannel.setDescription(channelDescription);
            //8.0以上声音控制
            if(notifyType==0){
                mChannel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION),Notification.AUDIO_ATTRIBUTES_DEFAULT);
                mChannel.enableVibration(true);
                mChannel.enableLights(true);
            }else if(notifyType==1){
                mChannel.setSound(null,null);
                mChannel.enableVibration(true);
                mChannel.enableLights(false);
            }else{
                mChannel.setSound(null,null);
                mChannel.enableVibration(false);
                mChannel.enableLights(false);
            }
            nm.createNotificationChannel(mChannel);
        }
        //====
        //设置标题
        builder.setContentTitle(title);
        //设置内容
        builder.setContentText(content);
        //设置小图标
        builder.setSmallIcon(smallIconId);
        if(bigIcon!=null){
            builder.setLargeIcon(bigIcon);
        }
        //8.0以上代码中会不会产生悬浮是由setPriority决定的，但是不同厂商会屏蔽这个设定，还是乖乖去设置中设置
        builder.setPriority(Notification.PRIORITY_HIGH);
        //设置通知时间
        builder.setWhen(System.currentTimeMillis());
        /**
         *  设置点击后触发的事件
         */
        PendingIntent contentIntent=PendingIntent.getActivity(mContext,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        //设置点击触发的事件
        builder.setContentIntent(contentIntent);
        builder.setAutoCancel(true);//设置点击是否直接关闭
        //8.0以下声音控制
        if(notifyType==0){
            builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            //设置通知方式，声音，震动，呼吸灯等效果，这里通知方式为全部,多种用|叠加
            builder.setDefaults(NotificationCompat.DEFAULT_SOUND|NotificationCompat.DEFAULT_VIBRATE|NotificationCompat.DEFAULT_LIGHTS);
        }else if(notifyType==1){
            builder.setSound(null);
            builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        }else{
//            builder.setVibrate(new long[]{0});
//            builder.setSound(null);
//            builder.setLights(0, 0, 0);
            //8.0以下发现要静默通知只能用这个方法
            builder.setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE);
        }
        //发送通知请求，notifyId不能一样,如果一样就相当于更新这个通知而不是产生新通知
        nm.notify(notifyId, builder.build());
    }
    public static void playNotificationRing(Context context) {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone rt = RingtoneManager.getRingtone(context, uri);
        rt.play();
    }
    /**
     * 手机震动一下
     */
    public static void playNotificationVibrate(Context context) {
        Vibrator mVibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        long[] vibrationPattern = new long[]{0, 180, 80, 120};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 第一个参数为震动周期，第二个参数是重复次数，振动需要添加权限
            VibrationEffect vibrationEffect = VibrationEffect.createWaveform(vibrationPattern,-1);
            mVibrator.vibrate(vibrationEffect);
        } else {
            //第一个参数为震动周期，第二个参数是重复次数，振动需要添加权限
            mVibrator.vibrate(vibrationPattern,-1);
        }
    }
}
