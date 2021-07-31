package com.lqh.jaxlinmaster.lqhtest;

import com.lqh.jaxlinmaster.lqhcommon.lqhutils.RegexUtil;

/**
 * Created by Linqh on 2021/7/27.
 *
 * @describe:
 */
//@CreateUidAnnotation(uid = "10100")
public class Lqhtest {
    public static void main(String[] args) {
       System.out.println("0.0是不是float:"+RegexUtil.isFloat("0.0"));
    }
}
