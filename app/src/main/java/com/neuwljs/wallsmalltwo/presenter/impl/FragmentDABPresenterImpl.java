package com.neuwljs.wallsmalltwo.presenter.impl;

import android.annotation.SuppressLint;
import android.content.Context;

import com.neuwljs.wallsmalltwo.model.base.Response;
import com.neuwljs.wallsmalltwo.model.gson.Lost;
import com.neuwljs.wallsmalltwo.presenter.PresenterContract;
import com.neuwljs.wallsmalltwo.util.LogUtil;
import com.neuwljs.wallsmalltwo.util.helper.ResponseHelper;
import com.neuwljs.wallsmalltwo.util.manager.RetrofitManager;
import com.neuwljs.wallsmalltwo.util.manager.SchedulerManager;
import com.neuwljs.wallsmalltwo.util.network.MyErrorConsumer;
import com.neuwljs.wallsmalltwo.view.ViewContract;

import java.util.List;

import io.reactivex.functions.Consumer;

import static com.neuwljs.wallsmalltwo.common.ApiService.LOST_FIELD_DEFAULT;
import static com.neuwljs.wallsmalltwo.model.Constant.LOAD_DATA_ONCE_NUM;

public class FragmentDABPresenterImpl
        implements PresenterContract.FragmentDABPresenter, MyErrorConsumer.OnErrorListener {

    private static final String TAG = "FragmentDABPresenterImpl";

    // 当前页码
    private int currentPage;

    // 是否有下一页
    private boolean hasNext = true;

    // 视图的引用
    private ViewContract.FragmentDABView mFragmentDABView;

    // 上下文对象
    private Context mContext;

    // 每页请求的最大数据量
    private static final String LENGTH = String.valueOf (LOAD_DATA_ONCE_NUM);

    public FragmentDABPresenterImpl(Context context) {
        mContext = context;
    }

    @Override
    public void attach(ViewContract.FragmentDABView fragmentDABView) {
        mFragmentDABView = fragmentDABView;
    }

    @Override
    public void detach() {
        mFragmentDABView = null;
    }

    @SuppressLint("CheckResult")
    @Override
    public void queryPage(String page) {
        mFragmentDABView.showProgressPage ();
        LogUtil.d (TAG, page);

        RetrofitManager.getInstance ()
                .create ()
                .queryLost (LOST_FIELD_DEFAULT, LENGTH, page)
                .compose (SchedulerManager.getInstance ().<Response<List<Lost>>>applySchedulers ())
                .compose (ResponseHelper.<List<Lost>>transform ())
                .subscribe (new Consumer<List<Lost>> () {
                    @Override
                    public void accept(List<Lost> losts) throws Exception {
                        if(losts == null || losts.size () == 0){

                            // 返回空的表示已经完成
                            hasNext = false;
                            mFragmentDABView.showDone ();
                        }else{

                            // 展示数据
                            mFragmentDABView.showRecyclerView (losts);
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

    /**
     * {@link com.neuwljs.wallsmalltwo.util.network.MyErrorConsumer.OnErrorListener}
     */
    @Override
    public void onError() {
        mFragmentDABView.showErrorPage ();
    }
}
