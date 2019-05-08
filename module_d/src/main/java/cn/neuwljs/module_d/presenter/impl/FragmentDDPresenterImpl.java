package cn.neuwljs.module_d.presenter.impl;

import android.content.Intent;

import cn.neuwljs.module_d.presenter.IFragmentDDPresenter;
import cn.neuwljs.module_d.view.IFragmentDDView;
import cn.neuwljs.module_d.view.impl.FragmentDD;
import cn.neuwljs.module_d.view.impl.UserActivity;
import cn.neuwljs.module_d.view.impl.UserSubmitActivity;

public class FragmentDDPresenterImpl implements IFragmentDDPresenter {

    private IFragmentDDView mFragmentDDView;
    private FragmentDD mFragmentDD;

    @Override
    public void gotoUserActivity() {
        Intent intent = new Intent (mFragmentDD.obtainActivity (), UserActivity.class);
        mFragmentDD.startActivity (intent);
    }

    @Override
    public void gotoUserSubmitActivity() {
        Intent intent = new Intent (mFragmentDD.obtainActivity (), UserSubmitActivity.class);
        mFragmentDD.startActivity (intent);
    }

    @Override
    public void attach(IFragmentDDView fragmentDDView) {
        mFragmentDDView = fragmentDDView;
        mFragmentDD = ((FragmentDD)mFragmentDDView);
    }

    @Override
    public void detach() {
        mFragmentDDView = null;
        mFragmentDD = null;
    }
}
