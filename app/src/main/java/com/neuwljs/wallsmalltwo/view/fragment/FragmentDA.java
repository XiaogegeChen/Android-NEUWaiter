package com.neuwljs.wallsmalltwo.view.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.neuwljs.wallsmalltwo.R;
import com.neuwljs.wallsmalltwo.adapter.viewpager.MyFragmentPagerAdapter;
import com.neuwljs.wallsmalltwo.adapter.viewpager.MyPagerAdapter;
import com.neuwljs.wallsmalltwo.common.BaseFragment;
import com.neuwljs.wallsmalltwo.util.LogUtil;
import com.neuwljs.wallsmalltwo.view.widget.BannerViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.neuwljs.wallsmalltwo.model.Constant.BANNER_INTERVAL;

public class FragmentDA
        extends BaseFragment
        implements View.OnClickListener {

    private static final String TAG = "FragmentDA";

    private static final int BANNER_MESSAGE_WHAT = 0;

    private BannerViewPager mBannerViewPager;
    private TabLayout mTabLayout;
    private ViewPager mPropertyViewPager;
    private FloatingActionButton mFloatingActionButton;

    // 轮播图图集
    private List<Bitmap> mBitmapList;

    // 轮播图的指示点集合
    private List<View> mBannerPointList;

    // 轮播图页面监听器
    private MyOnPageChangeListener mBannerPageChangeListener;

    // 物品启示页面监听器
    private MyOnPageChangeListener mPropertyPageChangeListener;

    // mFloatingActionButton的监听器
    private OnFloatingActionButtonClickListener mOnFloatingActionButtonClickListener;

    // 轮播图定时器和定时任务
    private Timer mBannerTimer;
    private TimerTask mBannerTimerTask;

    @SuppressLint("HandlerLeak")
    private Handler mBannerHandler = new Handler (){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case BANNER_MESSAGE_WHAT:

                    // 滑动到下一页
                    int currentItem = mBannerViewPager.getCurrentItem ();
                    if(currentItem < mBitmapList.size () - 1){
                        mBannerViewPager.setCurrentItem (currentItem + 1, true);
                    }else{
                        mBannerViewPager.setCurrentItem (0, true);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    // tabLayout标题
    private String[] mTabLayoutTitles;

    // mPropertyViewPager的fragment集合
    private List<BaseFragment> mFragmentList;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_d_a;
    }

    @Override
    public void initView(View view) {
        mBannerViewPager = view.findViewById (R.id.fragment_d_a_picture_view_pager);
        mTabLayout = view.findViewById (R.id.fragment_d_a_tab);
        mPropertyViewPager = view.findViewById (R.id.fragment_d_a_lost_and_found_view_pager);
        mFloatingActionButton = view.findViewById (R.id.fragment_d_a_fab_refresh);

        // 轮播图指示器初始化
        mBannerPointList = new ArrayList<> ();
        mBannerPointList.add (view.findViewById (R.id.fragment_d_a_banner_point_0));
        mBannerPointList.add (view.findViewById (R.id.fragment_d_a_banner_point_1));
        mBannerPointList.add (view.findViewById (R.id.fragment_d_a_banner_point_2));
        mBannerPointList.add (view.findViewById (R.id.fragment_d_a_banner_point_3));

        // 设置轮播图在第一页
        mBannerPointList.get (0).setBackground (obtainResources ().getDrawable (R.drawable.bg_fragment_d_a_banner_blue_point));
    }

    @Override
    public void initData() {
        // 轮播图数据
        mBitmapList = new ArrayList<> ();
        mBitmapList.add (BitmapFactory.decodeResource (obtainResources (), R.drawable.ic_test_1));
        mBitmapList.add (BitmapFactory.decodeResource (obtainResources (), R.drawable.ic_test_2));
        mBitmapList.add (BitmapFactory.decodeResource (obtainResources (), R.drawable.ic_test_3));
        mBitmapList.add (BitmapFactory.decodeResource (obtainResources (), R.drawable.ic_test_4));

        // 轮播图设置适配器
        mBannerViewPager.setAdapter (new MyPagerAdapter (obtainContext (), mBitmapList));

        // 轮播图定时器初始化
        startBannerTimer ();

        // 轮播图设置监听器
        mBannerPageChangeListener = new MyOnPageChangeListener () {
            @Override
            public void onPageSelected(int i) {
                // 设置选中点的位置
                for(int j=0;j<mBannerPointList.size ();j++){
                    if(j == i){
                        mBannerPointList.get (j).setBackground (obtainResources ().getDrawable (R.drawable.bg_fragment_d_a_banner_blue_point));
                    }else{
                        mBannerPointList.get (j).setBackground (obtainResources ().getDrawable (R.drawable.bg_fragment_d_a_banner_normal_point));
                    }
                }

                // 取消上一个定时器并重新开启定时器
                if(mBannerTimer != null){
                    mBannerTimer.cancel ();
                    mBannerTimer = new Timer ();
                    startBannerTimer ();
                }
            }
        };
        mBannerViewPager.addOnPageChangeListener (mBannerPageChangeListener);

        // mPropertyViewPager添加fragment
        mFragmentList = new ArrayList<> ();
        mFragmentList.add (new FragmentDAA ());
        mFragmentList.add (new FragmentDAB ());

        // tabLayout设置
        mTabLayoutTitles = obtainResources ().getStringArray (R.array.fragment_d_a_tab_titles);
        for(String title : mTabLayoutTitles){
            mTabLayout.addTab (mTabLayout.newTab ().setText (title));
        }

        // mPropertyViewPager设置适配器
        mPropertyViewPager.setAdapter (new MyFragmentPagerAdapter (getFragmentManager (), mFragmentList ) {
            @Override
            public List<String> getTitle() {
                return Arrays.asList (mTabLayoutTitles);
            }
        });

        // mPropertyViewPager页面监听
        mPropertyPageChangeListener = new MyOnPageChangeListener () {
            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        mOnFloatingActionButtonClickListener = ((FragmentDAA)(mFragmentList.get (0)))
                                .getOnFloatingActionButtonClickListener ();
                        LogUtil.d (TAG, "onPageSelected: " + "change listener to A");
                        break;
                    case 1:
                        mOnFloatingActionButtonClickListener = ((FragmentDAB)(mFragmentList.get (1)))
                                .getOnFloatingActionButtonClickListener ();
                        LogUtil.d (TAG, "onPageSelected: " + "change listener to B");
                        break;
                    default:
                        break;
                }
            }
        };
        mPropertyViewPager.addOnPageChangeListener (mPropertyPageChangeListener);

        // mPropertyViewPager绑定
        mTabLayout.setupWithViewPager (mPropertyViewPager);

        // mFloatingActionButton监听器
        mFloatingActionButton.setOnClickListener (this);

        // 初始设置FloatingActionButton的监听器是第一页的
        mOnFloatingActionButtonClickListener = ((FragmentDAA)(mFragmentList.get (0)))
                .getOnFloatingActionButtonClickListener ();
    }

    /**
     * {@link android.view.View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
        switch (v.getId ()){
            case R.id.fragment_d_a_fab_refresh:
                if(mOnFloatingActionButtonClickListener != null){
                    mOnFloatingActionButtonClickListener.onFloatingActionButtonClick ();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 开启轮播图的定时器
     */
    private void startBannerTimer(){
        if(mBannerTimer == null){
            mBannerTimer = new Timer ();
        }

        mBannerTimerTask = new TimerTask () {
            @Override
            public void run() {

                // 发送消息通知handler滑动到下一页
                Message message = mBannerHandler.obtainMessage ();
                message.what = BANNER_MESSAGE_WHAT;
                mBannerHandler.sendMessage (message);
            }
        };

        mBannerTimer.schedule (mBannerTimerTask, BANNER_INTERVAL);
    }

    /**
     * 去掉viewPager的两个空实现
     */
    public abstract class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    }

    /**
     * floatingActionButton的监听器，具体由{@link #mFragmentList 的成员实现}
     */
    public interface OnFloatingActionButtonClickListener{
        /**
         * FloatingActionButton 的点击事件
         */
        void onFloatingActionButtonClick();
    }
}
