package com.sbnh.healer_head_small_program.bean.convert;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.sbnh.healer_head_small_program.utils.DateUtils;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * @Author Liutong
 * @Date 2020/9/23
 */
public class SysTimeGsonSerializer extends TypeAdapter<Long> {

    public static final SysTimeGsonSerializer INSTANCE;

    static {
        INSTANCE = new SysTimeGsonSerializer();
    }

    private SysTimeGsonSerializer() {
    }

    @Override
    public void write(JsonWriter jsonWriter, Long time) throws IOException {
        // 如果 value 没有值，那么不进行序列化
        if (time == null) {
            jsonWriter.nullValue();
        } else {
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            jsonWriter.value(dtf2.format(DateUtils.getDateTimeOfTimestamp(time)));
        }

    }

    @Override
    public Long read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
