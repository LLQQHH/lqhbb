package com.lqh.jaxlinmaster.bean

import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtils

/**
 * Created by Linqh on 2021/6/25.
@describe:
 *
 */
//@CreateUidAnnotation(uid = "10100")
class LqhtestBean(name:String) {
    var name:String=name
    var age:Int=0
    constructor( name:String,age:Int):this(name){
            this.age=age
        LogUtils.e("constructor中name:"+this.name)
        LogUtils.e("constructor中age:"+this.age)
    }
    init {
       LogUtils.e("init改前name:"+this.name)
       LogUtils.e("init改前age:"+this.age)
        this.name="bb"
        this.age=3
       LogUtils.e("init改后name:"+this.name)
       LogUtils.e("init改后age:"+this.age)
    }
}