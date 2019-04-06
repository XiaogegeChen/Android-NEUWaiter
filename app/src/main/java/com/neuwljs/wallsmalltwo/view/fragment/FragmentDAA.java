package com.neuwljs.wallsmalltwo.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.neuwljs.wallsmalltwo.R;
import com.neuwljs.wallsmalltwo.common.BaseFragment;

public class FragmentDAA extends BaseFragment {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView (inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_d_a_a;
    }

    @Override
    public void initView(View view) {
        mProgressBar = view.findViewById (R.id.fragment_d_a_a_progress);
        mRecyclerView = view.findViewById (R.id.fragment_d_a_a_property_recycler_view);
    }

    @Override
    public void initData() {

    }
}
