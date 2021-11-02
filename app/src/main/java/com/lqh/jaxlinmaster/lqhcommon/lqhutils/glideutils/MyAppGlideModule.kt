package com.lqh.jaxlinmaster.lqhcommon.lqhutils.glideutils

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.lqh.jaxlinmaster.R
import com.lqh.jaxlinmaster.interceptor.GlideInterceptor
import okhttp3.OkHttpClient
import java.io.InputStream


/**
 * Created by Linqh on 2021/9/9.
@describe:实现链式调用
 *
 */
//@CreateUidAnnotation(uid = "10100")
@GlideModule
class MyAppGlideModule : AppGlideModule() {
    //重写一些默认配置
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_chat_picture)
            .error(R.drawable.ic_chat_picture)
        builder.setDefaultRequestOptions(requestOptions)
//            .skipMemoryCache(false) //使用原图
//            .diskCacheStrategy(DiskCacheStrategy.DATA) //只缓存原始图片
//        builder.setDefaultRequestOptions(requestOptions)
//        val calculator = MemorySizeCalculator.Builder(context).build()
//        val defaultMemoryCacheSize = calculator.memoryCacheSize
//        val defaultBitmapPoolSize = calculator.bitmapPoolSize
//        val defaultArrayPoolSize = calculator.arrayPoolSizeInBytes
//        builder.setDefaultRequestOptions(RequestOptions().format(DecodeFormat.PREFER_RGB_565))
//        builder.setMemoryCache(LruResourceCache((defaultMemoryCacheSize / 2).toLong()))
//        builder.setBitmapPool(LruBitmapPool((defaultBitmapPoolSize / 2).toLong()))
//        builder.setArrayPool(LruArrayPool(defaultArrayPoolSize / 2))
    }
    //重写一些组件配置
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {

         //比如重写使用okhttp加载,
        val builder = OkHttpClient.Builder()
        //打印地址
        builder.addInterceptor(GlideInterceptor())
        val okHttpClient = builder.build()
        //得先依赖这个compile "com.github.bumptech.glide:okhttp3-integration:4.11.0"，才有OkHttpUrlLoader.Factory
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okHttpClient)
        )
    }
}