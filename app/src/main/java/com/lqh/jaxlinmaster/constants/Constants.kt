package com.lqh.jaxlinmaster.constants

/**
 * Created by Linqh on 2021/8/4.
@describe:
 *
 */
//@CreateUidAnnotation(uid = "10100")
class Constants {
    //可以省略companion
     object Strings {
         const val BUNDLE = "bundle"
         const val DATA = "data"
         const val HEAD_KEY = "Authorization"
         const val HEAD_KEY_VALUE_PRE = "Bearer "
         const val RESOURCE_FRONT = "http://dev5api.baiduyo.com.cn/bdisk/dupan/get?id="
          const val LQHTOKEN = "45C459A3-81C2-14E3-FCBE-AA3C4807DB08"
    }
    //不需要名字的时候可以省略
//    companion object  {
//        const val BUNDLE = "bundle"
//    }

    object RQCode {
        const val CREATE_TARGET_RC = 5000 //创建指标
    }
}