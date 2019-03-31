package com.neuwljs.wallsmalltwo.common;

import java.util.HashMap;
import java.util.Map;

public class ApiService {

    /**
     * okhttp连接超时时间
     */
    public static final long OKHTTP_CONNECT_TIME_OUT = 5;

    /**
     * okhttp head 的name
     */
    public static final String OKHTTP_HEAD_NAME = "okhttp_head_name";

    /**
     * 获取天气信息api的head的key
     */
    public static final String WEATHER_HEAD_KEY="weather";

    /**
     * 获取天气信息api的baseUrl
     */
    public static final String WEATHER_BASE_URL = "https://free-api.heweather.net/";

    /**
     * 请求头和baseUrl的映射集合
     */
    public static final Map<String, String> OKHTTP_HEAD_MAP;

    static {
        OKHTTP_HEAD_MAP = new HashMap<> ();
        OKHTTP_HEAD_MAP.put (WEATHER_HEAD_KEY, WEATHER_BASE_URL);
    }

    /**
     * 请求网络的接口
     */
    public interface Api{

    }
}
