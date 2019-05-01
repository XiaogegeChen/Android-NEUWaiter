package cn.neuwljs.module_d.view.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import cn.neuwljs.common.util.ToastUtil;
import cn.neuwljs.module_d.IndicatorFragment;
import cn.neuwljs.module_d.R;
import cn.neuwljs.module_d.model.base.LostPropertyType;
import cn.neuwljs.module_d.presenter.impl.FragmentDBAPresenterImpl;
import cn.neuwljs.module_d.view.IFragmentDBAView;
import cn.neuwljs.widget.NoSlideViewPager;

import static cn.neuwljs.module_d.Constants.NO_TYPE_SELECTED;

public class FragmentDBA
        extends IndicatorFragment
        implements View.OnClickListener, FragmentDB.OnArrowClickListener, IFragmentDBAView {

    //控件
    private FrameLayout mPressedStudentCardLayout;
    private FrameLayout mStudentCardLayout;
    private FrameLayout mPressedIdCardLayout;
    private FrameLayout mIdCardLayout;
    private FrameLayout mPressedBankCardLayout;
    private FrameLayout mBankCardLayout;
    private FrameLayout mPressedElseLayout;
    private FrameLayout mElseLayout;

    //业务逻辑实现的引用
    private FragmentDBAPresenterImpl mFragmentDBAPresenter;

    //当前的ViewPager实例
    private NoSlideViewPager mNoSlideViewPager;

    //丢失物品的类别
    private String mLostPropertyType;

    public void setNoSlideViewPager(NoSlideViewPager noSlideViewPager) {
        mNoSlideViewPager = noSlideViewPager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView (inflater, container, savedInstanceState);

        //逻辑与视图绑定
        mFragmentDBAPresenter.attach (this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        mFragmentDBAPresenter.detach ();
    }

    public FragmentDBA(){
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_d_fragment_d_b_a;
    }

    @Override
    public void initView(View view) {
        mStudentCardLayout = view.findViewById (R.id.module_d_fragment_d_b_a_student_card_layout);
        mPressedStudentCardLayout = view.findViewById (R.id.module_d_fragment_d_b_a_student_card_layout_pre);
        mPressedIdCardLayout = view.findViewById (R.id.module_d_fragment_d_b_a_id_card_layout_pre);
        mIdCardLayout = view.findViewById (R.id.module_d_fragment_d_b_a_id_card_layout);
        mPressedBankCardLayout = view.findViewById (R.id.module_d_fragment_d_b_a_bank_card_layout_pre);
        mBankCardLayout = view.findViewById (R.id.module_d_fragment_d_b_a_bank_card_layout);
        mPressedElseLayout = view.findViewById (R.id.module_d_fragment_d_b_a_else_layout_pre);
        mElseLayout = view.findViewById (R.id.module_d_fragment_d_b_a_else_layout);
    }

    @Override
    public void initData() {
        mStudentCardLayout.setOnClickListener (this);
        mIdCardLayout.setOnClickListener (this);
        mBankCardLayout.setOnClickListener (this);
        mElseLayout.setOnClickListener (this);
        mPressedStudentCardLayout.setOnClickListener (this);
        mPressedIdCardLayout.setOnClickListener (this);
        mPressedBankCardLayout.setOnClickListener (this);
        mPressedElseLayout.setOnClickListener (this);

        mFragmentDBAPresenter = new FragmentDBAPresenterImpl ();
    }

    /**
     * {@link View.OnClickListener}
     */
    @Override
    public void onClick(View v) {

        int id = v.getId ();
        if(id == R.id.module_d_fragment_d_b_a_student_card_layout){
            //设置视图
            mPressedStudentCardLayout.setVisibility (View.VISIBLE);
            mStudentCardLayout.setVisibility (View.INVISIBLE);
            mPressedIdCardLayout.setVisibility (View.INVISIBLE);
            mIdCardLayout.setVisibility (View.VISIBLE);
            mPressedBankCardLayout.setVisibility (View.INVISIBLE);
            mBankCardLayout.setVisibility (View.VISIBLE);
            mPressedElseLayout.setVisibility (View.INVISIBLE);
            mElseLayout.setVisibility (View.VISIBLE);

            //失物类型赋值
            mLostPropertyType = LostPropertyType.STUDENT_CARD;

            //跳转
            onRightClick ();
        }else if(id == R.id.module_d_fragment_d_b_a_student_card_layout_pre){
            mPressedStudentCardLayout.setVisibility (View.INVISIBLE);
            mStudentCardLayout.setVisibility (View.VISIBLE);
            mPressedIdCardLayout.setVisibility (View.INVISIBLE);
            mIdCardLayout.setVisibility (View.VISIBLE);
            mPressedBankCardLayout.setVisibility (View.INVISIBLE);
            mBankCardLayout.setVisibility (View.VISIBLE);
            mPressedElseLayout.setVisibility (View.INVISIBLE);
            mElseLayout.setVisibility (View.VISIBLE);

            //失物类型赋值
            mLostPropertyType = null;
        }else if(id == R.id.module_d_fragment_d_b_a_id_card_layout){
            mPressedStudentCardLayout.setVisibility (View.INVISIBLE);
            mStudentCardLayout.setVisibility (View.VISIBLE);
            mPressedIdCardLayout.setVisibility (View.VISIBLE);
            mIdCardLayout.setVisibility (View.INVISIBLE);
            mPressedBankCardLayout.setVisibility (View.INVISIBLE);
            mBankCardLayout.setVisibility (View.VISIBLE);
            mPressedElseLayout.setVisibility (View.INVISIBLE);
            mElseLayout.setVisibility (View.VISIBLE);

            //失物类型赋值
            mLostPropertyType = LostPropertyType.ID_CARD;

            //跳转
            onRightClick ();
        }else if(id == R.id.module_d_fragment_d_b_a_id_card_layout_pre){
            mPressedStudentCardLayout.setVisibility (View.INVISIBLE);
            mStudentCardLayout.setVisibility (View.VISIBLE);
            mPressedIdCardLayout.setVisibility (View.INVISIBLE);
            mIdCardLayout.setVisibility (View.VISIBLE);
            mPressedBankCardLayout.setVisibility (View.INVISIBLE);
            mBankCardLayout.setVisibility (View.VISIBLE);
            mPressedElseLayout.setVisibility (View.INVISIBLE);
            mElseLayout.setVisibility (View.VISIBLE);

            //失物类型赋值
            mLostPropertyType = null;
        }else if(id == R.id.module_d_fragment_d_b_a_bank_card_layout){
            mPressedStudentCardLayout.setVisibility (View.INVISIBLE);
            mStudentCardLayout.setVisibility (View.VISIBLE);
            mPressedIdCardLayout.setVisibility (View.INVISIBLE);
            mIdCardLayout.setVisibility (View.VISIBLE);
            mPressedBankCardLayout.setVisibility (View.VISIBLE);
            mBankCardLayout.setVisibility (View.INVISIBLE);
            mPressedElseLayout.setVisibility (View.INVISIBLE);
            mElseLayout.setVisibility (View.VISIBLE);

            //失物类型赋值
            mLostPropertyType = LostPropertyType.BANK_CARD;

            //跳转
            onRightClick ();
        }else if(id == R.id.module_d_fragment_d_b_a_bank_card_layout_pre){
            mPressedStudentCardLayout.setVisibility (View.INVISIBLE);
            mStudentCardLayout.setVisibility (View.VISIBLE);
            mPressedIdCardLayout.setVisibility (View.INVISIBLE);
            mIdCardLayout.setVisibility (View.VISIBLE);
            mPressedBankCardLayout.setVisibility (View.INVISIBLE);
            mBankCardLayout.setVisibility (View.VISIBLE);
            mPressedElseLayout.setVisibility (View.INVISIBLE);
            mElseLayout.setVisibility (View.VISIBLE);

            //失物类型赋值
            mLostPropertyType = null;
        }else if(id == R.id.module_d_fragment_d_b_a_else_layout){
            mPressedStudentCardLayout.setVisibility (View.INVISIBLE);
            mStudentCardLayout.setVisibility (View.VISIBLE);
            mPressedIdCardLayout.setVisibility (View.INVISIBLE);
            mIdCardLayout.setVisibility (View.VISIBLE);
            mPressedBankCardLayout.setVisibility (View.INVISIBLE);
            mBankCardLayout.setVisibility (View.VISIBLE);
            mPressedElseLayout.setVisibility (View.VISIBLE);
            mElseLayout.setVisibility (View.INVISIBLE);

            //失物类型赋值
            mLostPropertyType = LostPropertyType.OTHER;

            //跳转
            onRightClick ();
        }else if(id == R.id.module_d_fragment_d_b_a_else_layout_pre){
            mPressedStudentCardLayout.setVisibility (View.INVISIBLE);
            mStudentCardLayout.setVisibility (View.VISIBLE);
            mPressedIdCardLayout.setVisibility (View.INVISIBLE);
            mIdCardLayout.setVisibility (View.VISIBLE);
            mPressedBankCardLayout.setVisibility (View.INVISIBLE);
            mBankCardLayout.setVisibility (View.VISIBLE);
            mPressedElseLayout.setVisibility (View.INVISIBLE);
            mElseLayout.setVisibility (View.VISIBLE);

            //失物类型赋值
            mLostPropertyType = null;
        }
    }

    @Override
    public FragmentDB.OnArrowClickListener getOnArrowClickListener() {
        return this;
    }

    @Override
    public void refresh() {
        // 所有的类型都不选中
        mPressedStudentCardLayout.setVisibility (View.INVISIBLE);
        mStudentCardLayout.setVisibility (View.VISIBLE);
        mPressedIdCardLayout.setVisibility (View.INVISIBLE);
        mIdCardLayout.setVisibility (View.VISIBLE);
        mPressedBankCardLayout.setVisibility (View.INVISIBLE);
        mBankCardLayout.setVisibility (View.VISIBLE);
        mPressedElseLayout.setVisibility (View.INVISIBLE);
        mElseLayout.setVisibility (View.VISIBLE);

        // 类型置为空
        mLostPropertyType = null;
    }

    /**
     * {@link FragmentDB.OnArrowClickListener}
     */
    @Override
    public void onLeftClick() {
    }

    @Override
    public void onRightClick() {

        // 通知fragmentDBC更改类型,跳转到下一页
        if(mLostPropertyType == null){
            showToast (NO_TYPE_SELECTED);
        }else{
            mFragmentDBAPresenter.notifyPropertyRefresh (mLostPropertyType);
            mNoSlideViewPager.setCurrentItem (1, true);
        }
    }

    /**
     * {@link IFragmentDBAView}
     */
    @Override
    public void showProgressPage() {
    }

    @Override
    public void showErrorPage() {
    }

    @Override
    public void showToast(String message) {
        ToastUtil.showToast (obtainContext (), message);
    }
}
