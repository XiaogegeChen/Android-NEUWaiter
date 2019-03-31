package com.neuwljs.wallsmalltwo.common;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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
     * 百度OCR的SK
     */
    public static final String GRANT_TYPE = "client_credentials";

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
     * 请求头和baseUrl的映射集合
     * 每个key对应一个baseUrl,动态更改的时候使用
     */
    public static final Map<String, String> OKHTTP_HEAD_MAP;

    static {
        OKHTTP_HEAD_MAP = new HashMap<> ();
        OKHTTP_HEAD_MAP.put (WEATHER_HEAD_KEY, WEATHER_BASE_URL);
        OKHTTP_HEAD_MAP.put (OCR_HEAD_KEY, OCR_BASE_URL);
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
         * @return 由于是动态变换的,这里返回请求到的json的object如下,之后再处理
         *
         *  {refresh_token=25.623959b8d43ac402f2b0aa32d2440aef.315360000.1869405557.282335-15306874,
         *  expires_in=2592000.0,
         *  session_key=9mzdCSfPQ/nxuse751jqUJaUu6c/uG5PZ56z+KB4rG7tTrz64IMVVAgnOfAc1H+ssk7Edc648HIcek+MRKgqTt2kqfzyhw==,
         *  access_token=24.21afe8990a0a9b63cb46a5560f4cd372.2592000.1556637557.282335-15306874,
         *  scope=brain_ocr_passport vis-ocr_定额发票识别 brain_ocr_quota_invoice vis-ocr_车辆vin码识别 brain_ocr_vin public vis-ocr_ocr brain_ocr_scope brain_ocr_general brain_ocr_general_basic brain_ocr_general_enhanced vis-ocr_business_license brain_ocr_webimage brain_all_scope brain_ocr_idcard brain_ocr_driving_license brain_ocr_vehicle_license vis-ocr_plate_number brain_solution brain_ocr_plate_number brain_ocr_accurate brain_ocr_accurate_basic brain_ocr_receipt brain_ocr_business_license brain_solution_iocr brain_ocr_handwriting brain_ocr_vat_invoice brain_numbers brain_ocr_train_ticket brain_ocr_taxi_receipt wise_adapt lebo_resource_base lightservice_public hetu_basic lightcms_map_poi kaidian_kaidian ApsMisTest_Test权限 vis-classify_flower lpq_开放 cop_helloScope ApsMis_fangdi_permission smartapp_snsapi_base iop_autocar oauth_tp_app smartapp_smart_game_openapi oauth_sessionkey smartapp_swanid_verify smartapp_opensource_openapi,
         *  session_secret=41a61afc0756032b4fd5acfee22ef1d0}
         */
        @Headers ({OKHTTP_HEAD_NAME + ":" + OCR_HEAD_KEY})
        @GET("oauth/2.0/token")
        Observable<Object> queryOCRAccessToken(@Query("grant_type") String grantType,
                                               @Query("client_id") String clientId,
                                               @Query("client_secret") String clientSecret);
    }
}
