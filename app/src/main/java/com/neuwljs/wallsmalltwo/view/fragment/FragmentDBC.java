package com.neuwljs.wallsmalltwo.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.neuwljs.wallsmalltwo.R;
import com.neuwljs.wallsmalltwo.common.BaseFragment;
import com.neuwljs.wallsmalltwo.common.IndicatorFragment;
import com.neuwljs.wallsmalltwo.model.base.Owner;
import com.neuwljs.wallsmalltwo.model.base.Publisher;
import com.neuwljs.wallsmalltwo.view.ViewContract;
import com.neuwljs.wallsmalltwo.view.widget.NoSlideViewPager;

public class FragmentDBC
        extends IndicatorFragment
        implements View.OnClickListener, ViewContract.FragmentDBCView, FragmentDB.OnArrowClickListener {

    //控件
    private Button mSubmitButton;
    private FrameLayout mProgressPage;
    private CardView mCardView;

    //当前的ViewPager实例
    private NoSlideViewPager mNoSlideViewPager;

    public void setNoSlideViewPager(NoSlideViewPager noSlideViewPager) {
        mNoSlideViewPager = noSlideViewPager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView (inflater, container, savedInstanceState);
        return view;
    }

    public FragmentDBC() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_d_b_c;
    }

    @Override
    public void initView(View view) {
        mSubmitButton = view.findViewById (R.id.fragment_d_b_c_submit);
        mProgressPage = view.findViewById (R.id.fragment_d_b_c_progress_page);
        mCardView = view.findViewById (R.id.fragment_d_b_c_content_page);
    }

    @Override
    public void initData() {
        mSubmitButton.setOnClickListener (this);
    }

    @Override
    public FragmentDB.OnArrowClickListener getOnArrowClickListener() {
        return this;
    }

    /**
     * {@link View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
        switch (v.getId ()){
            case R.id.fragment_d_b_c_submit:
                if(mNoSlideViewPager != null){
                    mNoSlideViewPager.setCurrentItem (3, true);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void showProgressPage() {
        mCardView.setVisibility (View.INVISIBLE);
        mProgressPage.setVisibility (View.VISIBLE);
    }

    @Override
    public void showErrorPage() {
    }

    @Override
    public void showToast(String message) {

    }

    /**
     * {@link FragmentDB.OnArrowClickListener}
     */
    @Override
    public void onLeftClick() {
        //跳转
        mNoSlideViewPager.setCurrentItem (1, true);
    }

    @Override
    public void onRightClick() {
    }

    @Override
    public void showOwner(Owner owner) {

    }

    @Override
    public void showPublisher(Publisher publisher) {

    }
}
