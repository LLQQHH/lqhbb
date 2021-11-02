package com.lqh.jaxlinmaster.lqhcommon.lqhutils.glideutils

import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.Headers

/**
 * Created by Linqh on 2021/9/9.
 * @describe:
 */
//@CreateUidAnnotation(uid = "10100")
class MyGlideUrl @JvmOverloads constructor(
    private val mUrl: String,
    headers: Headers? = Headers.DEFAULT
) : GlideUrl(mUrl, headers) {
    //这个就是缓存key
    override fun getCacheKey(): String {
        return mUrl.replace(findTokenParam(), "")
    }
    //把传来的地址做处理
    private fun findTokenParam(): String {
        var tokenParam = ""
        val tokenKeyIndex =
            if (mUrl.indexOf("?token=") >= 0) mUrl.indexOf("?token=") else mUrl.indexOf("&token=")
        if (tokenKeyIndex != -1) {
            val nextAndIndex = mUrl.indexOf("&", tokenKeyIndex + 1)
            tokenParam = if (nextAndIndex != -1) {
                mUrl.substring(tokenKeyIndex + 1, nextAndIndex + 1)
            } else {
                mUrl.substring(tokenKeyIndex)
            }
        }
        return tokenParam
    }
}