package cn.neuwljs.module_d.view.impl;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import cn.neuwljs.common.base.BaseActivity;
import cn.neuwljs.module_d.R;

public class UserSubmitActivity extends BaseActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
    }

    @Override
    public void initView() {
        mToolbar = findViewById (R.id.module_d_activity_user_submit_tool_bar);
        mTabLayout = findViewById (R.id.module_d_activity_user_submit_tab);
        mViewPager = findViewById (R.id.module_d_activity_user_submit_view_pager);
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.module_d_activity_user_submit;
    }

    @Override
    public int getStatusBarColor() {
        return getContext ().getResources ().getColor (R.color.module_d_accent_color);
    }

    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }
}
