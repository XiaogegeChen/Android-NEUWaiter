package com.neuwljs.wallsmalltwo.presenter.impl;

import com.neuwljs.wallsmalltwo.common.IndicatorFragment;
import com.neuwljs.wallsmalltwo.presenter.PresenterContract;
import com.neuwljs.wallsmalltwo.view.ViewContract;

import org.greenrobot.eventbus.EventBus;

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
        // TODO
    }

    @Override
    public void notifyFragmentDBRefresh() {
        // 前三个页面刷新
        IndicatorFragment.RefreshEvent event = new IndicatorFragment.RefreshEvent ();
        event.setBegin (true);
        EventBus.getDefault ().post (event);
    }
}
