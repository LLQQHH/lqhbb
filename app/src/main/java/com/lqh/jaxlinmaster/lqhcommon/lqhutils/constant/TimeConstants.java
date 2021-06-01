package com.lqh.jaxlinmaster.lqhcommon.lqhutils.constant;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/03/13
 *     desc  : constants of time
 * </pre>
 */
public final class TimeConstants {

    public static final int MSEC = 1;//毫秒
    public static final int SEC  = 1000;//秒
    public static final int MIN  = 60000;//分
    public static final int HOUR = 3600000;//小时
    public static final int DAY  = 86400000;//天

    @IntDef({MSEC, SEC, MIN, HOUR, DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
