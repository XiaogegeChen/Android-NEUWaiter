package cn.neuwljs.network;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Retrofit的单例
 */
class RetrofitManager {

    /**
     * okhttp连接超时时间
     */
    private static long sConnectTime = 6;

    /**
     * okhttp读取数据超时时间
     */
    private static long sReadTime = 6;

    /**
     * okhttp上传数据超时时间
     */
    private static long sWriteTime = 6;

    // 动态更改baseurl时用的head的key
    private static final String OKHTTP_HEAD_NAME = "okhttp_head_name";

    // head的value和baseurl映射表
    private static Map<String, String> sMap = new HashMap<> ();

    private static final String TAG =" RetrofitManager";
    private static RetrofitManager mSingleTon;
    private Retrofit mRetrofit;

    static void init(Map<String, String> headMap, TimeoutParam param){
        sMap = headMap;
        sWriteTime = param.getWriteTime () == 0 ? sWriteTime : param.getWriteTime ();
        sReadTime = param.getReadTime () == 0 ? sReadTime : param.getReadTime ();
        sConnectTime = param.getConnectTime () == 0 ? sConnectTime : param.getConnectTime ();
    }

    static String getHeadKey(){
        return OKHTTP_HEAD_NAME;
    }

    private RetrofitManager(){

        //通过okhttp拦截器动态更改baseurl
        OkHttpClient client = new OkHttpClient.Builder ()
                .connectTimeout (sConnectTime, TimeUnit.SECONDS)
                .readTimeout (sReadTime, TimeUnit.SECONDS)
                .writeTimeout (sWriteTime, TimeUnit.SECONDS)
                .retryOnConnectionFailure (true)
                .addInterceptor (chain -> {
                    Request request = chain.request ();
                    Request.Builder builder = request.newBuilder();

                    // 拿到设置的head
                    List<String> headList = request.headers (OKHTTP_HEAD_NAME);
                    HttpUrl oldHttpUrl = request.url();
                    if(headList.size () > 0){

                        // 移除设置的head
                        builder.removeHeader (OKHTTP_HEAD_NAME);
                        String head = headList.get (0);
                        HttpUrl newBaseUrl = null;

                        //根据head的值更改baseUrl
                        Map<String, String> map = sMap;
                        for(String key : map.keySet ()){
                            if(key.equals (head)){
                                String s = map.get (key);
                                if(s != null){
                                    newBaseUrl = HttpUrl.parse (s);
                                }
                                break;
                            }
                            newBaseUrl = oldHttpUrl;
                        }

                        // 生成新的url
                        HttpUrl newUrl = oldHttpUrl
                                .newBuilder()
                                .scheme(newBaseUrl.scheme())
                                .host(newBaseUrl.host())
                                .port(newBaseUrl.port())
                                .build();
                        LogUtil.d (TAG, "newUrl: "+newUrl.toString ());
                        return chain.proceed (builder.url (newUrl).build ());
                    }else{
                        LogUtil.d (TAG, "oldUrl: "+request.url ().toString ());
                        return chain.proceed (request);
                    }
                })
                .build ();

        //拿到Retrofit实例
        mRetrofit = new Retrofit.Builder ()
                .client (client)
                .baseUrl ("https://www.baidu.com/")
                .addConverterFactory (MyGsonConverterFactory.create ())
                .addCallAdapterFactory (RxJava2CallAdapterFactory.create ())
                .build ();
    }

    /**
     * 拿到单例
     * @return RetrofitManager的单例
     */
    static RetrofitManager getInstance(){
        if(mSingleTon == null){
            synchronized (RetrofitManager.class){
                if(mSingleTon == null){
                    mSingleTon = new RetrofitManager ();
                }
            }
        }
        return mSingleTon;
    }

    Retrofit getRetrofit(){
        return  mRetrofit;
    }

}
