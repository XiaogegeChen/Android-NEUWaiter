package com.neuwljs.wallsmalltwo.util;

import com.google.gson.JsonParseException;
import com.neuwljs.wallsmalltwo.model.MyException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

public class ExceptionUtil {
    /**
     * 未知异常
     */
    public static final String UNKNOWN_EXCEPTION = "未知错误";

    /**
     * 网络异常
     */
    private static final String NETWORK_EXCEPTION = "网络异常";

    /**
     * 数据解析异常
     */
    private static final String PARSE_EXCEPTION = "数据解析错误";

    /**
     * 服务器异常
     */
    private static final String SERVER_EXCEPTION = "服务器错误";

    /**
     * 把一个exception转化成MyException,方便统一处理
     * @param e 异常
     * @return MyException
     */
    public static MyException transfer(Throwable e){
        MyException me;

        if(e instanceof JSONException || e instanceof JsonParseException || e instanceof ParseException){
            me = new MyException (PARSE_EXCEPTION, e.getMessage ());
        }else if(e instanceof ConnectException){
            me = new MyException (NETWORK_EXCEPTION, e.getMessage ());
        }else if(e instanceof UnknownHostException || e instanceof SocketTimeoutException){
            me = new MyException (NETWORK_EXCEPTION, e.getMessage ());
        }else if(e instanceof MyException){
            me = new MyException (SERVER_EXCEPTION, e.getMessage ());
        }else{
            me = new MyException (UNKNOWN_EXCEPTION, e.getMessage ());
        }
        return me;
    }
}
