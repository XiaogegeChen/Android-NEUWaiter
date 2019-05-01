package cn.neuwljs.module_d;

import java.util.List;

import cn.neuwljs.common.network.Response;
import cn.neuwljs.module_d.model.gson.Found;
import cn.neuwljs.module_d.model.gson.Lost;
import cn.neuwljs.module_d.model.gson.SubmitPropertyResult;
import cn.neuwljs.module_d.model.submit.SubmitProperty;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static cn.neuwljs.module_d.Constants.LOST_AND_FOUND_HEAD_KEY;
import static cn.neuwljs.module_d.Constants.OKHTTP_HEAD_NAME;

/**
 * 请求网络的接口
 */
public interface Api {

    /**
     * 分页获得失物招领服务器返回的found数据列表
     * @param length 每页有多少个数据
     * @param page 页码,也就是第几页,从0开始
     * @return 包含Found信息的bean
     */
    @Headers({OKHTTP_HEAD_NAME + ":" + LOST_AND_FOUND_HEAD_KEY})
    @GET("list_api.php")
    Observable<Response<List<Found>>> queryFound(@Query("len") String length,
                                                 @Query("page") String page);

    /**
     * 分页获得失物招领服务器返回的lost数据列表
     * @param lost 标记字段，这个字段内容不限，如果有这个字段就是拿到lost数据列表，没有的话拿到found列表
     *             目前赋值为{@link Constants#LOST_FIELD_DEFAULT}
     * @param length 每页有多少个数据
     * @param page 码,也就是第几页,从0开始
     * @return 包含Lost信息的bean
     */
    @Headers ({OKHTTP_HEAD_NAME + ":" + LOST_AND_FOUND_HEAD_KEY})
    @GET("list_api.php")
    Observable<Response<List<Lost>>> queryLost(@Query("lost") String lost,
                                               @Query("len") String length,
                                               @Query("page") String page);

    /**
     * 向失物招领服务器提交物品数据
     * @param submitProperty 物品信息的bean
     * @return SubmitPropertyResult实例，包含这条信息的数据库中的位置
     */
    @Headers ({OKHTTP_HEAD_NAME + ":" + LOST_AND_FOUND_HEAD_KEY})
    @POST("upload.php")
    Observable<Response<List<SubmitPropertyResult>>> uploadProperty(@Body SubmitProperty submitProperty);
}
