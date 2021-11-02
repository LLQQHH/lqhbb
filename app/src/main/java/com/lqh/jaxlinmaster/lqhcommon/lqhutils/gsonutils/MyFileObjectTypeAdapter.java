package com.lqh.jaxlinmaster.lqhcommon.lqhutils.gsonutils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.LinkedTreeMap;
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
 * @describe:用来自己解析某个成员变量是Object导致int变成double的
 */
//@CreateUidAnnotation(uid = "10100")
public class MyFileObjectTypeAdapter extends TypeAdapter<Object> {
    private final TypeAdapter<Object> delegate = new Gson().getAdapter(Object.class);
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
        }else{
            delegate.write(out,value);
        }

    }
}
