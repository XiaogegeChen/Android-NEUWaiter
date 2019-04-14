package com.neuwljs.wallsmalltwo.presenter.impl;

import com.neuwljs.wallsmalltwo.presenter.PresenterContract;
import com.neuwljs.wallsmalltwo.view.ViewContract;
import com.neuwljs.wallsmalltwo.view.activity.LoginActivityD;

public class LoginActivityDPresenterImpl implements PresenterContract.LoginActivityDPresenter {

    // 当前视图的引用
    private ViewContract.LoginActivityDView mLoginActivityDView;

    // 当前activity的引用
    private LoginActivityD mLoginActivityD;

    public LoginActivityDPresenterImpl(LoginActivityD loginActivityD) {
        mLoginActivityD = loginActivityD;
    }

    @Override
    public void attach(ViewContract.LoginActivityDView loginActivityDView) {
        mLoginActivityDView = loginActivityDView;
    }

    @Override
    public void detach() {
        mLoginActivityDView = null;
        mLoginActivityD = null;
    }

    @Override
    public void login() {
        // TODO 回调，赋值，finish活动
    }
}
