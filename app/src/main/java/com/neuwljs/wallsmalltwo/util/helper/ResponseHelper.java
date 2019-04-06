package com.neuwljs.wallsmalltwo.util.helper;

import com.neuwljs.wallsmalltwo.model.MyException;
import com.neuwljs.wallsmalltwo.model.base.Response;
import com.neuwljs.wallsmalltwo.util.ExceptionUtil;

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
        return new ObservableTransformer<Response<T>, T> () {
            @Override
            public ObservableSource<T> apply(Observable<Response<T>> upstream) {
                return upstream.onErrorResumeNext (new Function<Throwable, ObservableSource<? extends Response<T>>> () {
                    @Override
                    public ObservableSource<? extends Response<T>> apply(Throwable throwable) throws Exception {

                        // 在抛出之前把这个Exception转化成可处理的MyException
                        return Observable.error (ExceptionUtil.transfer (throwable));
                    }
                }).flatMap (new Function<Response<T>, ObservableSource<T>> () {
                    @Override
                    public ObservableSource<T> apply(Response<T> tResponse) throws Exception {
                        String errorCode = tResponse.getErrorCode ();
                        String message = tResponse.getReason ();
                        if("200".equals (errorCode)){

                            // 直接发射出去
                            return Observable.just (tResponse.getResult ());
                        }else{

                            // 转化成处理的MyException并发射
                            return Observable.error (ExceptionUtil.transfer (new MyException (errorCode, message)));
                        }
                    }
                });
            }
        };
    }

}
