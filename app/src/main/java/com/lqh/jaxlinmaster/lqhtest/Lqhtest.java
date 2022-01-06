package com.lqh.jaxlinmaster.lqhtest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.LogUtil;
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.RegexUtil;
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.gsonutils.GsonUtil;
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.gsonutils.MyMapDeserializerDoubleAsIntFix;
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.gsonutils.MyObjectTypeAdapter;
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.gsonutils.RawStringJsonAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Linqh on 2021/7/27.
 *
 * @describe:
 */
//@CreateUidAnnotation(uid = "10100")
public class Lqhtest {
    public static void main(String[] args) {
        System.out.println("0.0是不是float:" + RegexUtil.isFloat("0.0"));
        String dataJson = "{\"Rate\" : 1.0, \"extend\" : {\"number\" : 30, \"amount\" : 120.3}}";
        Data data = GsonUtil.parseJsonObject(dataJson, Data.class);
        System.out.println("Data:" + data.toString());
        Map<String, Object> stringObjectMap = GsonUtil.parseJsonMap(dataJson,String.class, Object.class,Map.class);
        System.out.println("stringObjectMap:" + stringObjectMap.toString());

//        Gson gson = new Gson();
//        HashMap<String, Object> testMap = new HashMap<>();
//        testMap.put("Rate", 1);
//        Data.Extend extend = new Data.Extend();
//        extend.setNumber(30);
//        extend.setAmount(120.3);
//        testMap.put("extend", extend);
//        String s = gson.toJson(testMap);
//        System.out.println("Gson().toJson(Map):" + s);


//        Data dataUserJSON = JSON.parseObject(dataJson, Data.class);
//        System.out.println(dataUserJSON);
//        String s = JSON.toJSONString(dataUserJSON);
//        System.out.println(s);
    }
    public static class DataSingle implements Serializable {
        private Double Rate;
        private int age;
        private Object name;

        public Double getRate() {
            return Rate;
        }

        public void setRate(Double rate) {
            Rate = rate;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }
    }

    public static class Data implements Serializable {

        private Double Rate;

        //private Map extend;
        //@JsonAdapter(RawStringJsonAdapter.class)
        private Object extend;


        public Double getRate() {
            return Rate;
        }

        public void setRate(Double rate) {
            Rate = rate;
        }

        public Object getExtend() {
            return extend;
        }

        public void setExtend(Object extend) {
            this.extend = extend;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "Rate=" + Rate +
                    ", extend=" + extend +
                    '}';
        }

        public static class Extend implements Serializable {
            private int number;
            private double amount;

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            @Override
            public String toString() {
                return "Extend{" +
                        "number=" + number +
                        ", amount=" + amount +
                        '}';
            }
        }
    }

    public static Gson getGson() {
        Gson gson = new GsonBuilder().create();
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
            if (list != null) {
                int i = list.indexOf(ObjectTypeAdapter.FACTORY);
                if (i != -1) {
                    list.set(i, MyObjectTypeAdapter.FACTORY);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gson;

    }

}
