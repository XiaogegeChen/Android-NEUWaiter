package cn.neuwljs.common.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import cn.neuwljs.common.R;

/**
 * Activity基类
 * 初始化侧滑
 */
public abstract class BaseActivity extends AppCompatActivity {

    private BGASwipeBackHelper mBGASwipeBackHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //初始化侧滑帮助类
        initBGASwipeBackHelper();
        super.onCreate (savedInstanceState);

        //改变状态栏颜色
        changeStatusBarColor ();

        setContentView (getLayoutId ());
        init ();
    }

    private void init(){
        initView ();
        initData ();
    }

    private void changeStatusBarColor(){
        if(getStatusBarColor () != -1){
            if(Build.VERSION.SDK_INT >= 21){
                getWindow ().setStatusBarColor (getStatusBarColor ());
            }
        }
    }

    /**
     * 获取context
     * @return  context
     */
    public Context getContext(){
        return getBaseContext ();
    }

    /**
     * 初始化视图
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 设置布局
     * @return 布局
     */
    public abstract int getLayoutId();

    /**
     * 设置状态栏颜色
     * @return 状态栏颜色
     */
    public abstract int getStatusBarColor();

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return 是否支持滑动返回
     */
    public abstract boolean isSupportSwipeBack();

    @Override
    public void onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mBGASwipeBackHelper.isSliding()) {
            return;
        }
        mBGASwipeBackHelper.backward();
    }

    private void initBGASwipeBackHelper(){
        mBGASwipeBackHelper = new BGASwipeBackHelper (this, new BGASwipeBackHelper.Delegate () {
            @Override
            public boolean isSupportSwipeBack() {
                return BaseActivity.this.isSupportSwipeBack ();
            }

            @Override
            public void onSwipeBackLayoutSlide(float slideOffset) {

            }

            @Override
            public void onSwipeBackLayoutCancel() {

            }

            @Override
            public void onSwipeBackLayoutExecuted() {
                mBGASwipeBackHelper.swipeBackward ();
            }
        });
        // 设置滑动返回是否可用。默认值为 true
        mBGASwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mBGASwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mBGASwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mBGASwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mBGASwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mBGASwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mBGASwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mBGASwipeBackHelper.setIsNavigationBarOverlap(false);
    }
}
