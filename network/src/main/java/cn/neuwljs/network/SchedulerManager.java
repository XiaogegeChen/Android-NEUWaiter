package cn.neuwljs.network;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 线程调度管理类
 */
public class SchedulerManager {

    private static SchedulerManager mSingleTon;

    private SchedulerManager(){
    }

    public static SchedulerManager getInstance(){
        if(mSingleTon == null){
            synchronized (SchedulerManager.class){
                if(mSingleTon == null){
                    mSingleTon = new SchedulerManager ();
                }
            }
        }
        return mSingleTon;
    }

    /**
     * 调度线程 观察者在主线程,被观察者在io线程
     * @param <T> 返回的数据的bean
     * @return ObservableTransformer
     */
    public <T> ObservableTransformer<T,T> applySchedulers(){
        return upstream -> upstream
                .subscribeOn (Schedulers.io ())
                .observeOn (AndroidSchedulers.mainThread ());
    }
}
