package com.lqh.jaxlinmaster.lqhcommon.lqhutils;

import android.view.View;

import java.util.Calendar;

/**
 * Created by Linqh on 2021/12/14.
 *
 * @describe:
 */
//@CreateUidAnnotation(uid = "10100")
//如果使用这个就是全局了
public class FastClickUtil {

    public static long mLastClickTime = 0;
    public static  int TIME_INTERVAL = 1000;//不设置为,这样外面就可以配置了

    /**
     * @return 返回true表示快速点击，取消执行click事件
     */
    public static boolean isFastClick(){
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastClickTime <= TIME_INTERVAL) {
            return true;
        } else {
            mLastClickTime = currentTime;
            return false;
        }
    }
    public static final int TIME_VIEW_INTERVAL = 1000;
    //一般用在无复用的列表
    public static boolean isFastClickForCustom(View v){
       return isFastClickForCustom(v,TIME_VIEW_INTERVAL);
    }
    public static boolean isFastClickForCustom(View v,long timeInterval){
        Object tag = v.getTag(v.getId());
        long beforeTime = tag != null ? (long) tag : 0;
        long currentTimeMillis = System.currentTimeMillis();
        if(currentTimeMillis - beforeTime <=timeInterval){
            return true;
        }else{
            v.setTag(v.getId(),currentTimeMillis);
            return false;
        }
    }
}
