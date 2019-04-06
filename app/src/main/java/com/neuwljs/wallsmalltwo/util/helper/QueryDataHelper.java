package com.neuwljs.wallsmalltwo.util.helper;

import android.annotation.SuppressLint;

import com.neuwljs.wallsmalltwo.util.manager.SchedulerManager;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class QueryDataHelper<T> {

    /**
     * 请求数据
     * @param type 发起的请求的类型 Type.NORMAL表示正常类型,优先从本地拉取数据
     *             Type.REFRESH表示刷新类型,优先从网络拉取数据
     * @return 返回数据的可订阅主题,默认的观察者在主线程,发布者在io线程
     */
    @SuppressLint("CheckResult")
    public Maybe<T> queryData(Type type){
        switch (type){
            case NORMAL:
                return Observable.concat (queryFromLocal (),queryFromNet ())
                        .compose (SchedulerManager.getInstance ().<T>applySchedulers ())
                        .firstElement ();
            case REFRESH:
                return Observable.concat (queryFromNet (), queryFromLocal ())
                        .compose (SchedulerManager.getInstance ().<T>applySchedulers ())
                        .firstElement ();
            default:
                return Observable.concat (queryFromLocal (),queryFromNet ())
                        .compose (SchedulerManager.getInstance ().<T>applySchedulers ())
                        .firstElement ();
        }
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

    /**
     * 访问类型
     */
    public enum Type{
        /**
         * 正常的访问类型，优先从本地拿数据，比如读取地理位置的列表
         */
        NORMAL,

        /**
         * 刷新式的访问类型，优先请求网络拿数据，比如刷新界面
         */
        REFRESH
    }
}
