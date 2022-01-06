package com.lqh.jaxlinmaster.lqhtest;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.lqh.jaxlinmaster.R;
import com.lqh.jaxlinmaster.bean.LqhTestBeanNew;
import com.lqh.jaxlinmaster.lqhbase.LqhBaseActivity;
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtil;
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.gsonutils.GsonUtil;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LqhTestJavaActivity extends LqhBaseActivity {
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        Toast mToast = Toast.makeText(this, "显示不同位置", Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.BOTTOM,0,0);
        mToast.setMargin(50,0);
        mToast.show();
        LqhTestBeanNew tempBean= new LqhTestBeanNew("测试");
        tempBean.setSurname("林");
        tempBean.setFamilyAddress("测试家庭地址");
        tempBean.setEmailAddress("123456789@qq.com");
        LogUtil.e("tempBean前", tempBean.toString());
        String toJson = GsonUtil.toJson(tempBean);
        LogUtil.e("tempBean用GsontoJson1", toJson);
        toJson="{\"email\":\"123456789@qq.com\",\"emailAddress\":\"111@qq.com\",\"email_address\":null,\"name\":\"测试\",\"surname\":\"林\",\"familyAddress\":\"测试家庭地址\"}";
        LqhTestBeanNew tempBeanNew = GsonUtil.parseJsonObject(toJson, LqhTestBeanNew.class);
        LogUtil.e("tempBean用GsontoJson2", GsonUtil.toJson(tempBeanNew));
        LogUtil.e("tempBean后", tempBeanNew.toString());
        button5();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_lqh_test_java;
    }
    public static String UrlAppendQueryParameter(String url, Map<String,String> map){
        Uri.Builder builder = Uri.parse(url).buildUpon();
        if(map!=null&&map.size()>0){
            Set<Map.Entry<String, String>> entries = map.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                String key = entry.getKey();
                String value = entry.getValue();
                if(!TextUtils.isEmpty(key)){
                    builder.appendQueryParameter(key, value);
                }
            }
            return builder.build().toString();
        }
        return url;
    }
    public static HashMap<String, String> form2Map(String mapStr) {
        String listinfo[];
        HashMap<String, String> map = new HashMap<String, String>();
        if(TextUtils.isEmpty(mapStr)){
            return map;
        }
        if(mapStr.startsWith("?")){
            mapStr=mapStr.substring(1);
        }
        listinfo = mapStr.split("&");
        for(String s : listinfo)
        {
            String list[]  = s.split("=");
            if(list.length>1)
            {
                map.put(list[0], list[1]);
            }

        }
        return map;
    }
    public static String UrlAddQueryParameter(String url,Map<String,String> map){

        if(!TextUtils.isEmpty(url)&&map!=null&&map.size()>0){
            Uri uri = Uri.parse(url);
            HashMap<String, String>  existMap= form2Map(uri.getQuery());

            Set<Map.Entry<String, String>> entries = map.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                String key = entry.getKey();
                String value = entry.getValue();
                if(!TextUtils.isEmpty(key)){
                    existMap.put(key,value);
                }
            }
            Uri.Builder builder = uri.buildUpon().clearQuery();
            Set<Map.Entry<String, String>> entriesNew = existMap.entrySet();
            for (Map.Entry<String, String> entry : entriesNew) {
                String key = entry.getKey();
                String value = entry.getValue();
                if(!TextUtils.isEmpty(key)){
                    builder.appendQueryParameter(key,value);
                }
            }
            return builder.build().toString();
        }
        return url;
    }
    private void button5() {
        String uriStr = "http://www.zpan.com:8080/lujing/path.htm?id=10&name=zhangsan&old=24#zuihoude";
        Map<String,String> map=new HashMap<>();
        map.put("id","500");
        map.put("name",null);
        map.put("age","12");
        Log.e("zpan覆盖1",UrlAppendQueryParameter(uriStr,map));
        Log.e("zpan覆盖2",UrlAddQueryParameter(uriStr,map));
        Uri mUri = Uri.parse(uriStr);
        if(mUri.isOpaque()){
            Log.e("zpan", "链接存在问题");
            return;
        }else{
            Log.e("zpan", "链接正常");
        }
        String uriStrGet = mUri.toString();
        // 协议
        String scheme = mUri.getScheme();
        // 域名+端口号+路径+参数
        String scheme_specific_part = mUri.getSchemeSpecificPart();
        // 域名+端口号
        String authority = mUri.getAuthority();
        // 域名
        String host = mUri.getHost();
        // 端口号
        int port = mUri.getPort();
        // 路径
        String path = mUri.getPath();
        // 参数
        String query = mUri.getQuery();
        // fragment
        String fragment = mUri.getFragment();

        Log.e("zpan", "======完整地址===uriString ==" + uriStrGet);
        Log.e("zpan", "======协议===scheme ==" + scheme);
        Log.e("zpan", "======域名+端口号+路径+参数==scheme_specific_part ===" + scheme_specific_part);
        Log.e("zpan", "======域名+端口号===authority ==" + authority);
        Log.e("zpan", "======fragment===fragment ==" + fragment);
        Log.e("zpan", "======域名===host ==" + host);
        Log.e("zpan", "======端口号===port ==" + port);
        Log.e("zpan", "======路径===path ==" + path);
        Log.e("zpan", "======参数===query ==" + query);

        // 依次提取出Path的各个部分的字符串，以字符串数组的形式输出
        List<String> pathSegments = mUri.getPathSegments();
        for (String str : pathSegments) {
            Log.e("zpan", "======路径拆分====path ==" + str);
        }
        //第一种获得请求参数的方法
        if(!TextUtils.isEmpty(query)){
            Log.e("zpan","======query方式一====");
            String[] arr = query.split("&");
            for (String s : arr) {
                String key = s.split("=")[0];
                String value = s.split("=")[1];

                Log.e("zpan","=key= " + key + " =value= " + value);
            }
        }
        // 第二种获得请求参数的方法
        Set<String> queryKeys = mUri.getQueryParameterNames();
        Log.e("zpan","======query方式二====");
        for(String queryKey: queryKeys) {
            String queryValue = mUri.getQueryParameter(queryKey);
            Log.e("zpan","=key= " + queryKey + " =value= " + queryValue);
        }
    }
}