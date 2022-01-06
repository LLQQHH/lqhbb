package com.lqh.jaxlinmaster.bean

import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtil
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.gsonutils.GsonExclude
import java.io.Serializable

/**
 * Created by Linqh on 2021/6/25.
@describe:
 *
 */
//@CreateUidAnnotation(uid = "10100")
class LqhTestBean(name:String) :Serializable {
    var name:String=name
    var age:Int?=null
    var surname:String?=null
    @GsonExclude
    var familyAddress:String?=null
    var emailAddress:String?=null
    constructor( name:String,age:Int):this(name){
            this.age=age
        LogUtil.e("constructor中name:"+this.name)
        LogUtil.e("constructor中age:"+this.age)
    }

    override fun toString(): String {
        return "LqhTestBean(name='$name', age=$age, surname=$surname, familyAddress=$familyAddress, emailAddress=$emailAddress)"
    }


}