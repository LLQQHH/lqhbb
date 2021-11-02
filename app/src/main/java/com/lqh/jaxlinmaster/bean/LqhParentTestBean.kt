package com.lqh.jaxlinmaster.bean

import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtil
import java.io.Serializable

/**
 * Created by Linqh on 2021/6/25.
@describe:
 *
 */
//@CreateUidAnnotation(uid = "10100")
class LqhParentTestBean(var  nameParent:String) :Serializable {
    var ageParent:Int=0
    var parentList:MutableList<LqhTestBean>?=null
    constructor( nameParent:String,ageParent:Int):this(nameParent){
            this.ageParent=ageParent
        LogUtil.e("constructor中name:"+this.nameParent)
        LogUtil.e("constructor中age:"+this.ageParent)
    }

}