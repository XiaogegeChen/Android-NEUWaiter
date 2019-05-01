package cn.neuwljs.common.network;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * 服务器返回数据的帮助类,对数据进行过滤,
 * 如果数据不合法抛出MyException异常
 */
public class ResponseHelper {

    /**
     * 对请求网络的结果进一步过滤
     * @param <T> 返回的数据的bean
     * @return ObservableTransformer
     */
    public static <T>ObservableTransformer<Response<T>, T> transform(){
        return upstream -> upstream.onErrorResumeNext (throwable -> {

            // 在抛出之前把这个Exception转化成可处理的MyException
            return Observable.error (ExceptionUtil.transfer (throwable));
        }).flatMap ((Function<Response<T>, ObservableSource<T>>) tResponse -> {
            String errorCode = tResponse.getErrorCode ();
            String message = tResponse.getReason ();
            if("200".equals (errorCode)){

                // 直接发射出去
                return Observable.just (tResponse.getResult ());
            }else{

                // 转化成处理的MyException并发射
                return Observable.error (ExceptionUtil.transfer (new MyException (errorCode, message)));
            }
        });
    }

}
