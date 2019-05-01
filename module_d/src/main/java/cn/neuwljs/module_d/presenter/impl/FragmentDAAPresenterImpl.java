package cn.neuwljs.module_d.presenter.impl;

import android.annotation.SuppressLint;
import android.content.Context;

import cn.neuwljs.common.network.MyErrorConsumer;
import cn.neuwljs.common.network.ResponseHelper;
import cn.neuwljs.common.util.LogUtil;
import cn.neuwljs.module_d.Api;
import cn.neuwljs.module_d.presenter.IFragmentDAAPresenter;
import cn.neuwljs.module_d.view.IFragmentDAAView;
import cn.neuwljs.network.Network;

import static cn.neuwljs.module_d.Constants.LOAD_DATA_ONCE_NUM;

public class FragmentDAAPresenterImpl
        implements IFragmentDAAPresenter, MyErrorConsumer.OnErrorListener {

    private static final String TAG = "FragmentDAAPresenterImpl";

    private IFragmentDAAView mFragmentDAAView;
    private Context mContext;

    // 每页请求的最大数据量
    private static final String LENGTH = String.valueOf (LOAD_DATA_ONCE_NUM);

    // 是否有下一页
    private boolean hasNext = true;

    // 当前页码
    private int currentPage;

    public FragmentDAAPresenterImpl(Context context) {
        mContext = context;
    }

    @Override
    public void attach(IFragmentDAAView iFragmentDAAView) {
        mFragmentDAAView = iFragmentDAAView;
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

        Network.query ()
                .create (Api.class)
                .queryFound (LENGTH, page)
                .compose (Network.changeScheduler ())
                .compose (ResponseHelper.transform ())
                .subscribe (founds -> {
                    if(founds == null || founds.size () == 0){

                        // 返回空的表示已经完成
                        hasNext = false;
                        mFragmentDAAView.showDone ();
                    }else{

                        // 展示数据
                        mFragmentDAAView.showRecyclerView (founds);
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
     * {@link cn.neuwljs.common.network.MyErrorConsumer.OnErrorListener}
     */
    @Override
    public void onError() {
        mFragmentDAAView.showErrorPage ();
    }
}
