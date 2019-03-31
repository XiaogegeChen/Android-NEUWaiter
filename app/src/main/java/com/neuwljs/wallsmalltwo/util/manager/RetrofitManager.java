package com.neuwljs.wallsmalltwo.util.manager;

import com.neuwljs.wallsmalltwo.common.ApiService;
import com.neuwljs.wallsmalltwo.util.LogUtil;
import com.neuwljs.wallsmalltwo.util.convert.MyGsonConverterFactory;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static com.neuwljs.wallsmalltwo.common.ApiService.OKHTTP_CONNECT_TIME_OUT;
import static com.neuwljs.wallsmalltwo.common.ApiService.OKHTTP_HEAD_MAP;
import static com.neuwljs.wallsmalltwo.common.ApiService.OKHTTP_HEAD_NAME;
import static com.neuwljs.wallsmalltwo.common.ApiService.WEATHER_BASE_URL;

/**
 * Retrofit的单例
 */
public class RetrofitManager {
    private static final String TAG =" RetrofitManager";
    private static RetrofitManager mSingleTon;
    private Retrofit mRetrofit;

    private RetrofitManager(){

        //通过okhttp拦截器动态更改baseurl
        OkHttpClient client = new OkHttpClient.Builder ()
                .connectTimeout (OKHTTP_CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor (new Interceptor () {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request request = chain.request ();
                        Request.Builder builder = request.newBuilder();
                        List<String> headList = request.headers (OKHTTP_HEAD_NAME);
                        HttpUrl oldHttpUrl = request.url();
                        if(headList.size () > 0){
                            builder.removeHeader (OKHTTP_HEAD_NAME);
                            String head = headList.get (0);
                            HttpUrl newBaseUrl = null;

                            //根据头更改baseurl
                            Map<String, String> map = OKHTTP_HEAD_MAP;
                            for(String key : map.keySet ()){
                                if(key.equals (head)){
                                    newBaseUrl = HttpUrl.parse (map.get (key));
                                    break;
                                }
                                newBaseUrl = oldHttpUrl;
                            }

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
                    }
                })
                .build ();

        //拿到Retrofit实例
        mRetrofit = new Retrofit.Builder ()
                .client (client)
                .baseUrl (WEATHER_BASE_URL)
                .addConverterFactory (MyGsonConverterFactory.create ())
                .addCallAdapterFactory (RxJava2CallAdapterFactory.create ())
                .build ();
    }

    /**
     * 拿到单例
     * @return RetrofitManager的单例
     */
    public static RetrofitManager getInstance(){
        if(mSingleTon == null){
            synchronized (RetrofitManager.class){
                if(mSingleTon == null){
                    mSingleTon = new RetrofitManager ();
                }
            }
        }
        return mSingleTon;
    }

    public ApiService.Api create(){
        return mRetrofit.create (ApiService.Api.class);
    }
}
