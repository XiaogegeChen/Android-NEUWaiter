package cn.neuwljs.module_d.presenter.impl;

import org.greenrobot.eventbus.EventBus;

import cn.neuwljs.module_d.presenter.IFragmentDBAPresenter;
import cn.neuwljs.module_d.view.IFragmentDBAView;
import cn.neuwljs.module_d.view.impl.FragmentDBC;

public class FragmentDBAPresenterImpl implements IFragmentDBAPresenter {

    private IFragmentDBAView mFragmentDBAView;

    @Override
    public void attach(IFragmentDBAView fragmentDBAView) {
        mFragmentDBAView = fragmentDBAView;
    }

    @Override
    public void detach() {
        mFragmentDBAView = null;
    }

    @Override
    public void notifyPropertyRefresh(String propertyType) {
        FragmentDBC.RefreshPropertyTypeEvent event = new FragmentDBC.RefreshPropertyTypeEvent ();
        event.setPropertyType (propertyType);
        EventBus.getDefault ().post (event);
    }
}
