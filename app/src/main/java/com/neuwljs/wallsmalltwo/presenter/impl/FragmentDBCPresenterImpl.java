package com.neuwljs.wallsmalltwo.presenter.impl;

import com.neuwljs.wallsmalltwo.presenter.PresenterContract;
import com.neuwljs.wallsmalltwo.view.ViewContract;

public class FragmentDBCPresenterImpl implements PresenterContract.FragmentDBCPresenter {

    private ViewContract.FragmentDBCView mFragmentDBCView;

    @Override
    public void attach(ViewContract.FragmentDBCView fragmentDBCView) {
        mFragmentDBCView = fragmentDBCView;
    }

    @Override
    public void detach() {
        mFragmentDBCView = null;
    }

    @Override
    public void load() {

    }
}
