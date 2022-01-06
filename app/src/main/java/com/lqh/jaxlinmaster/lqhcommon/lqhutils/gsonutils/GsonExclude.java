package com.lqh.jaxlinmaster.lqhcommon.lqhutils.gsonutils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Linqh on 2021/12/17.
 * 序列化时 用于排除 属性
 *
 */
@Target( ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GsonExclude {
}
