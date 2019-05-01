package cn.neuwljs.ocr;

import android.annotation.SuppressLint;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

abstract class QueryDataHelper<T> {

    /**
     * 请求数据
     * @return 返回数据的可订阅主题,默认的观察者在主线程,发布者在io线程
     */
    @SuppressLint("CheckResult")
    Maybe<T> queryData(){
        return Observable.concat (queryFromLocal (),queryFromNet ())
                .subscribeOn (Schedulers.io ())
                .observeOn (AndroidSchedulers.mainThread ())
                .firstElement ();
    }

    /**
     * 从网络获取数据
     * @return 获取到的数据的主题，可订阅
     */
    public abstract Observable<T> queryFromNet();

    /**
     * 从本地获取数据
     * @return 获取到的数据的主题,可订阅
     */
    public abstract Observable<T> queryFromLocal();
}
