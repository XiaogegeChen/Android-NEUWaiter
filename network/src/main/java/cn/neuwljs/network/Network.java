package cn.neuwljs.network;

import java.util.Map;

import io.reactivex.ObservableTransformer;
import retrofit2.Retrofit;

/**
 * 请求网络的门面类，通过该类的方法初始化和请求网络
 */
public class Network {

    /**
     * 请求网络的具体方法
     */
    public static Retrofit query(){
        return RetrofitManager.getInstance ()
                .getRetrofit ();
    }

    /**
     * 初始化，初始化baseurl替换表
     * @param headMap baseurl替换表
     */
    public static void init(Map<String, String> headMap){
        RetrofitManager.init (headMap, new TimeoutParam.Builder ().build ());
    }

    /**
     * 初始化，初始化baseurl替换表
     * @param headMap baseurl替换表
     * @param param 超时时间设置
     */
    public static void init(Map<String, String> headMap, TimeoutParam param){
        RetrofitManager.init (headMap, param == null ? new TimeoutParam.Builder ().build () : param);
    }

    /**
     * 设置是否开启日志打印，默认开启
     */
    public static void openLog(boolean b){
        LogUtil.openLog (b);
    }

    /**
     * 拿到设置的头的key
     */
    public static String getHeadKey(){
        return RetrofitManager.getHeadKey ();
    }

    /**
     * 线程调度
     */
    public static <T> ObservableTransformer<T,T> changeScheduler(){
        return SchedulerManager.getInstance ().applySchedulers ();
    }
}
