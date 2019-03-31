package com.neuwljs.wallsmalltwo.util.convert;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

public class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody, T>{

    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    MyGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override public T convert(ResponseBody responseBody) throws IOException{
        //拿到返回的字符串,responseBody.string()只能调用一次
        String result = responseBody.string ();

        //拿到reader
        MediaType mediaType = responseBody.contentType();
        Charset charset = mediaType != null ? mediaType.charset(UTF_8) : UTF_8;
        ByteArrayInputStream bis = new ByteArrayInputStream (result.getBytes());
        InputStreamReader reader = new InputStreamReader (bis,charset);

        // 不是json格式就不做处理
        if(!isJsonFormat (result)){
            responseBody.close ();
            return (T)result;
        }else{
            JsonReader jsonReader = gson.newJsonReader(reader);
            try {
                return adapter.read(jsonReader);
            } finally {
                responseBody.close();
            }
        }
    }

    /**
     * 判断字符串是不是json格式
     * @param value 带判断的字符串
     * @return 是否为json格式
     */
    private boolean isJsonFormat(String value){
        try{
            gson.fromJson(value, Object.class);
            return true;
        }catch (JsonSyntaxException e){
            return false;
        }
    }
}
