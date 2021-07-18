package com.lqh.jaxlinmaster.lqhbase

/**
 * Created by Linqh on 2021/7/18.
@describe:
 *
 */
//@CreateUidAnnotation(uid = "10100")
interface IPareVisibilityObserver {
    fun onParentFragmentHiddenChanged(isVisibleToUser:Boolean)
}