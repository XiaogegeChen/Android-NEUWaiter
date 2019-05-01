package cn.neuwljs.ocr;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static cn.neuwljs.ocr.Constant.CONTENT_TYPE_KEY;
import static cn.neuwljs.ocr.Constant.CONTENT_TYPE_VALUE;
import static cn.neuwljs.ocr.Constant.OCR_HEAD_KEY;
import static cn.neuwljs.ocr.Constant.OKHTTP_HEAD_NAME;

interface Api {

    /**
     * 获得百度文字识别鉴权认证  Access Token值的api
     * @param grantType 固定值  为{@link Constant#GRANT_TYPE}
     * @param clientId AK
     * @param clientSecret SK
     * @return 一个AccessToken的实例,这个实例的accessToken域可能为空
     */
    @Headers({OKHTTP_HEAD_NAME + ":" + OCR_HEAD_KEY})
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
}
