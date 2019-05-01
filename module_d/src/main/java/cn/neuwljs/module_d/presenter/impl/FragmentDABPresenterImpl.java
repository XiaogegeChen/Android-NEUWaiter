package cn.neuwljs.module_d.presenter.impl;

import android.annotation.SuppressLint;
import android.content.Context;

import cn.neuwljs.common.network.MyErrorConsumer;
import cn.neuwljs.common.network.ResponseHelper;
import cn.neuwljs.common.util.LogUtil;
import cn.neuwljs.module_d.Api;
import cn.neuwljs.module_d.presenter.IFragmentDABPresenter;
import cn.neuwljs.module_d.view.IFragmentDABView;
import cn.neuwljs.network.Network;

import static cn.neuwljs.module_d.Constants.LOAD_DATA_ONCE_NUM;
import static cn.neuwljs.module_d.Constants.LOST_FIELD_DEFAULT;

public class FragmentDABPresenterImpl
        implements IFragmentDABPresenter, MyErrorConsumer.OnErrorListener {

    private static final String TAG = "FragmentDABPresenterImpl";

    // 当前页码
    private int currentPage;

    // 是否有下一页
    private boolean hasNext = true;

    // 视图的引用
    private IFragmentDABView mFragmentDABView;

    // 上下文对象
    private Context mContext;

    // 每页请求的最大数据量
    private static final String LENGTH = String.valueOf (LOAD_DATA_ONCE_NUM);

    public FragmentDABPresenterImpl(Context context) {
        mContext = context;
    }

    @Override
    public void attach(IFragmentDABView fragmentDABView) {
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

        Network.query ()
                .create (Api.class)
                .queryLost (LOST_FIELD_DEFAULT, LENGTH, page)
                .compose (Network.changeScheduler ())
                .compose (ResponseHelper.transform ())
                .subscribe (losts -> {
                    if(losts == null || losts.size () == 0){

                        // 返回空的表示已经完成
                        hasNext = false;
                        mFragmentDABView.showDone ();
                    }else{

                        // 展示数据
                        mFragmentDABView.showRecyclerView (losts);
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

        // 从第一页开始重新加载
        currentPage = 0;
        hasNext = true;
        queryPage (String.valueOf (currentPage));
    }

    /**
     * {@link cn.neuwljs.common.network.MyErrorConsumer.OnErrorListener}
     */
    @Override
    public void onError() {
        mFragmentDABView.showErrorPage ();
    }
}
