package com.neuwljs.wallsmalltwo.presenter.impl;

import android.annotation.SuppressLint;
import android.content.Context;

import com.neuwljs.wallsmalltwo.model.base.Response;
import com.neuwljs.wallsmalltwo.model.gson.Found;
import com.neuwljs.wallsmalltwo.presenter.PresenterContract;
import com.neuwljs.wallsmalltwo.util.LogUtil;
import com.neuwljs.wallsmalltwo.util.helper.ResponseHelper;
import com.neuwljs.wallsmalltwo.util.manager.RetrofitManager;
import com.neuwljs.wallsmalltwo.util.manager.SchedulerManager;
import com.neuwljs.wallsmalltwo.util.network.MyErrorConsumer;
import com.neuwljs.wallsmalltwo.view.ViewContract;

import java.util.List;

import io.reactivex.functions.Consumer;

import static com.neuwljs.wallsmalltwo.model.Constant.LOAD_DATA_ONCE_NUM;

public class FragmentDAAPresenterImpl
        implements PresenterContract.FragmentDAAPresenter, MyErrorConsumer.OnErrorListener {

    private static final String TAG = "FragmentDAAPresenterImpl";

    private ViewContract.FragmentDAAView mFragmentDAAView;
    private Context mContext;

    // 当前页码
    private int currentPage;

    // 是否有下一页
    private boolean hasNext = true;

    // 每页请求的最大数据量
    private static final String LENGTH = String.valueOf (LOAD_DATA_ONCE_NUM);

    public FragmentDAAPresenterImpl(Context context) {
        mContext = context;
    }

    @Override
    public void attach(ViewContract.FragmentDAAView fragmentDAAView) {
        mFragmentDAAView = fragmentDAAView;
    }

    @Override
    public void detach() {
        mFragmentDAAView = null;
    }

    @SuppressLint("CheckResult")
    @Override
    public void queryPage(String page) {
        mFragmentDAAView.showProgressPage ();
        LogUtil.d (TAG, page);

        // 请求服务器拿到数据并显示
        RetrofitManager.getInstance ()
                .create ()
                .queryFound (LENGTH, page)
                .compose (SchedulerManager.getInstance ().<Response<List<Found>>>applySchedulers ())
                .compose (ResponseHelper.<List<Found>>transform ())
                .subscribe (new Consumer<List<Found>> () {
                    @Override
                    public void accept(List<Found> founds) throws Exception {
                        if(founds == null || founds.size () == 0){

                            // 返回空的表示已经完成
                            hasNext = false;
                            mFragmentDAAView.showDone ();
                        }else{

                            // 展示数据
                            mFragmentDAAView.showRecyclerView (founds);
                        }
                    }
                }, new MyErrorConsumer (mContext, this));
    }

    @Override
    public void queryNext() {
        if(hasNext){
            currentPage++;
            queryPage (String.valueOf (currentPage));
        }
    }

    @Override
    public void refresh() {

        // 从第一页开始
        currentPage = 0;
        hasNext = true;
        queryPage (String.valueOf (currentPage));
    }

    /**
     * {@link com.neuwljs.wallsmalltwo.util.network.MyErrorConsumer.OnErrorListener}
     */
    @Override
    public void onError() {
        mFragmentDAAView.showErrorPage ();
    }
}
