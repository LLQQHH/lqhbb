package com.lqh.jaxlinmaster

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.Gravity
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lqh.jaxlinmaster.lqhbase.BaseLazyFragmentForX
import com.lqh.jaxlinmaster.lqhbase.LqhBaseActivity
import com.lqh.jaxlinmaster.lqhbase.LqhBaseFragment
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtil
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.StatusBarUtil
import com.lqh.jaxlinmaster.lqhwidget.lqhbottomtab.LqhBottomItemView
import com.lqh.jaxlinmaster.lqhwidget.lqhbottomtab.LqhBottomTab
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.net.URI


class MainActivity : LqhBaseActivity() {
    var currentPosition: Int = 0
    val TAG_CURPOS = "tag_curpos"
    private val TAG_POSITONSTR = arrayOf("A", "B", "C", "D", "E")
    private val titles = arrayOf("FA标题", "FB标题", "FC标题", "FD标题", "FE标题")

    //private var fragmentSparseArray = SparseArray<Fragment>()
    private var fragmentList = mutableListOf<LqhBaseFragment>()

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView(savedInstanceState: Bundle?) {
        StatusBarUtil.setStatusBarLightMode(this,true)
        LogUtil.e("当前activity", "onCreate")
        LogUtil.e("目录外部公有getExternalStorageDirectory:",Environment.getExternalStorageDirectory().absolutePath)
        LogUtil.e("目录外部公有getExternalStoragePublicDirectory_PICTURES:",Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath)
        LogUtil.e("目录外部公有getExternalStoragePublicDirectory_DCIM:",Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath)
        LogUtil.e("目录外部公有getDataDirectory:",Environment.getDataDirectory().absolutePath)
        LogUtil.e("目录外部公有getDownloadCacheDirectory:",Environment.getDownloadCacheDirectory().absolutePath)
        LogUtil.e("目录外部公有getRootDirectory:",Environment.getRootDirectory().absolutePath)
        LogUtil.e("目录外部专有getExternalFilesDir:",getExternalFilesDir(Environment.DIRECTORY_MUSIC)!!.absolutePath)
        LogUtil.e("目录外部专有getExternalCacheDir:",externalCacheDir!!.absolutePath)
        LogUtil.e("目录内部getFilesDir:",filesDir!!.absolutePath)
        LogUtil.e("目录内部getCacheDir:",cacheDir!!.absolutePath)
        cacheDir
        val externalFilesDirs = getExternalFilesDirs("")
        externalFilesDirs?.forEachIndexed { index, file ->
            file?.let {
                LogUtil.e("目录内部getExternalFilesDirs:","index:$index+,${it!!.absolutePath}")
            }
        }
        var pathStr=filesDir.absolutePath+File.separator+"text.txt"
        var  file=File(pathStr)
        var uriFromPath= Uri.parse(pathStr)
        var uriFromFile= Uri.fromFile(file)
        val pathFromUri: String? = uriFromFile.path
        var fileFromUri =File(URI(uriFromFile.toString()))
        LogUtil.e("file_path地址:",file.path)
        LogUtil.e("uriFromPath地址:",uriFromPath.toString())
        LogUtil.e("pathFromUri地址:",pathFromUri.toString())
        LogUtil.e("uriFromFile地址:",uriFromFile.toString())
        LogUtil.e("fileFromUri地址:",fileFromUri.path)
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(TAG_CURPOS)
            var fragmentA : FragmentA?= supportFragmentManager.findFragmentByTag(TAG_POSITONSTR[0]) as FragmentA?
            var fragmentB : FragmentB?= supportFragmentManager.findFragmentByTag(TAG_POSITONSTR[1]) as FragmentB?
            var fragmentC : FragmentC?= supportFragmentManager.findFragmentByTag(TAG_POSITONSTR[2]) as FragmentC?
            var fragmentD : FragmentD?= supportFragmentManager.findFragmentByTag(TAG_POSITONSTR[3]) as FragmentD?
            var fragmentE : FragmentE?= supportFragmentManager.findFragmentByTag(TAG_POSITONSTR[4]) as FragmentE?
            fragmentList.add(fragmentA ?: FragmentA.newInstance(titles[0]))
            fragmentList.add(fragmentB ?: FragmentB.newInstance(titles[1]))
            fragmentList.add(fragmentC ?: FragmentC.newInstance(titles[2]))
            fragmentList.add(fragmentD ?: FragmentD.newInstance(titles[3]))
            fragmentList.add(fragmentE ?: FragmentE.newInstance(titles[4]))
        }
        else {
            initFragment()
        }
        //initFragment()
        val buildLqhBottomItemView = LqhBottomItemView.Builder(this)
            .setItemText("测试4")
            .setNormalIcon(ContextCompat.getDrawable(this, R.drawable.ic_main_my_normalcy))
            .setSelectedIcon(ContextCompat.getDrawable(this, R.drawable.ic_main_my_pitchon))
            .build()
        buildLqhBottomItemView.gravity = Gravity.CENTER
        lqhBottomTab.addTab(buildLqhBottomItemView)
        lqhBottomTab.addOnTabSelectedListener(object : LqhBottomTab.OnTabSelectedListener {
            override fun onTabSelected(position: Int) {
                LogUtil.e("选中" + position)
                if (position == 2) {
                    val positionTabView = lqhBottomTab.getPositionTabView(position)
                    if (positionTabView?.customLayout != null) {
                        val ivDesk =
                            positionTabView.customLayout!!.findViewById<ImageView>(R.id.iv_desk)
                        ivDesk.setImageResource(R.drawable.ic_main_desk_normalcy)
                    }
                } else if (position == 1) {
                    val positionTabView = lqhBottomTab.getPositionTabView(position)
                    if (positionTabView?.customLayout != null) {
                        val ivDesk =
                            positionTabView.customLayout!!.findViewById<ImageView>(R.id.iv_flag)
                        ivDesk.setImageResource(R.drawable.ic_main_index)
                    }
                }
                changeFragment(position, true)
            }

            override fun onTabUnselected(position: Int) {
                LogUtil.e("未选中" + position)
                if (position == 2) {
                    val positionTabView = lqhBottomTab.getPositionTabView(position)
                    if (positionTabView?.customLayout != null) {
                        val ivDesk =
                            positionTabView.customLayout!!.findViewById<ImageView>(R.id.iv_desk)
                        ivDesk.setImageResource(R.drawable.ic_main_desk)
                    }
                } else if (position == 1) {
                    val positionTabView = lqhBottomTab.getPositionTabView(position)
                    if (positionTabView?.customLayout != null) {
                        val ivDesk =
                            positionTabView.customLayout!!.findViewById<ImageView>(R.id.iv_flag)
                        ivDesk.setImageResource(R.drawable.ic_main_index_normalcy)
                    }
                }
            }

            override fun onTabReselected(position: Int) {
                LogUtil.e("重复选中" + position)
            }

        })
        if (currentPosition != 0) {
            lqhBottomTab.setSelectItem(currentPosition)
        } else {
            changeFragment(currentPosition, true)
        }
//        var testFragmentPagerAdapter = TestFragmentPagerAdapter(
//            supportFragmentManager,
//            FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT,
//            fragmentList
//        )
//        viewpager.adapter = testFragmentPagerAdapter
//        lqhBottomTab.setViewPager(viewpager)
//        var testFragmentPagerAdapter2 = TestFragmentStateAdapter(this, fragmentList)
//
//        viewpager2.adapter = testFragmentPagerAdapter2
//        LqhBottomTabMediator(viewpager2, lqhBottomTab).attach()
//        lqhBottomTab.setSelectItem(1)
//        indicator_view?.apply {
//            setIndicatorStyle(IndicatorStyle.CIRCLE)
//            setSlideMode(IndicatorSlideMode.WORM)
//            setSliderWidth(SizeUtils.dp2px(10.0f).toFloat(),SizeUtils.dp2px(10.0f).toFloat())
//            setSliderColor(Color.BLACK,Color.RED)
//            setSliderGap(SizeUtils.dp2px(20.0f).toFloat())
//            setupWithViewPager(viewpager2)
//        }
    }

    private fun initFragment() {
        fragmentList.add(FragmentA.newInstance(titles[0]))
        fragmentList.add(FragmentB.newInstance(titles[1]))
        fragmentList.add(FragmentC.newInstance(titles[2]))
        fragmentList.add(FragmentD.newInstance(titles[3]))
        fragmentList.add(FragmentE.newInstance(titles[4]))
    }

    //动态替换fragment
    private fun changeFragment(showPosition: Int, isforX: Boolean) {
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.apply {
            var fromFragment = fragmentList[currentPosition]
            var toFragment = fragmentList[showPosition]
            if (fromFragment != null && fromFragment != toFragment && fromFragment.isAdded) {
                LogUtil.e("fragment", "添加过")
                hide(fromFragment)
                if (isforX) {
                    setMaxLifecycle(fromFragment, Lifecycle.State.STARTED)
                }
            }
            if (toFragment != null) {
                if (!toFragment.isAdded) {
                    add(R.id.frame_layout, toFragment, TAG_POSITONSTR[showPosition])
                } else {
                    show(toFragment)
                }
                if (isforX) {
                    setMaxLifecycle(toFragment, Lifecycle.State.RESUMED)
                }
            }

        }.commitAllowingStateLoss()
        currentPosition = showPosition
        lqhBottomTab.post {
            LogUtil.e("当前有几个Fragment", "" + supportFragmentManager.fragments.size)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }


    internal class TestFragmentPagerAdapter(
        fm: FragmentManager,
        behavior: Int,
        fragments: MutableList<LqhBaseFragment>?
    ) :
        FragmentPagerAdapter(fm, behavior) {
        private var fragments: MutableList<LqhBaseFragment>? = null
        override fun getItem(position: Int): Fragment {
            return fragments!![position]
        }

        override fun getCount(): Int {
            return fragments?.size ?: 0
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return super.getPageTitle(position)
        }

        init {
            this.fragments = fragments
        }
    }

    internal class TestFragmentStateAdapter(
        fragmentActivity: FragmentActivity,
        fragments: List<BaseLazyFragmentForX>?
    ) :
        FragmentStateAdapter(fragmentActivity) {
        private var fragments: List<BaseLazyFragmentForX>? = null
        override fun createFragment(position: Int): Fragment {
            return fragments!![position]
        }

        override fun getItemCount(): Int {
            return fragments?.size ?: 0
        }

        init {
            this.fragments = fragments
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        //第一种方案防止重建,直接取消的保存的fragment,这种方案不好，因为按home键返回到桌面，即使不会内存补足，也会走这个方法,但是你把他移除了
        //重新进来又没有走oncreate,所以导致界面空白，这种方案不行
//        val beginTransaction = supportFragmentManager.beginTransaction()
//        fragmentList.forEach {
//            beginTransaction.remove(it)
//        }
//        beginTransaction.commitAllowingStateLoss()
//第二种方案
        outState.putInt(TAG_CURPOS, currentPosition);
        super.onSaveInstanceState(outState)
        LogUtil.e("当前activity", "onSaveInstanceState");
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        LogUtil.e("当前activity", "onRestoreInstanceState");
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun initStatusBar() {

    }

    override fun finish() {
        super.finish()
       //overridePendingTransition(R.anim.test_in,R.anim.test_out)
    }

}