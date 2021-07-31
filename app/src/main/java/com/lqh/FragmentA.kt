package com.lqh

import android.content.*
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lqh.jaxlinmaster.R
import com.lqh.jaxlinmaster.lqhbase.BaseLazyFragmentForX
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtils
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.ToastUtils
import kotlinx.android.synthetic.main.fragment_a.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


/**
 * Created by Linqh on 2021/6/24.
@describe:
 *
 */
//@CreateUidAnnotation(uid = "10100")
class FragmentA : BaseLazyFragmentForX() {
    private var title: String? = null

    companion object {
        fun newInstance(title: String): FragmentA {
            var testFragment = FragmentA()
            val bundle = Bundle()
            bundle.putString("title", title)
            testFragment.arguments = bundle
            return testFragment;
        }
    }

    override fun initView(layout: View) {
        tv_title.text = title
    }

    override fun getLayoutId(): Int = R.layout.fragment_a
    override fun onAttach(context: Context) {
        super.onAttach(context)
        title = arguments?.getString("title")
        LogUtils.e("主当前$title", "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LogUtils.e("主当前$title", "onCreate")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtils.e("主当前$title", "onViewCreated")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogUtils.e("主当前$title", "onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        LogUtils.e("主当前$title", "onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.e("主当前$title", "在onResume中判断isHidden" + isHidden)
        LogUtils.e("主当前$title", "onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.e("主当前$title", "onPause")
        LogUtils.e("主当前$title", "在onPause中判断isHidden" + isHidden)
    }


    override fun onStop() {
        super.onStop()
        LogUtils.e("主当前$title", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogUtils.e("主当前$title", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.e("主当前$title", "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        LogUtils.e("主当前$title", "onDetach")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        title = arguments?.getString("title")
        //居然有时候获取不到
        LogUtils.e("主当前$title", "isVisibleToUser:$isVisibleToUser")
        LogUtils.e("主当前$title", "在setUserVisibleHint中判断isHidden" + isHidden)
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        LogUtils.e("主当前$title", "onHiddenChanged:$hidden")
        LogUtils.e("主当前$title", "在onHiddenChanged中判断isHidden" + isHidden)
    }


    override fun onFragmentPause() {
        LogUtils.e("主当前$title", "不可见onFragmentPause")
    }

    var avatarBitmap: Bitmap? = null
    override fun onFragmentLazyInit(IsFirstVisible: Boolean) {
        LogUtils.e("主当前$title", "可见isFirstLoad:" + IsFirstVisible)
        if (IsFirstVisible) {
            avatarBitmap = getBitmap(R.drawable.default_avatar)

            queryImages()
            if (pathUri != null) {
                var requestOptions = RequestOptions().let {
                    it.placeholder(R.drawable.default_avatar)
                    it.error(R.drawable.default_avatar)
                    it.fallback(R.drawable.default_avatar)
                }
                Glide.with(context!!).load(pathUri.toString()).apply(requestOptions).into(iv_1)
            }
            if (pathStr != null) {
                var requestOptions = RequestOptions().let {
                    it.placeholder(R.drawable.default_avatar)
                    it.error(R.drawable.default_avatar)
                    it.fallback(R.drawable.default_avatar)
                }
                Glide.with(context!!).load(pathStr).apply(requestOptions).into(iv_2)
            }
            tv_save.setOnClickListener {
                savePhotoAlbum(avatarBitmap!!, "test12.jpeg")
            }
        }
    }

    private fun savePhotoAlbum(src: Bitmap, fileName: String) {
        //Androidq以上
        var fileDirPath = Environment.DIRECTORY_PICTURES + File.separator + "lqhPictures"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues()
            //test1
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            contentValues.put(MediaStore.Images.Media.DESCRIPTION, fileName)
            contentValues.put(MediaStore.Images.Media.TITLE, fileName)
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, fileDirPath)
            val contentResolver: ContentResolver = context!!.contentResolver
            //拿到uri
            val uri =
                contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            try {
                val outputStream = contentResolver.openOutputStream(uri!!)
                src.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream!!.flush()
                outputStream.close()
                ToastUtils.show("保存成功")
            } catch (e: IOException) {
                e.printStackTrace()
                ToastUtils.show("保存失败")
            }
        } else {
            try {
                val fileDir = File(Environment.getExternalStorageDirectory().absolutePath,fileDirPath)
                LogUtils.e("跟目录",fileDir.absolutePath)
                if (!fileDir.exists()) {
                    val mkdirs = fileDir.mkdirs()
                    if (mkdirs){
                        LogUtils.e("跟目录","创建成功")
                    }else{
                        LogUtils.e("跟目录","创建失败")
                    }

                }
                val file = File(fileDir, fileName)
                LogUtils.e("跟目录的子目录",file.absolutePath)
                val outputStream = FileOutputStream(file)
                src.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.flush()
                outputStream.close()
                activity!!.sendBroadcast(
                    Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.fromFile(file)
                    )
                )
                ToastUtils.show("保存成功")
            } catch (e: IOException) {
                ToastUtils.show("保存失败")
            }
        }

    }

    private var pathUri: Uri? = null
    private var pathStr: String? = null

    // 先拿到图片数据表的uri
    private val query_uri_image = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    //private val QUERY_URI = MediaStore.Files.getContentUri("external")
    // 需要获取数据表中的哪几列信息
    private val projection = arrayOf(
        MediaStore.Video.Media._ID,
        MediaStore.Video.Media.DATA,
        MediaStore.Video.Media.DISPLAY_NAME,
        MediaStore.Video.Media.MIME_TYPE,
        MediaStore.MediaColumns.WIDTH,
        MediaStore.MediaColumns.HEIGHT
    )

    // 查询条件，因为是查询全部图片，传null,若要过滤Gif,请用下面这个
    private val selection_not_gif = (MediaStore.MediaColumns.SIZE + ">0"
            + " AND " + MediaStore.MediaColumns.MIME_TYPE + "!=?")

    // 条件参数 ，因为是查询全部图片，传null，,若要过滤Gif,请用下面这个
    private val selectionargs = arrayOf("image/gif")

    // 排序：按id倒叙
    private val order = MediaStore.Files.FileColumns._ID + " DESC"

    fun queryImages() {
        LogUtils.e("媒体库query_uri_image", query_uri_image.toString())
        val cursor: Cursor? = context!!.contentResolver
            .query(
                query_uri_image,
                projection,
                selection_not_gif,
                selectionargs,
                order
            )
        try {
            if (cursor != null && cursor.count > 0) {
                var count = 3
                cursor.moveToFirst()
                while (!cursor.isAfterLast) {
                    //获取对应列的值     //获取id是第几列
                    var id = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID))
                    //这个AndroidQ上获取不到,但是q一下就是真实地址
                    val oldPAth =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
                    //名字
                    val name =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME))
                    val mimeType =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE))
                    //通过id获取变成Uri
                    val imageUri =
                        ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                    //下面可以用一个List来接收媒体数据
                    Log.e("媒体库图片地址", imageUri.toString())
                    Log.e("媒体库图片废弃地址", oldPAth)
                    Log.e("媒体库图片名字", name)
                    Log.e("媒体库图片mimeType", mimeType)
                    count--
                    cursor.moveToNext()
                    if (count == 0) {
                        pathUri = imageUri
                        pathStr = oldPAth
                        return
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor!!.close()
        }
    }

    fun getBitmap(@DrawableRes resId: Int): Bitmap? {
        val drawable: Drawable = ContextCompat.getDrawable(context!!, resId) ?: return null
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return bitmap
    }

    private fun drawableToBitamp(drawable: Drawable) {
        val bd = drawable as BitmapDrawable
        var bitmap = bd.bitmap
    }
}