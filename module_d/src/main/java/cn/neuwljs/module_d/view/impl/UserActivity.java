package cn.neuwljs.module_d.view.impl;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.neuwljs.common.base.BaseActivity;
import cn.neuwljs.common.util.ImageUtil;
import cn.neuwljs.module_d.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity
        extends BaseActivity
        implements View.OnClickListener, AppBarLayout.OnOffsetChangedListener {

    private static final String TAG = "UserActivity";
    private static final String DEBUG_URL = "https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2810627290,1080409091&fm=58&s=8197C732C535FA313E526557030030BB&bpow=121&bpoh=75";

    private CoordinatorLayout mCoordinatorLayout;
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView mUserHeadImageBackground;
    private CircleImageView mUserHeadImage;
    private TextView mUserName;
    private TextView mLost;
    private TextView mFound;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId ();
        if(id == android.R.id.home){
            finish ();
        }
        return true;
    }

    @Override
    public void initView() {
        mCoordinatorLayout = findViewById (R.id.module_d_activity_user_root_layout);
        mAppBarLayout = findViewById (R.id.module_d_activity_user_app_bar);
        mCollapsingToolbarLayout = findViewById (R.id.module_d_activity_user_collapsing_toolbar);
        mUserHeadImageBackground = findViewById (R.id.module_d_activity_user_bg_image);
        mUserHeadImage = findViewById (R.id.module_d_activity_user_head_image);
        mUserName = findViewById (R.id.module_d_activity_user_name);
        mLost = findViewById (R.id.module_d_activity_user_lost);
        mFound = findViewById (R.id.module_d_activity_user_found);
        mToolbar = findViewById (R.id.module_d_activity_user_tool_bar);
        mRecyclerView = findViewById (R.id.module_d_activity_user_recycler_view);
    }

    @Override
    public void initData() {

        ImageUtil.displayImage (this, DEBUG_URL, mUserHeadImageBackground, 15);

        // toolbar适配
        setSupportActionBar (mToolbar);
        setTitle ("");

        // 监听mAppBarLayout的滑动
        mAppBarLayout.addOnOffsetChangedListener (this);

        // 显示回退按钮
        ActionBar actionBar = getSupportActionBar ();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled (true);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_d_activity_user;
    }

    @Override
    public int getStatusBarColor() {
        getWindow ()
                .getDecorView ()
                .setSystemUiVisibility (View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        // 状态栏隐藏
        return Color.TRANSPARENT;
    }

    @Override
    public boolean isSupportSwipeBack() {

        // 支持侧滑返回
        return true;
    }

    /**
     * {@link android.view.View.OnClickListener}
     */
    @Override
    public void onClick(View v) {

    }

    /**
     * {@link android.support.design.widget.AppBarLayout.OnOffsetChangedListener}
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        int offSet = -1 * i;
        int critical = mCollapsingToolbarLayout.getScrimVisibleHeightTrigger();

        if(offSet >= critical){
            mCollapsingToolbarLayout.setTitle ("User");
        }else{
            mCollapsingToolbarLayout.setTitle (" ");
        }
    }
}
