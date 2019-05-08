package cn.neuwljs.module_d.view.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.neuwljs.common.adapter.BaseRecyclerViewAdapter;
import cn.neuwljs.common.adapter.LoadMoreRecyclerViewAdapter;
import cn.neuwljs.common.base.BaseFragment;
import cn.neuwljs.module_d.R;
import cn.neuwljs.module_d.adapter.FoundRecyclerViewAdapter;
import cn.neuwljs.module_d.model.event.NotifyFragmentDAARefreshEvent;
import cn.neuwljs.module_d.model.gson.Found;
import cn.neuwljs.module_d.presenter.impl.FragmentDAAPresenterImpl;
import cn.neuwljs.module_d.view.IFragmentDAAView;

public class FragmentDAA
        extends BaseFragment
        implements IFragmentDAAView, BaseRecyclerViewAdapter.OnScrollingListener,
        FragmentDA.OnFloatingActionButtonClickListener {

    private RecyclerView mRecyclerView;

    // 业务逻辑的实例
    private FragmentDAAPresenterImpl mFragmentDAAPresenter;

    // recyclerView 的适配器
    private FoundRecyclerViewAdapter mAdapter;

    // recyclerView 数据源
    private List<Found> mFoundList;

    // 向fragmentDA提供floatingActionButton点击事件的具体实现
    public FragmentDA.OnFloatingActionButtonClickListener getOnFloatingActionButtonClickListener(){
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView (inflater, container, savedInstanceState);
        EventBus.getDefault ().register (this);
        mFragmentDAAPresenter.attach (this);

        mFragmentDAAPresenter.queryPage (String.valueOf (0));
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        mFragmentDAAPresenter.detach ();
        EventBus.getDefault ().unregister (this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_d_fragment_d_a_a;
    }

    @Override
    public void initView(View view) {
        mRecyclerView = view.findViewById (R.id.module_d_fragment_d_a_a_property_recycler_view);
    }

    @Override
    public void initData() {
        mFragmentDAAPresenter = new FragmentDAAPresenterImpl (obtainContext ());

        // recyclerView 初始化
        mRecyclerView.setLayoutManager (new LinearLayoutManager (obtainContext ()));
        mFoundList = new ArrayList<> ();
        mAdapter = new FoundRecyclerViewAdapter (mFoundList, obtainContext ());

        // 设置滑动监听器
        mAdapter.setListener (this);
        mRecyclerView.setAdapter (mAdapter);
    }

    @Override
    public void showProgressPage() {
        mAdapter.setState (LoadMoreRecyclerViewAdapter.STATE_LOAD);
    }

    @Override
    public void showErrorPage() {
        mAdapter.setState (LoadMoreRecyclerViewAdapter.STATE_ERROR);
    }

    @Override
    public void showToast(String message) {
    }

    @Override
    public void showRecyclerView(List<Found> foundList) {
        mAdapter.addToEnd (foundList);
    }

    @Override
    public void showDone() {
        mAdapter.setState (LoadMoreRecyclerViewAdapter.STATE_DONE);
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
        mFragmentDAAPresenter.queryNext ();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(NotifyFragmentDAARefreshEvent event){

        // 刷新
        onFloatingActionButtonClick ();
    }

    /**
     * {@link cn.neuwljs.module_d.view.impl.FragmentDA.OnFloatingActionButtonClickListener}
     */
    @Override
    public void onFloatingActionButtonClick() {

        // 数据源清空，并刷新
        mFoundList.clear ();
        mAdapter.notifyDataSetChanged ();
        mFragmentDAAPresenter.refresh ();
    }

}