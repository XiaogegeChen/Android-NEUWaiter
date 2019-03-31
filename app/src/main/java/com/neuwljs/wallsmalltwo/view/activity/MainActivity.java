package com.neuwljs.wallsmalltwo.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
import android.view.View;

import com.neuwljs.wallsmalltwo.R;
import com.neuwljs.wallsmalltwo.adapter.MyFragmentPagerAdapter;
import com.neuwljs.wallsmalltwo.common.BaseActivity;
import com.neuwljs.wallsmalltwo.common.BaseFragment;
import com.neuwljs.wallsmalltwo.view.fragment.FragmentA;
import com.neuwljs.wallsmalltwo.view.fragment.FragmentB;
import com.neuwljs.wallsmalltwo.view.fragment.FragmentC;
import com.neuwljs.wallsmalltwo.view.fragment.FragmentD;
import com.neuwljs.wallsmalltwo.view.fragment.FragmentE;
import com.neuwljs.wallsmalltwo.view.widget.NoSlideViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String TAG = "MainActivity";

    private BottomNavigationView mBottomNavigationView;
    private NoSlideViewPager mNoSlideViewPager;
    private FloatingActionButton mFloatingActionButton;

    //底部导航栏id集合
    private int[] mBottomNavigationViewItemIds;
    //fragment集合
    private List<BaseFragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
    }

    @Override
    public void initView() {
        mBottomNavigationView = findViewById (R.id.bnv_main);
        mNoSlideViewPager = findViewById (R.id.vp_no_slide_main);
        mFloatingActionButton = findViewById (R.id.fab);
    }

    @Override
    public void initData() {
        mBottomNavigationViewItemIds = new int[]{R.id.menu_main_first, R.id.menu_main_second,
                R.id.menu_main_third, R.id.menu_main_fourth, R.id.menu_main_fifth};

        mFragmentList = new ArrayList<> ();
        mFragmentList.add (FragmentA.newInstance ());
        mFragmentList.add (FragmentB.newInstance ());
        mFragmentList.add (FragmentC.newInstance ());
        mFragmentList.add (FragmentD.newInstance ());
        mFragmentList.add (FragmentE.newInstance ());

        mNoSlideViewPager.setAdapter (new MyFragmentPagerAdapter (
                getSupportFragmentManager (),
                mFragmentList){

            @Override
            public List<String> getTitle() {
                return null;
            }
        });

        mFloatingActionButton.setOnClickListener (this);
        mBottomNavigationView.setOnNavigationItemSelectedListener (this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public int getStatusBarColor() {
        return getContext ().getResources ().getColor (R.color.colorAccent);
    }

    @Override
    public boolean isSupportSwipeBack() {
        //这个界面不支持侧滑
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //与viewpager绑定
        for(int i=0;i<mBottomNavigationViewItemIds.length;i++){
            if(menuItem.getItemId () == mBottomNavigationViewItemIds[i]){
                mNoSlideViewPager.setCurrentItem (i);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()){
            case R.id.fab:
                break;
        }
    }

}
