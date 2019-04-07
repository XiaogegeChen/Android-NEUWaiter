package com.neuwljs.wallsmalltwo.common;

import com.neuwljs.wallsmalltwo.model.base.Response;
import com.neuwljs.wallsmalltwo.model.gson.AccessToken;
import com.neuwljs.wallsmalltwo.model.gson.Found;
import com.neuwljs.wallsmalltwo.model.gson.Words;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class ApiService {

    /**
     * okhttp连接超时时间
     */
    public static final long OKHTTP_CONNECT_TIME_OUT = 6;

    /**
     * okhttp读取数据超时时间
     */
    public static final long OKHTTP_READ_TIME_OUT = 6;

    /**
     * okhttp上传数据超时时间
     */
    public static final long OKHTTP_WRITE_TIME_OUT = 6;

    /**
     * okhttp head的name
     */
    public static final String OKHTTP_HEAD_NAME = "okhttp_head_name";

    /**
     * 百度OCR的AK
     */
    public static final String OCR_AK = "Fww20HZOlhRhNuvQET6jDSQt";

    /**
     * 百度OCR的SK
     */
    public static final String OCR_SK = "ylXMmGC4VMC90zwsBwDpQ68EmzbjulUE";

    /**
     * 百度OCR的GRANT_TYPE
     */
    public static final String GRANT_TYPE = "client_credentials";

    /**
     * 百度OCR请求头中的Content-Type的key
     */
    public static final String CONTENT_TYPE_KEY = "Content-Type";

    /**
     * 百度OCR请求头中的Content-Type的value
     */
    public static final String CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded";

    /**
     * 获取天气信息api的head的key
     */
    public static final String WEATHER_HEAD_KEY="weather";

    /**
     * 获取天气信息api的baseUrl
     */
    public static final String WEATHER_BASE_URL = "https://free-api.heweather.net/";

    /**
     * 百度OCR的head的key
     */
    public static final String OCR_HEAD_KEY="ocr";

    /**
     * 百度OCR的baseUrl
     */
    public static final String OCR_BASE_URL = "https://aip.baidubce.com/";

    /**
     * 失物招领服务器的head的key
     */
    public static final String LOST_AND_FOUND_HEAD_KEY="lost_and_found";

    /**
     * 失物招领服务器的baseUrl
     */
    public static final String LOST_AND_FOUND_BASE_URL = "http://www.neuwljs.cn/";

    /**
     * 请求头和baseUrl的映射集合
     * 每个key对应一个baseUrl,动态更改的时候使用
     */
    public static final Map<String, String> OKHTTP_HEAD_MAP;

    static {
        OKHTTP_HEAD_MAP = new HashMap<> ();
        OKHTTP_HEAD_MAP.put (WEATHER_HEAD_KEY, WEATHER_BASE_URL);
        OKHTTP_HEAD_MAP.put (OCR_HEAD_KEY, OCR_BASE_URL);
        OKHTTP_HEAD_MAP.put (LOST_AND_FOUND_HEAD_KEY, LOST_AND_FOUND_BASE_URL);
    }

    /**
     * 请求网络的接口
     */
    public interface Api{

        /**
         * 获得百度文字识别鉴权认证  Access Token值的api
         * @param grantType 固定值  为{@link ApiService#GRANT_TYPE}
         * @param clientId AK
         * @param clientSecret SK
         * @return 一个AccessToken的实例,这个实例的accessToken域可能为空
         */
        @Headers ({OKHTTP_HEAD_NAME + ":" + OCR_HEAD_KEY})
        @GET("oauth/2.0/token")
        Observable<AccessToken> queryOCRAccessToken(@Query("grant_type") String grantType,
                                                    @Query("client_id") String clientId,
                                                    @Query("client_secret") String clientSecret);

        /**
         * 获得百度OCR识别文字之后返回的结果
         * @param accessToken 是{@link Api#queryOCRAccessToken(String, String, String)}的返回值的accessToken字段
         * @param image 经过base64编码的图片
         * @return Words实例,包含图片的文字信息
         */
        @FormUrlEncoded
        @Headers ({OKHTTP_HEAD_NAME + ":" + OCR_HEAD_KEY,
                CONTENT_TYPE_KEY + ":" + CONTENT_TYPE_VALUE})
        @POST("rest/2.0/ocr/v1/accurate_basic")
        Observable<Words> queryOCRWords(@Query ("access_token") String accessToken,
                                        @Field("image") String image);

        /**
         * 分页获得失物招领服务器返回的found数据列表
         * @param length 每页有多少个数据,目前写10
         * @param page 页码,也就是第几页,从0开始
         * @return 包含Found信息的bean
         */
        @Headers ({OKHTTP_HEAD_NAME + ":" + LOST_AND_FOUND_HEAD_KEY})
        @GET("list_api.php")
        Observable<Response<List<Found>>> queryFound(@Query("len") String length,
                                                     @Query("page") String page);
    }
}
