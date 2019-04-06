package com.neuwljs.wallsmalltwo.presenter.impl;

import com.neuwljs.wallsmalltwo.presenter.PresenterContract;
import com.neuwljs.wallsmalltwo.view.ViewContract;
import com.neuwljs.wallsmalltwo.view.fragment.FragmentDBC;

import org.greenrobot.eventbus.EventBus;

public class FragmentDBAPresenterImpl implements PresenterContract.FragmentDBAPresenter {

    private ViewContract.FragmentDBAView mFragmentDBAView;

    @Override
    public void attach(ViewContract.FragmentDBAView fragmentDBAView) {
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
