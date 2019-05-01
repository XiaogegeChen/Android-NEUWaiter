package cn.neuwljs.module_d.view.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.neuwljs.common.adapter.BaseRecyclerViewAdapter;
import cn.neuwljs.common.base.BaseFragment;
import cn.neuwljs.common.util.LogUtil;
import cn.neuwljs.module_d.R;
import cn.neuwljs.module_d.adapter.LostRecyclerViewAdapter;
import cn.neuwljs.module_d.model.gson.Lost;
import cn.neuwljs.module_d.presenter.impl.FragmentDABPresenterImpl;
import cn.neuwljs.module_d.view.IFragmentDABView;

import static cn.neuwljs.common.adapter.LoadMoreRecyclerViewAdapter.STATE_DONE;
import static cn.neuwljs.common.adapter.LoadMoreRecyclerViewAdapter.STATE_ERROR;
import static cn.neuwljs.common.adapter.LoadMoreRecyclerViewAdapter.STATE_LOAD;

public class FragmentDAB
        extends BaseFragment
        implements IFragmentDABView, BaseRecyclerViewAdapter.OnScrollingListener,
        FragmentDA.OnFloatingActionButtonClickListener {

    private static final String TAG = "FragmentDAB";

    // 业务逻辑的实例
    private FragmentDABPresenterImpl mFragmentDABPresenter;

    // RecyclerView实例
    private RecyclerView mRecyclerView;

    // 数据源
    private List<Lost> mLostList;

    // recycler适配器
    private LostRecyclerViewAdapter mAdapter;

    // 向fragmentDA提供floatingActionButton点击事件的具体实现
    public FragmentDA.OnFloatingActionButtonClickListener getOnFloatingActionButtonClickListener(){
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView (inflater, container, savedInstanceState);
        mFragmentDABPresenter.attach (this);
        mFragmentDABPresenter.queryPage (String.valueOf (0));
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        mFragmentDABPresenter.detach ();
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_d_fragment_d_a_b;
    }

    @Override
    public void initView(View view) {
        mRecyclerView = view.findViewById (R.id.module_d_fragment_d_a_b_property_recycler_view);
    }

    @Override
    public void initData() {
        mFragmentDABPresenter = new FragmentDABPresenterImpl (obtainContext ());

        // mRecyclerView初始化
        mRecyclerView.setLayoutManager (new LinearLayoutManager (obtainContext ()));
        mLostList = new ArrayList<> ();
        mAdapter = new LostRecyclerViewAdapter (mLostList, obtainContext ());

        // 滑动监听
        mAdapter.setListener (this);
        mRecyclerView.setAdapter (mAdapter);
    }

    @Override
    public void showProgressPage() {
        mAdapter.setState (STATE_LOAD);
    }

    @Override
    public void showErrorPage() {
        mAdapter.setState (STATE_ERROR);
    }

    @Override
    public void showToast(String message) {
    }

    @Override
    public void showRecyclerView(List<Lost> lostList) {
        mAdapter.addToEnd (lostList);
    }

    @Override
    public void showDone() {
        mAdapter.setState (STATE_DONE);
    }

    /**
     * {@link cn.neuwljs.common.adapter.BaseRecyclerViewAdapter.OnScrollingListener}
     */
    @Override
    public void onScrolling(RecyclerView recyclerView) {
    }

    @Override
    public void onScrollTop(RecyclerView recyclerView) {
    }

    @Override
    public void onScrollDown(RecyclerView recyclerView) {
        mFragmentDABPresenter.queryNext ();
    }

    /**
     * {@link cn.neuwljs.module_d.view.impl.FragmentDA.OnFloatingActionButtonClickListener}
     */
    @Override
    public void onFloatingActionButtonClick() {

        // 刷新列表
        mLostList.clear ();
        mAdapter.notifyDataSetChanged ();
        mFragmentDABPresenter.refresh ();
        LogUtil.d (TAG, TAG+" fresh");
    }
}
