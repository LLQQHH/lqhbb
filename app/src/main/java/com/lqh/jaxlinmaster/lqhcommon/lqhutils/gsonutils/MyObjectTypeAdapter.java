package com.lqh.jaxlinmaster.lqhcommon.lqhutils.gsonutils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Linqh on 2021/10/26.
 *
 * @describe:用来替换自带的ObjectTypeAdapter，只能通过反射替换,
 * 如果用来直接注册，只能在gson.registerTypeAdapter(Type type, Object typeAdapter)指定具体的type类
 */
//@CreateUidAnnotation(uid = "10100")
public class MyObjectTypeAdapter extends TypeAdapter<Object> {
    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            return type.getRawType() == Object.class ? (TypeAdapter<T>) new MyObjectTypeAdapter(gson) : null;
        }
    };
    private final Gson gson;

    private MyObjectTypeAdapter(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object read(JsonReader in) throws IOException {
        JsonToken token = in.peek();
        switch (token) {
            case BEGIN_ARRAY:
                List<Object> list = new ArrayList<>();
                in.beginArray();
                while (in.hasNext()) {
                    list.add(read(in));
                }
                in.endArray();
                return list;

            case BEGIN_OBJECT:
                Map<String, Object> map = new LinkedTreeMap<>();
                in.beginObject();
                while (in.hasNext()) {
                    map.put(in.nextName(), read(in));
                }
                in.endObject();
                return map;

            case STRING:
                return in.nextString();

            case NUMBER:
                //将其作为一个字符串读取出来
                String numberStr = in.nextString();
                //返回的numberStr不会为null
                if (numberStr.contains(".") || numberStr.contains("e")
                        || numberStr.contains("E")) {
                    return Double.parseDouble(numberStr);
                }
                return Long.parseLong(numberStr);
//                /**
//                 * 改写数字的处理逻辑，将数字值分为整型与浮点型。
//                 */
//                double dbNum = in.nextDouble();
//
//                // 数字超过long的最大值，返回浮点类型
//                if (dbNum > Long.MAX_VALUE)
//                {
//                    return dbNum;
//                }
//
//                // 判断数字是否为整数值
//                long lngNum = (long) dbNum;
//                if (dbNum == lngNum)
//                {
//                    return lngNum;
//                } else
//                {
//                    return dbNum;
//                }
            case BOOLEAN:
                return in.nextBoolean();

            case NULL:
                in.nextNull();
                return null;

            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public void write(JsonWriter out, Object value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            //noinspection unchecked
            TypeAdapter<Object> typeAdapter = (TypeAdapter<Object>) gson.getAdapter(value.getClass());
            if (typeAdapter instanceof ObjectTypeAdapter) {
                out.beginObject();
                out.endObject();
            } else {
                typeAdapter.write(out, value);
            }
        }
    }

}
