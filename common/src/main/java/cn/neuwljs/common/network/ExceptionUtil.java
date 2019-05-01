package cn.neuwljs.common.network;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import cn.neuwljs.common.util.LogUtil;

public class ExceptionUtil {

    private static final String TAG = "ExceptionUtil";

    /**
     * 未知异常
     */
    public static final String UNKNOWN_EXCEPTION = "未知错误";

    /**
     * 网络异常
     */
    private static final String NETWORK_EXCEPTION = "网络异常";

    /**
     * 网络异常
     */
    private static final String TIME_OUT_EXCEPTION = "连接超时";

    /**
     * 数据解析异常
     */
    private static final String PARSE_EXCEPTION = "数据解析异常";

    /**
     * 服务器异常
     */
    private static final String SERVER_EXCEPTION = "服务器异常";

    /**
     * 把一个exception转化成MyException,方便统一处理
     * @param e 异常
     * @return MyException
     */
    public static MyException transfer(Throwable e){
        MyException me;

        // 打印错误信息
        LogUtil.e(TAG, e.toString ());

        if(e instanceof JSONException || e instanceof JsonParseException || e instanceof ParseException){
            me = new MyException (PARSE_EXCEPTION, e.getMessage ());
        }else if(e instanceof ConnectException){
            me = new MyException (NETWORK_EXCEPTION, e.getMessage ());
        }else if(e instanceof UnknownHostException){
            me = new MyException (NETWORK_EXCEPTION, e.getMessage ());
        }else if(e instanceof SocketTimeoutException){
            me = new MyException (TIME_OUT_EXCEPTION, e.getMessage ());
        }else if(e instanceof MyException){
            me = new MyException (SERVER_EXCEPTION, e.getMessage ());
        }else{
            me = new MyException (UNKNOWN_EXCEPTION, e.getMessage ());
        }
        return me;
    }
}
