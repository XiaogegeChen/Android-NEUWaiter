package com.neuwljs.wallsmalltwo.presenter.impl;

import com.neuwljs.wallsmalltwo.presenter.PresenterContract;
import com.neuwljs.wallsmalltwo.view.ViewContract;

public class FragmentDBDPresenterImpl implements PresenterContract.FragmentDBDPresenter {

    private ViewContract.FragmentDBDView mFragmentDBDView;

    @Override
    public void attach(ViewContract.FragmentDBDView fragmentDBDView) {
        mFragmentDBDView = fragmentDBDView;
    }

    @Override
    public void detach() {
        mFragmentDBDView = null;
    }

    @Override
    public void notifyFragmentDARefresh() {
    }

    @Override
    public void notifyFragmentDBARefresh() {

    }
}
