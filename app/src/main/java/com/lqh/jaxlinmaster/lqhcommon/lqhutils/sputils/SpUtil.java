package com.lqh.jaxlinmaster.lqhcommon.lqhutils.sputils;

/**
 * Created by Linqh on 2021/10/21.
 *
 * @describe:
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Lqh on 2021/10/25.
 * SharedPreferences工具类
 * 保存的数据类型有：
 * 1、四种基本类型数据（int，float，long，boolean）+ String
 * 2、List类型的数据
 * 3、存储序列化对象
 * 4、存储MHashMap<K, V>集合
 */
public class SpUtil {
    //储存多个SpUtil,为了能实现多个位置存储
    private static final Map<String, SpUtil> SP_UTILS_MAP = new HashMap<>();
    private static String SP_NAME_DEFAULT = "SpUtil_name_default";
    private static String TAG = "SpUtil";
    private SharedPreferences sp;
    private Gson gson;
    //保存时间单位
    public static final int TIME_SECOND = 1;
    public static final int TIME_MINUTES = 60 * TIME_SECOND;
    public static final int TIME_HOUR = 60 * TIME_MINUTES;
    public static final int TIME_DAY = TIME_HOUR * 24;
    public static final int TIME_WEEK = TIME_DAY * 7;
    public static final int TIME_MONTH = TIME_DAY * 30;
    public static final int DURATION_UNIT = 1000;
    public static final int TIME_MAX = Integer.MAX_VALUE; // 不限制存放数据的数量


    private SpUtil(Context context, String spName, int mode) {
        sp = context.getSharedPreferences(spName, mode);
    }

    public static SpUtil getInstance(Context context) {
        return getInstance(context, "", Context.MODE_PRIVATE);
    }

    public static SpUtil getInstance(Context context, String spName) {
        return getInstance(context, spName, Context.MODE_PRIVATE);
    }

    public static SpUtil getInstance(Context context, String spName, final int mode) {
        if (TextUtils.isEmpty(spName)) {
            spName = SP_NAME_DEFAULT;
        }
        SpUtil spUtil = SP_UTILS_MAP.get(spName);
        if (spUtil == null) {
            synchronized (SpUtil.class) {
                spUtil = SP_UTILS_MAP.get(spName);
                if (spUtil == null) {
                    spUtil = new SpUtil(context, spName, mode);
                }
            }
        }
        return spUtil;
    }

    private Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public boolean isDuringSaveTime(long saveCurrentTime, int saveTime) {
        return (System.currentTimeMillis() - saveCurrentTime) / DURATION_UNIT <= saveTime;
    }


    public void putString(@NonNull String key, String value) {
        putString(key, value, TIME_MAX);
    }

    public void putString(@NonNull String key, String value, int saveTime) {
        putString(key, value, saveTime, true);
    }

    public void putString(@NonNull String key, String value, int saveTime, boolean isCommit) {
        String jsonValue = null;
        if (!TextUtils.isEmpty(value)) {
            SpSaveModel<String> spSaveModel = new SpSaveModel<>(saveTime, value, System.currentTimeMillis());
            jsonValue = getGson().toJson(spSaveModel);
        }
        if (isCommit) {
            sp.edit().putString(key, jsonValue).commit();
        } else {
            sp.edit().putString(key, jsonValue).apply();
        }
    }

    public String getString(@NonNull final String key) {
        return getString(key, "");
    }

    public String getString(@NonNull final String key, final String defaultValue) {
        String jsonStr = sp.getString(key, "");
        if (!TextUtils.isEmpty(jsonStr)) {
            SpSaveModel<String> spSaveModel = getGson().fromJson(jsonStr, new TypeToken<SpSaveModel<String>>() {
            }.getType());
            if (isDuringSaveTime(spSaveModel.getCurrentTime(), spSaveModel.getSaveTime())) {
                return spSaveModel.getValue();
            } else {
                //超过缓存时间就把他删除
                remove(key, true);
            }
        }
        return defaultValue;
    }

    public void putInt(@NonNull String key, int value) {
        putInt(key, value, TIME_MAX);
    }

    public void putInt(@NonNull String key, int value, int saveTime) {
        putInt(key, value, saveTime, true);
    }


    public void putInt(@NonNull String key, int value, int saveTime, boolean isCommit) {
        SpSaveModel<Integer> spSaveModel = new SpSaveModel<>(saveTime, value, System.currentTimeMillis());
        String jsonValue = getGson().toJson(spSaveModel);
        if (isCommit) {
            sp.edit().putString(key, jsonValue).commit();
        } else {
            sp.edit().putString(key, jsonValue).apply();
        }
    }

    public int getInt(@NonNull final String key) {
        return getInt(key, -1);
    }

    public int getInt(@NonNull final String key, final int defaultValue) {
        String jsonStr = sp.getString(key, "");
        if (!TextUtils.isEmpty(jsonStr)) {
            SpSaveModel<Integer> spSaveModel = getGson().fromJson(jsonStr, new TypeToken<SpSaveModel<Integer>>() {
            }.getType());
            if (isDuringSaveTime(spSaveModel.getCurrentTime(), spSaveModel.getSaveTime())) {
                return spSaveModel.getValue();
            } else {
                remove(key, true);
            }
        }
        return defaultValue;
    }


    public void putLong(@NonNull String key, long value) {
        putLong(key, value, TIME_MAX);
    }

    public void putLong(@NonNull String key, long value, int saveTime) {
        putLong(key, value, saveTime, true);
    }

    public void putLong(@NonNull String key, long value, int saveTime, boolean isCommit) {
        SpSaveModel<Long> spSaveModel = new SpSaveModel<>(saveTime, value, System.currentTimeMillis());
        String jsonValue = getGson().toJson(spSaveModel);
        if (isCommit) {
            sp.edit().putString(key, jsonValue).commit();
        } else {
            sp.edit().putString(key, jsonValue).apply();
        }
    }

    public long getLong(@NonNull final String key) {
        return getLong(key, -1L);
    }

    public long getLong(@NonNull final String key, final long defaultValue) {
        String jsonStr = sp.getString(key, "");
        if (!TextUtils.isEmpty(jsonStr)) {
            SpSaveModel<Long> spSaveModel = getGson().fromJson(jsonStr, new TypeToken<SpSaveModel<Long>>() {
            }.getType());
            if (isDuringSaveTime(spSaveModel.getCurrentTime(), spSaveModel.getSaveTime())) {
                return spSaveModel.getValue();
            } else {
                remove(key, true);
            }
        }
        return defaultValue;
    }

    public void putFloat(@NonNull String key, float value) {
        putFloat(key, value, TIME_MAX);
    }

    public void putFloat(@NonNull String key, float value, int saveTime) {
        putFloat(key, value, saveTime, true);
    }

    public void putFloat(@NonNull String key, float value, int saveTime, boolean isCommit) {
        SpSaveModel<Float> spSaveModel = new SpSaveModel<>(saveTime, value, System.currentTimeMillis());
        String jsonValue = getGson().toJson(spSaveModel);
        if (isCommit) {
            sp.edit().putString(key, jsonValue).commit();
        } else {
            sp.edit().putString(key, jsonValue).apply();
        }
    }

    public float getFloat(@NonNull final String key) {
        return getFloat(key, -1f);
    }

    public float getFloat(@NonNull final String key, final float defaultValue) {
        String jsonStr = sp.getString(key, "");
        if (!TextUtils.isEmpty(jsonStr)) {
            SpSaveModel<Float> spSaveModel = getGson().fromJson(jsonStr, new TypeToken<SpSaveModel<Float>>() {
            }.getType());
            if (isDuringSaveTime(spSaveModel.getCurrentTime(), spSaveModel.getSaveTime())) {
                return spSaveModel.getValue();
            } else {
                remove(key, true);
            }
        }
        return defaultValue;
    }

    public void putBoolean(@NonNull String key, boolean value) {
        putBoolean(key, value, TIME_MAX);
    }

    public void putBoolean(@NonNull String key, boolean value, int saveTime) {
        putBoolean(key, value, saveTime, true);
    }

    public void putBoolean(@NonNull String key, boolean value, int saveTime, boolean isCommit) {
        SpSaveModel<Boolean> spSaveModel = new SpSaveModel<>(saveTime, value, System.currentTimeMillis());
        String jsonValue = getGson().toJson(spSaveModel);
        if (isCommit) {
            sp.edit().putString(key, jsonValue).commit();
        } else {
            sp.edit().putString(key, jsonValue).apply();
        }
    }

    public boolean getBoolean(@NonNull final String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(@NonNull final String key, final boolean defaultValue) {
        String jsonStr = sp.getString(key, "");
        if (!TextUtils.isEmpty(jsonStr)) {
            SpSaveModel<Boolean> spSaveModel = getGson().fromJson(jsonStr, new TypeToken<SpSaveModel<Boolean>>() {
            }.getType());
            if (isDuringSaveTime(spSaveModel.getCurrentTime(), spSaveModel.getSaveTime())) {
                return spSaveModel.getValue();
            } else {
                remove(key, true);
            }
        }
        return defaultValue;
    }

    public void putStringSet(@NonNull String key, Set<String> value) {
        putStringSet(key, value, TIME_MAX);
    }

    public void putStringSet(@NonNull String key, Set<String> value, int saveTime) {
        putStringSet(key, value, TIME_MAX, true);
    }

    public void putStringSet(@NonNull String key, Set<String> value, int saveTime, boolean isCommit) {
        SpSaveModel<Set<String>> spSaveModel = new SpSaveModel<>(saveTime, value, System.currentTimeMillis());
        String jsonValue = getGson().toJson(spSaveModel);
        if (isCommit) {
            sp.edit().putString(key, jsonValue).commit();
        } else {
            sp.edit().putString(key, jsonValue).apply();
        }
    }

    public Set<String> getStringSet(@NonNull final String key) {
        return getStringSet(key, Collections.<String>emptySet());
    }

    public Set<String> getStringSet(@NonNull final String key,
                                    final Set<String> defaultValue) {
        String jsonStr = sp.getString(key, "");
        if (!TextUtils.isEmpty(jsonStr)) {
            SpSaveModel<Set<String>> spSaveModel = getGson().fromJson(jsonStr, new TypeToken<SpSaveModel<Set<String>>>() {
            }.getType());
            if (isDuringSaveTime(spSaveModel.getCurrentTime(), spSaveModel.getSaveTime())) {
                return spSaveModel.getValue();
            } else {
                remove(key, true);
            }
        }
        return defaultValue;
    }


    public void putObjectUseGson(String key, Serializable object) {
        putObjectUseGson(key, object, TIME_MAX);
    }

    public void putObjectUseGson(String key, Serializable object, int saveTime) {
        putObjectUseGson(key, object, saveTime, true);
    }

    public void putObjectUseGson(String key, Serializable value, int saveTime, boolean isCommit) {
        String jsonValue = null;
        if (value != null) {
            SpSaveModel<Serializable> spSaveModel = new SpSaveModel<>(saveTime, value, System.currentTimeMillis());
            jsonValue = getGson().toJson(spSaveModel);
        }
        if (isCommit) {
            sp.edit().putString(key, jsonValue).commit();
        } else {
            sp.edit().putString(key, jsonValue).apply();
        }
    }

    public <T extends Serializable> T getObjectUseGson(String key, Class<T> tClass) {
        String jsonStr = sp.getString(key, "");
        if (!TextUtils.isEmpty(jsonStr)) {
            Type SpSaveModelType = new ParameterizedTypeImpl(SpSaveModel.class, new Class[]{tClass});
            try {
                SpSaveModel<T> spSaveModel = getGson().fromJson(jsonStr, SpSaveModelType);
                if (isDuringSaveTime(spSaveModel.getCurrentTime(), spSaveModel.getSaveTime())) {
                    return spSaveModel.getValue();
                } else {
                    remove(key, true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }


    public void putDataList(String key, List dataList) {
        putDataList(key, dataList, TIME_MAX);
    }

    public void putDataList(String key, List dataList, int saveTime) {
        putDataList(key, dataList, saveTime, true);
    }

    public void putDataList(String key, List dataList, int saveTime, boolean isCommit) {
        String jsonValue = null;
        if (dataList != null) {
            SpSaveModel<List> spSaveModel = new SpSaveModel<>(saveTime, dataList, System.currentTimeMillis());
            jsonValue = getGson().toJson(spSaveModel);
        }
        if (isCommit) {
            sp.edit().putString(key, jsonValue).commit();
        } else {
            sp.edit().putString(key, jsonValue).apply();
        }
    }

    public <T> List<T> getDataList(String key, Class<T> tClass, List defaultDataList) {
        String jsonStr = sp.getString(key, "");
        if (!TextUtils.isEmpty(jsonStr)) {
            // 根据T生成List<T>
            Type listType = new ParameterizedTypeImpl(List.class, new Class[]{tClass});
            // 根据List<T>SpSaveModel<List<T>>
            Type SpSaveModelType = new ParameterizedTypeImpl(SpSaveModel.class, new Type[]{listType});
            try {
                SpSaveModel<List<T>> spSaveModel = getGson().fromJson(jsonStr, SpSaveModelType);
                if (isDuringSaveTime(spSaveModel.getCurrentTime(), spSaveModel.getSaveTime())) {
                    return spSaveModel.getValue();
                } else {
                    remove(key, true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defaultDataList;
    }

    public void putHashMapData(String key, Map map) {
        putHashMapData(key, map, TIME_MAX);
    }

    public void putHashMapData(String key, Map map, int saveTime) {
        putHashMapData(key, map, saveTime, true);
    }

    public void putHashMapData(String key, Map map, int saveTime, boolean isCommit) {
        String jsonValue = null;
        if (map != null) {
            SpSaveModel<Map> spSaveModel = new SpSaveModel<>(saveTime, map, System.currentTimeMillis());
            jsonValue = getGson().toJson(spSaveModel);
        }
        if (isCommit) {
            sp.edit().putString(key, jsonValue).commit();
        } else {
            sp.edit().putString(key, jsonValue).apply();
        }
    }

    public <K, V> Map<K, V> getHashMapData(String key, Class<K> mapClsK, Class<V> mapClsV, Map defaultDataMap) {
        return getHashMapData(key, mapClsK, mapClsV, defaultDataMap, HashMap.class);
    }

    public <K, V> Map<K, V> getHashMapData(String key, Class<K> mapClsK, Class<V> mapClsV, Map defaultDataMap, Class mapClsT) {
        String jsonStr = sp.getString(key, "");
        if (!TextUtils.isEmpty(jsonStr)) {
            // 根据KV生成HashMapType
            Type hashMapType = new ParameterizedTypeImpl(mapClsT, new Class[]{mapClsK, mapClsV});
            // 根据HashMap<K,V>SpSaveModel<HashMap<K,V>>
            Type SpSaveModelType = new ParameterizedTypeImpl(SpSaveModel.class, new Type[]{hashMapType});
            try {
                SpSaveModel<Map<K, V>> spSaveModel = getGson().fromJson(jsonStr, SpSaveModelType);
                if (isDuringSaveTime(spSaveModel.getCurrentTime(), spSaveModel.getSaveTime())) {
                    return spSaveModel.getValue();
                } else {
                    remove(key, true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return defaultDataMap;
    }


    public Map<String, ?> getAll() {
        return sp.getAll();
    }

    public boolean contains(@NonNull final String key) {
        return sp.contains(key);
    }

    public void remove(@NonNull final String key) {
        remove(key, false);
    }


    public void remove(@NonNull final String key, final boolean isCommit) {
        if (isCommit) {
            sp.edit().remove(key).commit();
        } else {
            sp.edit().remove(key).apply();
        }
    }

    public void clear() {
        clear(false);
    }

    public void clear(final boolean isCommit) {
        if (isCommit) {
            sp.edit().clear().commit();
        } else {
            sp.edit().clear().apply();
        }
    }


//    /**
//     * 获取SharedPreference保存的value
//     * 值的类型：（int，float，long，boolean）+String
//     *
//     * @param context 上下文
//     * @param key     储存值的key
//     * @param clazz   获取值的类型   int，float，long，boolean）+String
//     * @param <T>     T
//     * @return 值
//     */
//    public static <T> T getValue(Context context, String key, Class<T> clazz) {
//        if (context == null) {
//            throw new RuntimeException("context is null");
//        }
//
//        SharedPreferences sp = context.getSharedPreferences(SP_NAME_DEFAULT, Context.MODE_PRIVATE);
//        return getValue(key, getT(clazz), sp);
//    }
//
//    /**
//     * 获取SharedPreference保存的value
//     * 值的类型：（int，float，long，boolean）+String
//     *
//     * @param key 储存值的key
//     * @param t   获取值的类型   int，float，long，boolean）+String
//     * @param sp  SharedPreferences
//     * @param <T> T
//     * @return 值
//     */
//    private static <T> T getValue(String key, T t, SharedPreferences sp) {
//        if (t == null) {
//            return null;
//        }
//        if (t instanceof Integer) {
//            return (T) Integer.valueOf(sp.getInt(key, 0));
//        } else if (t instanceof String) {
//            return (T) sp.getString(key, "");
//        } else if (t instanceof Boolean) {
//            return (T) Boolean.valueOf(sp.getBoolean(key, false));
//        } else if (t instanceof Long) {
//            return (T) Long.valueOf(sp.getLong(key, 0L));
//        } else if (t instanceof Float) {
//            return (T) Float.valueOf(sp.getFloat(key, 0L));
//        }
//        Log.e(TAG, "无法找到" + key + "对应的值");
//        return null;
//    }
//
//    /**
//     * 通过反射创建类实例
//     * 反射有两种方式创建类实例：
//     * 1、Class.newInstance()：只能调用无参的构造函数（默认构造函数）
//     * 2、Constructor.newInstance()：可以根据传入的参数，调用任意构造构造函数。
//     * Integer、Boolean、Long、Float 没有默认构造函数，只能通过 Constructor.newInstance() 调用
//     * String 是有默认构造函数的，两种方法都适用
//     *
//     * @param clazz String.class、Integer.class、Boolean.class、Long.class、Float.class
//     * @param <T>   T
//     * @return T
//     */
//    private static <T> T getT(Class<T> clazz) {
//        T t = null;
//        String clazzName = clazz.getName();
//        Log.e(TAG, "基本类型名字是[" + clazzName + "]");
//        try {
//            if ("java.lang.Integer".equals(clazzName)) {
//                t = clazz.getConstructor(int.class).newInstance(1);
//            } else if ("java.lang.String".equals(clazzName)) {
//                t = clazz.newInstance();
//            } else if ("java.lang.Boolean".equals(clazzName)) {
//                t = clazz.getConstructor(boolean.class).newInstance(false);
//            } else if ("java.lang.Long".equals(clazzName)) {
//                t = clazz.getConstructor(long.class).newInstance(0L);
//            } else if ("java.lang.Float".equals(clazzName)) {
//                t = clazz.getConstructor(float.class).newInstance(0F);
//            }
//        } catch (InstantiationException e) {
//            Log.e(TAG, "类型输入错误或者复杂类型无法解析[" + e.getMessage() + "]");
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            Log.e(TAG, "类型输入错误或者复杂类型无法解析[" + e.getMessage() + "]");
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            Log.e(TAG, "类型输入错误或者复杂类型无法解析[" + e.getMessage() + "]");
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            Log.e(TAG, "类型输入错误或者复杂类型无法解析[" + e.getMessage() + "]");
//            e.printStackTrace();
//        }
//
//        return t;
//    }

//    /**
//     *
//     * 使用SharedPreference保存序列化对象
//     * 用Base64.encode将字节文件转换成Base64编码保存在String中
//     *
//     * @param context 上下文
//     * @param key     储存对象的key
//     * @param object  object对象  对象必须实现Serializable序列化，否则会出问题，
//     *                out.writeObject 无法写入 Parcelable 序列化的对象
//     */
//    public static void putObject(Context context, String key, Object object) {
//        SharedPreferences sp = context.getSharedPreferences(SP_NAME_DEFAULT, Context.MODE_PRIVATE);
//        //创建字节输出流
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        //创建字节对象输出流
//        ObjectOutputStream out = null;
//        try {
//            //然后通过将字对象进行64转码，写入sp中
//            out = new ObjectOutputStream(baos);
//            out.writeObject(object);
//            String objectValue = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
//            SharedPreferences.Editor editor = sp.edit();
//            editor.putString(key, objectValue);
//            editor.commit();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (baos != null) {
//                    baos.close();
//                }
//                if (out != null) {
//                    out.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//
//    /**
//     * 获取SharedPreference保存的对象
//     * 使用Base64解密String，返回Object对象
//     *
//     * @param context 上下文
//     * @param key     储存对象的key
//     * @param <T>     泛型
//     * @return 返回保存的对象
//     */
//    public static <T> T getObject(Context context, String key) {
//        SharedPreferences sp = context.getSharedPreferences(SP_NAME_DEFAULT, Context.MODE_PRIVATE);
//        if (sp.contains(key)) {
//            String objectValue = sp.getString(key, null);
//            if (TextUtils.isEmpty(objectValue)) {
//                return null;
//            }
//            byte[] buffer = Base64.decode(objectValue, Base64.DEFAULT);
//            //一样通过读取字节流，创建字节流输入流，写入对象并作强制转换
//            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
//            ObjectInputStream ois = null;
//            try {
//                ois = new ObjectInputStream(bais);
//                T t = (T) ois.readObject();
//                return t;
//            } catch (StreamCorruptedException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (bais != null) {
//                        bais.close();
//                    }
//                    if (ois != null) {
//                        ois.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return null;
//    }

    public static class ParameterizedTypeImpl implements ParameterizedType {
        private final Class raw;
        private final Type[] args;

        public ParameterizedTypeImpl(Class raw, Type[] args) {
            this.raw = raw;
            this.args = args != null ? args : new Type[0];
        }

        //返回Map<String,User>里的String和User，所以这里返回[String.class,User.clas]
        @Override
        public Type[] getActualTypeArguments() {
            return args;
        }

        // Map<String,User>里的Map,所以返回值是Map.class
        @Override
        public Type getRawType() {
            return raw;
        }

        //用于这个泛型上中包含了内部类的情况,一般返回null
        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}

