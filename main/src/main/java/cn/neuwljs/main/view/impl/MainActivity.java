package cn.neuwljs.main.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import cn.neuwljs.common.adapter.MyFragmentPagerAdapter;
import cn.neuwljs.common.base.BaseActivity;
import cn.neuwljs.common.base.BaseFragment;
import cn.neuwljs.main.R;
import cn.neuwljs.main.view.IMainActivityView;
import cn.neuwljs.widget.NoSlideViewPager;

import static cn.neuwljs.common.route.RouterMap.MODULE_A_FRAGMENT_A_PATH;
import static cn.neuwljs.common.route.RouterMap.MODULE_B_FRAGMENT_B_PATH;
import static cn.neuwljs.common.route.RouterMap.MODULE_C_FRAGMENT_C_PATH;
import static cn.neuwljs.common.route.RouterMap.MODULE_D_FRAGMENT_D_PATH;
import static cn.neuwljs.common.route.RouterMap.MODULE_E_FRAGMENT_E_PATH;

/**
 * MainActivity试图实现类
 */
public class MainActivity
        extends BaseActivity
        implements IMainActivityView, View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        // 必须写
        super.onActivityResult (requestCode, resultCode, data);
    }

    @Override
    public void initView() {
        mBottomNavigationView = findViewById (R.id.bnv_main);
        mNoSlideViewPager = findViewById (R.id.vp_no_slide_main);
        mFloatingActionButton = findViewById (R.id.fab);
    }

    @Override
    public void initData() {
        mBottomNavigationViewItemIds = new int[]{
                R.id.menu_main_first,
                R.id.menu_main_second,
                R.id.menu_main_third,
                R.id.menu_main_fourth,
                R.id.menu_main_fifth
        };

        // 依次拿到五个fragment的实例并存储
        BaseFragment fragmentA = (BaseFragment) ARouter
                .getInstance ()
                .build (MODULE_A_FRAGMENT_A_PATH)
                .navigation ();

        BaseFragment fragmentB = (BaseFragment) ARouter
                .getInstance ()
                .build (MODULE_B_FRAGMENT_B_PATH)
                .navigation ();

        BaseFragment fragmentC = (BaseFragment) ARouter
                .getInstance ()
                .build (MODULE_C_FRAGMENT_C_PATH)
                .navigation ();

        BaseFragment fragmentD = (BaseFragment) ARouter
                .getInstance ()
                .build (MODULE_D_FRAGMENT_D_PATH)
                .navigation ();

        BaseFragment fragmentE = (BaseFragment) ARouter
                .getInstance ()
                .build (MODULE_E_FRAGMENT_E_PATH)
                .navigation ();

        mFragmentList = new ArrayList<> ();
        mFragmentList.add (fragmentA);
        mFragmentList.add (fragmentB);
        mFragmentList.add (fragmentC);
        mFragmentList.add (fragmentD);
        mFragmentList.add (fragmentE);

        // 设置viewpager的适配器
        mNoSlideViewPager.setAdapter (new MyFragmentPagerAdapter (getSupportFragmentManager (), mFragmentList){
            @Override
            public List<String> getTitle() {

                // 没有标题，返回null
                return null;
            }
        });

        // 设置监听器
        mFloatingActionButton.setOnClickListener (this);
        mBottomNavigationView.setOnNavigationItemSelectedListener (this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_activity_main;
    }

    @Override
    public int getStatusBarColor() {
        return getContext ().getResources ().getColor (R.color.main_color_accent);
    }

    @Override
    public boolean isSupportSwipeBack() {
        //这个界面不支持侧滑
        return false;
    }

    /**
     * {@link IMainActivityView}
     */
    @Override
    public void showProgressPage() {

    }

    @Override
    public void showErrorPage() {

    }

    @Override
    public void showToast(String message) {

    }

    /**
     * {@link android.view.View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
        int id = v.getId ();
        if(id == R.id.fab){
        }
    }

    /**
     * {@link android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener}
     */
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
}
