package com.lqh.jaxlinmaster.lqhtest;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.RegexUtil;
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.gsonutils.MyObjectTypeAdapter;
import com.lqh.jaxlinmaster.lqhcommon.lqhutils.gsonutils.RawStringJsonAdapter;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
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
        HashMap<String, Object> testMap = new HashMap<>();
        testMap.put("Rate", 1.0f);
        Data.Extend extend = new Data.Extend();
        extend.setNumber(30);
        extend.setAmount(120.3);
        testMap.put("extend", extend);
        String s = new Gson().toJson(testMap);
        System.out.println("Gson().toJson(Map):" + s);
//        String s = testMap.toString();
        String dataJson = "{\"Rate\" : 1.0, \"extend\" : {\"number\" : 30, \"amount\" : 120.3}}";
        Gson gson = new Gson();
        Data data = gson.fromJson(dataJson, Data.class);
//        Gson gsontest = new GsonBuilder().registerTypeAdapter(new TypeToken<Map<String, Object>>(){}.getType(),new MyMapDeserializerDoubleAsIntFix()).create();
//        HashMap map = gsontest.fromJson(dataJson, new TypeToken<Map<String, Object>>(){}.getType());
        System.out.println(data.toString());
//        String mapStr = map.toString();
//        System.out.println(mapStr);
//        System.out.println("map to Gson"+getGson().toJson(map));
//        String dataListJson = "[1,30,120]";
//        List list = gson.fromJson(dataListJson, List.class);
//        System.out.println(list);
//        Data dataUserJSON = JSON.parseObject(dataJson, Data.class);
//        System.out.println(dataUserJSON);
//        String s = JSON.toJSONString(dataUserJSON);
//        System.out.println(s);
    }

    public static class Data implements Serializable {

        private Double Rate;

        //private Map extend;
        @JsonAdapter(RawStringJsonAdapter.class)
        private String extend;


        public Double getRate() {
            return Rate;
        }

        public void setRate(Double rate) {
            Rate = rate;
        }

        public String getExtend() {
            return extend;
        }

        public void setExtend(String extend) {
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
