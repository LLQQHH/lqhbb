package com.lqh.jaxlinmaster.lqhtest

import com.lqh.jaxlinmaster.lqhcommon.lqhutils.RegexUtil

/**
 * Created by Linqh on 2021/7/27.
 *
 * @describe:
 */
//@CreateUidAnnotation(uid = "10100")
object LqhtestK {
    @JvmStatic
    fun main(args: Array<String>) {
        println("0.0是不是float:" + RegexUtil.isFloat("0.0"))
        var temlist= mutableListOf<Int>()
        for(index in 0 until 10){
            temlist.add(index)
        }
        kotlin.run breaking@ {
            temlist.forEach continuing@{
                if (it==3){
                    return@breaking
                }
                System.out.println("index="+it);
            }
        }

    }
}