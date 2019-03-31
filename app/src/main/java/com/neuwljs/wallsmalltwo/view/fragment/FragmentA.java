package com.neuwljs.wallsmalltwo.view.fragment;

import android.view.View;

import com.neuwljs.wallsmalltwo.R;
import com.neuwljs.wallsmalltwo.common.BaseFragment;

public class FragmentA extends BaseFragment {

    public static FragmentA newInstance(){
        return new FragmentA ();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_a;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }
}
