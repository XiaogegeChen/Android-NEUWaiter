package com.neuwljs.wallsmalltwo.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neuwljs.wallsmalltwo.R;
import com.neuwljs.wallsmalltwo.adapter.recycler.FoundRecyclerViewAdapter;
import com.neuwljs.wallsmalltwo.common.BaseFragment;
import com.neuwljs.wallsmalltwo.common.BaseRecyclerViewAdapter;
import com.neuwljs.wallsmalltwo.model.gson.Found;
import com.neuwljs.wallsmalltwo.presenter.impl.FragmentDAAPresenterImpl;
import com.neuwljs.wallsmalltwo.view.ViewContract;

import java.util.ArrayList;
import java.util.List;

import static com.neuwljs.wallsmalltwo.adapter.recycler.FoundRecyclerViewAdapter.STATE_DONE;
import static com.neuwljs.wallsmalltwo.adapter.recycler.FoundRecyclerViewAdapter.STATE_ERROR;
import static com.neuwljs.wallsmalltwo.adapter.recycler.FoundRecyclerViewAdapter.STATE_LOAD;

public class FragmentDAA
        extends BaseFragment
        implements ViewContract.FragmentDAAView, BaseRecyclerViewAdapter.OnScrollingListener {

    private RecyclerView mRecyclerView;

    // 业务逻辑的实例
    private FragmentDAAPresenterImpl mFragmentDAAPresenter;

    // recyclerView 的适配器
    private FoundRecyclerViewAdapter mAdapter;

    // recyclerView 数据源
    private List<Found> mFoundList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView (inflater, container, savedInstanceState);
        mFragmentDAAPresenter.attach (this);

        mFragmentDAAPresenter.queryPage (String.valueOf (0));
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        mFragmentDAAPresenter.detach ();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_d_a_a;
    }

    @Override
    public void initView(View view) {
        mRecyclerView = view.findViewById (R.id.fragment_d_a_a_property_recycler_view);
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
    public void showRecyclerView(List<Found> foundList) {
        mAdapter.addToEnd (foundList);
    }

    @Override
    public void showDone() {
        mAdapter.setState (STATE_DONE);
    }

    /**
     * {@link com.neuwljs.wallsmalltwo.common.BaseRecyclerViewAdapter.OnScrollingListener}
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
}
