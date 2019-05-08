package cn.neuwljs.module_d.view.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.neuwljs.common.adapter.MyFragmentPagerAdapter;
import cn.neuwljs.common.base.BaseFragment;
import cn.neuwljs.common.util.LogUtil;
import cn.neuwljs.module_d.R;

import static cn.neuwljs.common.route.RouterMap.MODULE_D_FRAGMENT_D_PATH;

@Route (path = MODULE_D_FRAGMENT_D_PATH)
public class FragmentD extends BaseFragment {

    private static final String TAG = "FragmentD";
    
    private String[] mTabLayoutText;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    //fragment集合
    private List<BaseFragment> mFragmentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView (inflater, container, savedInstanceState);
        LogUtil.d (TAG, "onCreateView: ");
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged (hidden);
        LogUtil.d (TAG, "onHiddenChanged: " + hidden);
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_d_fragment_d;
    }

    @Override
    public void initView(View view) {
        mTabLayout = view.findViewById (R.id.module_d_fragment_d_tab);
        mViewPager = view.findViewById (R.id.module_d_fragment_d_view_pager);

        mTabLayoutText = getResources ().getStringArray (R.array.module_d_fragment_d_tab_titles);

        for(String tabLayoutText : mTabLayoutText){
            mTabLayout.addTab (mTabLayout.newTab ().setText (tabLayoutText));
        }
    }

    @Override
    public void initData() {
        mFragmentList = new ArrayList<> ();
        FragmentDA fragmentDA = new FragmentDA ();
        FragmentDB fragmentDB = new FragmentDB ();
        mFragmentList.add (fragmentDA);
        mFragmentList.add (fragmentDB);
        mFragmentList.add (new FragmentDC ());
        mFragmentList.add (new FragmentDD ());

        mViewPager.setAdapter (new MyFragmentPagerAdapter (getFragmentManager (), mFragmentList){

            @Override
            public List<String> getTitle() {
                return Arrays.asList (mTabLayoutText);
            }
        });

        mTabLayout.setupWithViewPager (mViewPager);

        // fragmentDB设置ViewPager,用于传递给fragmentDB的ViewPager的最后一页
        fragmentDB.setViewPager (mViewPager);
    }
}
