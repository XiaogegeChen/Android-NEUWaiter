package cn.neuwljs.module_d.view.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.badoualy.stepperindicator.StepperIndicator;

import java.util.ArrayList;
import java.util.List;

import cn.neuwljs.common.adapter.MyFragmentPagerAdapter;
import cn.neuwljs.common.base.BaseFragment;
import cn.neuwljs.common.util.LogUtil;
import cn.neuwljs.module_d.R;
import cn.neuwljs.widget.NoSlideViewPager;

public class FragmentDB extends BaseFragment
        implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private static final String TAG = "FragmentDB";

    private StepperIndicator mStepperIndicator;
    private NoSlideViewPager mNoSlideViewPager;
    private List<BaseFragment> mFragmentList;
    private LinearLayout mLeftLinearLayout;
    private LinearLayout mRightLinearLayout;

    //fragmentD的viewpager
    private ViewPager mViewPager;

    //左右两个指示箭头点击的监听器
    private OnArrowClickListener mOnArrowClickListener;

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView (inflater, container, savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_d_fragment_d_b;
    }

    @Override
    public void initView(View view) {
        mStepperIndicator = view.findViewById (R.id.module_d_fragment_d_b_stepper);
        mNoSlideViewPager = view.findViewById (R.id.module_d_fragment_d_b_view_pager);
        mLeftLinearLayout = view.findViewById (R.id.module_d_fragment_d_b_left);
        mRightLinearLayout = view.findViewById (R.id.module_d_fragment_d_b_right);
    }

    @Override
    public void initData() {
        //实例化viewpager的fragment
        mFragmentList = new ArrayList<> ();
        FragmentDBA fragmentDBA = new FragmentDBA ();
        FragmentDBB fragmentDBB = new FragmentDBB ();
        FragmentDBC fragmentDBC = new FragmentDBC ();
        FragmentDBD fragmentDBD = new FragmentDBD ();
        mFragmentList.add (fragmentDBA);
        mFragmentList.add (fragmentDBB);
        mFragmentList.add (fragmentDBC);
        mFragmentList.add (fragmentDBD);

        //绑定步骤器
        mNoSlideViewPager.setAdapter (new MyFragmentPagerAdapter (getFragmentManager (),
                mFragmentList){

            @Override
            public List<String> getTitle() {
                return null;
            }
        });
        mStepperIndicator.setViewPager (mNoSlideViewPager, true);

        //设置监听器
        mNoSlideViewPager.addOnPageChangeListener (this);
        mLeftLinearLayout.setOnClickListener (this);
        mRightLinearLayout.setOnClickListener (this);

        //先让左侧不可见
        mLeftLinearLayout.setVisibility (View.INVISIBLE);

        // 拿到当前ViewPager,用于跳转
        fragmentDBA.setNoSlideViewPager (mNoSlideViewPager);
        fragmentDBB.setNoSlideViewPager (mNoSlideViewPager);
        fragmentDBC.setNoSlideViewPager (mNoSlideViewPager);
        fragmentDBD.setNoSlideViewPager (mNoSlideViewPager);

        //最后一页拿到fragmentD的viewpager,用于跳转到首页,拿到当前ViewPager,用于转到第一页
        fragmentDBD.setOutViewPager (mViewPager);

        //
        mOnArrowClickListener = ((FragmentDBA) mFragmentList.get (0)).getOnArrowClickListener ();
    }

    /**
     * {@link ViewPager.OnPageChangeListener}
     */
    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {
        //实现在每一页正确显示底部按钮
        switch (i){
            case 0:
                mLeftLinearLayout.setVisibility (View.INVISIBLE);
                mRightLinearLayout.setVisibility (View.VISIBLE);

                LogUtil.d (TAG, "FragmentDBA: 0");

                // 更改监听器
                mOnArrowClickListener = ((FragmentDBA) mFragmentList.get (0)).getOnArrowClickListener ();
                break;
            case 1:
                mLeftLinearLayout.setVisibility (View.VISIBLE);
                mRightLinearLayout.setVisibility (View.VISIBLE);

                LogUtil.d (TAG, "FragmentDBB: 1");

                // 更改监听器
                mOnArrowClickListener = ((FragmentDBB) mFragmentList.get (1)).getOnArrowClickListener ();
                break;
            case 2:
                mLeftLinearLayout.setVisibility (View.VISIBLE);
                mRightLinearLayout.setVisibility (View.INVISIBLE);

                LogUtil.d (TAG, "FragmentDBC: 2");

                // 更改监听器
                mOnArrowClickListener = ((FragmentDBC) mFragmentList.get (2)).getOnArrowClickListener ();
                break;
            case 3:
                mLeftLinearLayout.setVisibility (View.INVISIBLE);
                mRightLinearLayout.setVisibility (View.INVISIBLE);

                LogUtil.d (TAG, "FragmentDBD: 3");

                // 更改监听器
                mOnArrowClickListener = ((FragmentDBD) mFragmentList.get (3)).getOnArrowClickListener ();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    /**
     * {@link View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
        int id = v.getId ();
        if (id == R.id.module_d_fragment_d_b_left){

            // 执行内部fragment内的逻辑,内部更改页面位置
            if(mOnArrowClickListener != null){
                mOnArrowClickListener.onLeftClick ();
            }
        }else if(id == R.id.module_d_fragment_d_b_right){

            // 执行内部fragment内的逻辑,内部更改页面位置
            if(mOnArrowClickListener != null){
                mOnArrowClickListener.onRightClick ();
            }
        }
    }

    /**
     * 左右两个指示箭头点击的回调
     */
    public interface OnArrowClickListener{
        /**
         * 左边箭头被点击
         */
        void onLeftClick();

        /**
         * 右边箭头被点击
         */
        void onRightClick();
    }
}
