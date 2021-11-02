package com.lqh.jaxlinmaster.lqhcommon.lqhutils.gsonutils;

import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by Linqh on 2020/8/31.
 *
 * @describe:
 */
//@CreateUidAnnotation(uid = "10100")
public class RawStringJsonAdapter extends TypeAdapter<String> {

    @Override
    public void write(JsonWriter out, String value) throws IOException {
        out.jsonValue(value);
    }

    @Override
    public String read(JsonReader in) throws IOException {
        return new JsonParser().parse(in).toString();
    }
}
