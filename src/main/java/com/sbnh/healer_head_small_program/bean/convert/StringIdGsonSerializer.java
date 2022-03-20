package com.sbnh.healer_head_small_program.bean.convert;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @Author Liutong
 * @Date 2020/9/23
 */
public class StringIdGsonSerializer extends TypeAdapter<String> {

    public static final StringIdGsonSerializer INSTANCE;

    static {
        INSTANCE = new StringIdGsonSerializer();
    }

    private StringIdGsonSerializer() {
    }

    @Override
    public void write(JsonWriter jsonWriter, String id) throws IOException {
        // 如果 value 没有值，那么不进行序列化
        if (id == null) {
            jsonWriter.nullValue();
            // 如果 value 的值为 0，那么输出0，不输出默认的 0.0
        } else {
            jsonWriter.value(id.toString());
        }

    }

    @Override
    public String read(JsonReader jsonReader) throws IOException {
        return new String(jsonReader.nextString());
    }
}
