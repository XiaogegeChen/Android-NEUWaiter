package com.neuwljs.wallsmalltwo.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neuwljs.wallsmalltwo.R;
import com.neuwljs.wallsmalltwo.adapter.MyFragmentPagerAdapter;
import com.neuwljs.wallsmalltwo.common.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentD extends BaseFragment {

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
        return view;
    }

    public static FragmentD newInstance(){
        return new FragmentD ();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_d;
    }

    @Override
    public void initView(View view) {
        mTabLayout = view.findViewById (R.id.fragment_d_tab);
        mViewPager = view.findViewById (R.id.fragment_d_view_pager);

        mTabLayoutText = getResources ().getStringArray (R.array.fragment_d_tab_titles);

        for(String tabLayoutText : mTabLayoutText){
            mTabLayout.addTab (mTabLayout.newTab ().setText (tabLayoutText));
        }
    }

    @Override
    public void initData() {
        mFragmentList = new ArrayList<> ();
        FragmentDB fragmentDB = new FragmentDB ();
        mFragmentList.add (new FragmentDA ());
        mFragmentList.add (fragmentDB);
        mFragmentList.add (new FragmentDC ());
        mFragmentList.add (new FragmentDD ());

        mViewPager.setAdapter (new MyFragmentPagerAdapter (getFragmentManager (),
                mFragmentList){

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
