package cn.neuwljs.module_d.presenter.impl;

import org.greenrobot.eventbus.EventBus;

import cn.neuwljs.module_d.IndicatorFragment;
import cn.neuwljs.module_d.presenter.IFragmentDBDPresenter;
import cn.neuwljs.module_d.view.IFragmentDBDView;
import cn.neuwljs.module_d.view.impl.FragmentDAA;

public class FragmentDBDPresenterImpl implements IFragmentDBDPresenter {

    private IFragmentDBDView mFragmentDBDView;

    @Override
    public void attach(IFragmentDBDView fragmentDBDView) {
        mFragmentDBDView = fragmentDBDView;
    }

    @Override
    public void detach() {
        mFragmentDBDView = null;
    }

    @Override
    public void notifyFragmentDAARefresh() {
        FragmentDAA.RefreshEvent event = new FragmentDAA.RefreshEvent ();
        event.setBegin (true);
        EventBus.getDefault ().post (event);
    }

    @Override
    public void notifyFragmentDBRefresh() {
        // 前三个页面刷新
        IndicatorFragment.RefreshEvent event = new IndicatorFragment.RefreshEvent ();
        event.setBegin (true);
        EventBus.getDefault ().post (event);
    }
}
