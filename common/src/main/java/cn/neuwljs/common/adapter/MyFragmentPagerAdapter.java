package cn.neuwljs.common.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

import cn.neuwljs.common.base.BaseFragment;

public abstract class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mFragmentList;
    private FragmentManager mFragmentManager;

    public MyFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        mFragmentManager = fm;
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList == null ? null : mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment)super.instantiateItem(container,position);
        mFragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = mFragmentList.get(position);
        mFragmentManager.beginTransaction().hide(fragment).commit();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(getTitle () != null){
            return getTitle ().get (position);
        }
        return null;
    }

    //设置title,如果不是和TabLayout绑定,返回null
    public abstract List<String> getTitle();
}
