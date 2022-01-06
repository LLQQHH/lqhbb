package com.lqh.jaxlinmaster.lqhcommon.lqhutils.gsonutils;

import android.text.TextUtils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtil;
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.sputils.SpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Linqh on 2021/10/29.
 *
 * @describe:
 */
//@CreateUidAnnotation(uid = "10100")
public class GsonUtil {
    private static Gson gson;
    public static <T> T parseJsonObject(String json, Class<T> tClass){
      return parseJsonObject(json,tClass,null);
    }

    /**
     * 把Sting字符串解析成类
     * @param json Json字符串
     * @param tClass 想要解析成的类型的class
     * @param customGson //自定义的Gson
     * @param <T>
     * @return 返回对象
     */
    public static <T> T parseJsonObject(String json, Class<T> tClass,Gson customGson){
        Gson currentGson=getGson();
        if(customGson!=null){
            currentGson=customGson;
        }
        T info = null;
        try {
            info = currentGson.fromJson(json,tClass);
        }catch (Exception e){
            e.printStackTrace();
        }
        return info;
    }
    /**
     *这个一般用在获取一个Json字符串然后通过自带JsonParser解析Json,然后解析某个字段然后再变成类
    */
    public static <T> T parseJsonObject(JsonObject json, Class<T> tClass){
        Gson currentGson=getGson();
        T info = null;
        try {
            info = currentGson.fromJson(json,tClass);
        }catch (Exception e){
            e.printStackTrace();
        }
        return info;
    }

    public static <T> List<T> parseJsonList(String json, Class<T> tInnerClass){
        return parseJsonList(json,tInnerClass);
    }

    /**把json字符串解析成List
     * @param json
     * @param tInnerClass
     * @param customGson
     * @param <T>
     * @return
     */
    public static <T> List<T> parseJsonList(String json, Class<T> tInnerClass,Gson customGson){
        Gson currentGson=getGson();
        if(customGson!=null){
            currentGson=customGson;
        }
        List<T> info = null;
        // 根据T生成List<T>
        Type listType = new ParameterizedTypeImpl(List.class, new Class[]{tInnerClass});
        try {
            info = currentGson.fromJson(json,listType);
        }catch (Exception e){
            e.printStackTrace();
        }
        return info;
    }

    public static <T> List<T> parseJsonList(JsonArray jsonArray,Class<T> tInnerClass){
        try {
            List<T> list = new ArrayList<T>();
            Gson currentGson=getGson();
            for (JsonElement jsonElement : jsonArray) {
                list.add(currentGson.fromJson(jsonElement, tInnerClass));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //当遇到上面解析不了的，就用这个
/*    public static <T> List<T> parseJsonList(String jsonString,Class<T> cls,Gson customGson){
        Gson currentGson=getGson();
        if(customGson!=null){
            currentGson=customGson;
        }
        List<T> list = new ArrayList<T>();
        try {
              这个过期了
            // JsonArray arry = new JsonParser().parse(jsonString).getAsJsonArray();
            JsonParser.parseString(null)会报错,记得判断
            JsonArray arry = JsonParser.parseString(jsonString).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(currentGson.fromJson(jsonElement, cls));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }*/

    public static <V> Map<String, V> parseJsonMap(String json,Class<V> mapClsV){
        return parseJsonMap(json,String.class,mapClsV);
    }
    public static <K, V> Map<K, V> parseJsonMap(String json,Class<K> mapClsK, Class<V> mapClsV){
        return parseJsonMap(json,mapClsK,mapClsV,HashMap.class);
    }
    public static <K, V> Map<K, V> parseJsonMap(String json,Class<K> mapClsK, Class<V> mapClsV,Class<?> mapCls){
        return parseJsonMap(json,mapClsK,mapClsV,mapCls,null);
    }

    /**把Json字符串解析成map
     * @param json
     * @param mapClsK map的key是实际类
     * @param mapClsV map的value是实际类
     * @param mapCls map实际类
     * @param customGson
     * @param <K>
     * @param <V>
     * @return 返回map
     */
    //如果Value是Object类型,解析到对象就直接无效了,对象直接变成LinkTreeMap!
    // 如果遇到json是Map<String, Object> 这样的格式，那只能用最初的JSONObject(Json)存,然后要什么就取什么,但是一般不会遇到
    public static <K, V> Map<K, V> parseJsonMap(String json,Class<K> mapClsK, Class<V> mapClsV,Class<?> mapCls,Gson customGson){
        Gson currentGson=getGson();
        if(customGson!=null){
            currentGson=customGson;
        }
        Map<K,V> info = null;
        try {
            // 根据KV生成HashMapType
            Type hashMapType =new  ParameterizedTypeImpl(mapCls, new Class[]{mapClsK, mapClsV});
            info = currentGson.fromJson(json,hashMapType);
        }catch (Exception e){
            e.printStackTrace();
        }
        return info;
    }


    public static String toJson(Object o){
        return toJson(o,null);
    }

    /**把对象转换成Json字符串，适用于所以类
     * 这个map也适用,Map.toSting 得到的不是Json字符串
     * @param o 需要转换的对象
     * @param customGson
     * @return
     */
    public static String toJson(Object o,Gson customGson){
        Gson currentGson=getGson();
        if(customGson!=null){
            currentGson=customGson;
        }
        return currentGson.toJson(o);
    }
    //这个主要是要通过key,获取内部的数据,只能获取第一层
    /*解析局部数据,可以用JsonParser,或者用自带的JSONObject
     *  举个例子：JsonParser.parseString(null)会报错记得判断
     *    JsonObject jsonParent = JsonParser.parseString(json).getAsJsonObject();
     *                 if(jsonParent!=null){
     *                     JsonElement jsonElement = jsonParent.get("data");
     *                     if(jsonElement instanceof JsonArray){
     *                         List<String> uids = GsonUtil.parseJsonList((JsonArray) jsonElement, String.class);
     *                     }
     *                 }
     * ==========================
     *                  try {
     *                       JSONObject jsonObject = new JSONObject(body);
     *                           if (jsonObject.has("data")){
     *                               JSONObject dataObj = jsonObject.optJSONObject("data");
     *                               if (dataObj!=null&&dataObj.has("planId")){
     *                                   int planId = dataObj.getInt("planId");
     *                               }
     *                       }
     *                   } catch (JSONException e) {
     *                       e.printStackTrace();
     *                   }
    * */

    /** 把json字符串的某个key转换成具体的类
     * @param json  json字符串
     * @param key
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T parseJsonObjectUserInnerKey(String json,String key, Class<T> tClass){
        try {
            JsonObject jsonParent = JsonParser.parseString(json).getAsJsonObject();
            JsonObject jsonChild = jsonParent.getAsJsonObject(key);
           return GsonUtil.parseJsonObject(jsonChild,tClass);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T parseJsonObjectUserInnerKeyOrigin(String json,String key, Class<T> tClass){
        try {
            JSONObject jsonParent=new JSONObject(json);
            JSONObject jsonChild = jsonParent.getJSONObject(key);
            //JSONObject是无法直接转成对象的,所以把jsonChild变成字符串,然后再调用
            return GsonUtil.parseJsonObject(jsonChild.toString(), tClass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**把json字符串的某个key转换成具体的List
     * @param json
     * @param key
     * @param tClass
     * @param <T>
     * @return
     */
    //解析局部数据
    //这个主要是要通过key,获取内部的数据,只能获取第一层
    public static <T> List<T> parseJsonListUserInnerKey(String json,String key, Class<T> tClass){
        try {
            JsonObject jsonParent = JsonParser.parseString(json).getAsJsonObject();
            JsonArray jsonChild = jsonParent.getAsJsonArray(key);
            return GsonUtil.parseJsonList(jsonChild,tClass);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
    //对于上传数据,我们可以把数据放在对象里面,然后用Gson.toJson变成json字符串再上传
    //但是如果对于里层数据不变，外层数据会一直变成，还是把数据放在Map<String,object>里面.上传的时候可以直接Gson.toJson（map),或者new JSONOBJECT(map).toString()变成json字符串

    //对于数据解析,当解析到Akey为动态的Json字符串,如果用Object这样去解析,里面的Object对象会被变成linkTreeMap,若value还是对象,key也会变成linkTreeMap
    //而且发现虽然activity可以传递map,但是即使你传递linkTreeMap转换后被变成HashMap,如果内部字段是map则会变成LinkedHashMap,如果你对map排序不要求,是可以直接做的
    //解决办法：
    //1. 用一个大类去接收,只要服务器加了一个字段，我们这个类就要去加一个字段，就是比较烦拉，但是能解决！界面之间可以传递,如果要求在这个上传的时候添加其他字段，这个字段跟大类同级，那可以用map先添加这个类！
    // 如果新增的其他字段要放在大类里面，要吗直接在大类也把新字段添加，若不想这样只能Gson.toJson（object),然后再JsonParser.parseString(jsonString),变成JsonObject,这样也可以添加其他数据
    // 2.用JSONOBJECT接收那个字段，这个时候能成功,而且还可以自己再添加额外字段,但是用JSONOBJECT没有实现序列化，如果要在界面中传递，这个方案,只能导入新库用fastJson可以实现JSONOBJECT界面传递
    //3.用String接收这个字段（直接接收会Gson会报错,得用一个适配来转换具体参考RawStringJsonAdapter）,变成字符串后，再生成JSONOBJECT，这样就可以添加额外字段，如果要传递数据，可以把JSONOBJECT.toString()赋值给Akey(个人觉得这个方式比较好)

    //对于解析局部数据由两种方法
    //1.自带的官方new JSONObject(),然后通过opt方法获取字段,如果字段是一个对象，可以变成JSONObject，然后toString(),最后用Gson.fromJson
    //2.用GSon的JsonParser.parseString(json),解析成JsonObject,然后通过get方法获取字段,如果字段是一个对象,直接用Gson.fromJson转换！
    /**合并两个Json数据
     * @param srcJsonStr  原来的
     * @param addJsonStr 需要加进去的
     * @return
     */
    public static String combineJson(String srcJsonStr, String addJsonStr)  {
        try {
            JSONObject srcJSONObject=null;
            JSONObject addJSONObject=null;
            if(!TextUtils.isEmpty(srcJsonStr)){
                srcJSONObject = new JSONObject(srcJsonStr);
            }
            if(!TextUtils.isEmpty(addJsonStr)){
                addJSONObject = new JSONObject(addJsonStr);
            }
            if(srcJSONObject!=null&&addJSONObject!=null){
                Iterator<String> itKeys = addJSONObject.keys();
                String key, value;
                while (itKeys.hasNext()) {
                    key = itKeys.next();
                    value = addJSONObject.optString(key);
                    srcJSONObject.put(key, value);
                }
                return srcJSONObject.toString();
            }else if(srcJSONObject!=null){
                return srcJSONObject.toString();
            }else if(addJSONObject!=null){
                return addJSONObject.toString();
            }else{
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**把Json数据合并到map
     * @param json
     * @param otherMap
     */
    public static void parseJsonToOtherMap(String json,Map<String,Object> otherMap){
        if(otherMap!=null&& !TextUtils.isEmpty(json)){
            //这里的map是LinkedTreeMap
            Map<String, Object> map = new Gson().fromJson(json, new TypeToken<Map<String, Object>>() {
            }.getType());
            if(map!=null&&!map.isEmpty()){
                otherMap.putAll(map);
            }
//                for (Map.Entry<String,Object> entry: map.entrySet()){
//                    otherMap.put(entry.getKey(), entry.getValue());//这边如果是Object会变成LinkedTreeMap
//                }
        }
    }
//这个是为了处理数据源本来是int，转换成map后变成double的问题
    public static Gson getGson(){
        if(gson==null){
            GsonBuilder gbuilder = new GsonBuilder();
            // 不导出实体类中没有用@Expose注解的属性,正常不加这个，如果设置这个属性，没有被 @Expose 标注的字段会被排除
            //gbuilder.excludeFieldsWithoutExposeAnnotation();
            // 设置 内置的属性(成员变量) 命名策略 ，其优先级 低于注解@SerializedName的形式
            //gbuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
            // 开启 排除 不进行序列化的 属性,这个是全局的,要自定义ExclusionStrategy,这里自定义GsonExclusionStrategy后要排除序列化的用@GsonExclude标记
            gbuilder.setExclusionStrategies(new GsonExclusionStrategy());
            // 支持Map的key为复杂对象的形式
            gbuilder.enableComplexMapKeySerialization();
            //gbuilder.serializeNulls();//序列化null
            // 格式化date型　　
            gbuilder.setDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            gson = gbuilder.create();
            try {
                Field factories = Gson.class.getDeclaredField("factories");
                factories.setAccessible(true);
                Object o = factories.get(gson);
//            if(o instanceof List){
//                int i = ((List<?>) o).indexOf(ObjectTypeAdapter.FACTORY);
//                if(i!=-1){
//                    List<TypeAdapterFactory> list =(List<TypeAdapterFactory>)o;
//                    因为是Collections$UnmodifiableList类所以无法直接set,直接set会报错
//                    list.set(i,MyObjectTypeAdapter.FACTORY);
//                }
//            }
                //获取内部类信息
//            Class<?>[] declaredClasses = Collections.class.getDeclaredClasses();
//            for (Class c : declaredClasses) {
//                if ("java.util.Collections$UnmodifiableList".equals(c.getName())) {
//                    //找到内部类的UnmodifiableList对应的class
//                    Field listField = c.getDeclaredField("list");
//                    listField.setAccessible(true);
//                    List<TypeAdapterFactory> list = (List<TypeAdapterFactory>) listField.get(o);
//                    int i = list.indexOf(ObjectTypeAdapter.FACTORY);
//                    if(i!=-1){
//                        list.set(i, MyObjectTypeAdapter.FACTORY);
//                    }
//                    break;
//                }
//            }
                Class<?> unmodifiableListClass = Class.forName("java.util.Collections$UnmodifiableList");
                Field listField = unmodifiableListClass.getDeclaredField("list");
                listField.setAccessible(true);
                List<TypeAdapterFactory> list = (List<TypeAdapterFactory>) listField.get(o);
                if(list!=null){
                    int i = list.indexOf(ObjectTypeAdapter.FACTORY);
                    if(i!=-1){
                        list.set(i, MyObjectTypeAdapter.FACTORY);
                    }
                }
                LogUtil.e("getGson替换","成功");
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        return gson;
    }
}
