package cn.neuwljs.module_d.view.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.neuwljs.common.base.BaseFragment;
import cn.neuwljs.common.util.ToastUtil;
import cn.neuwljs.login.Callback;
import cn.neuwljs.login.Login;
import cn.neuwljs.login.User;
import cn.neuwljs.module_d.R;
import cn.neuwljs.module_d.presenter.impl.FragmentDDPresenterImpl;
import cn.neuwljs.module_d.view.IFragmentDDView;
import cn.neuwljs.widget.layout.Widget_ListLayout;
import de.hdodenhof.circleimageview.CircleImageView;

import static cn.neuwljs.module_d.Constants.LOGIN_FAILED;

public class FragmentDD
        extends BaseFragment
        implements IFragmentDDView, View.OnClickListener{

    private static final String TAG = "FragmentDD";

    private CircleImageView mUserHeadImage;
    private TextView mUserName;
    private ImageView mSettingImage;
    private Widget_ListLayout mPublishLayout;
    private Widget_ListLayout mLostLayout;
    private Widget_ListLayout mMyCard;
    private Widget_ListLayout mDraftsLayout;
    private Widget_ListLayout mShareLayout;

    private FragmentDDPresenterImpl mFragmentDDPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView (inflater, container, savedInstanceState);
        mFragmentDDPresenter.attach (this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        mFragmentDDPresenter.detach ();
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_d_fragment_d_d;
    }

    @Override
    public void initView(View view) {
        mUserHeadImage = view.findViewById (R.id.module_d_fragment_d_d_user_head_image);
        mUserName = view.findViewById (R.id.module_d_fragment_d_d_user_name);
        mSettingImage = view.findViewById (R.id.module_d_fragment_d_d_set);
        mPublishLayout = view.findViewById (R.id.module_d_fragment_d_d_publish);
        mLostLayout = view.findViewById (R.id.module_d_fragment_d_d_lost);
        mMyCard = view.findViewById (R.id.module_d_fragment_d_d_my_card);
        mShareLayout = view.findViewById (R.id.module_d_fragment_d_d_share);
        mDraftsLayout = view.findViewById (R.id.module_d_fragment_d_d_drafts);
    }

    @Override
    public void initData() {
        mFragmentDDPresenter = new FragmentDDPresenterImpl ();

        // 设置监听器
        mUserHeadImage.setOnClickListener (this);
        mPublishLayout.setOnClickListener (this);
        mSettingImage.setOnClickListener (this);
        mLostLayout.setOnClickListener (this);
        mMyCard.setOnClickListener (this);
        mDraftsLayout.setOnClickListener (this);
        mShareLayout.setOnClickListener (this);
    }

    /**
     * {@link View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
        int id = v.getId ();
        if(id == R.id.module_d_fragment_d_d_set){
            Intent intent = new Intent (obtainActivity (), SettingActivity.class);
            startActivity (intent);
        }else if(id == R.id.module_d_fragment_d_d_publish){
            Login.checkLogin (new Callback () {
                @Override
                public void onSuccess(User user) {

                }

                @Override
                public void onFailure() {
                    ToastUtil.showToast (obtainContext (), LOGIN_FAILED);
                }
            });
        }else if(id == R.id.module_d_fragment_d_d_lost){
            Login.checkLogin (new Callback () {
                @Override
                public void onSuccess(User user) {

                }

                @Override
                public void onFailure() {
                    ToastUtil.showToast (obtainContext (), LOGIN_FAILED);
                }
            });
        }else if(id == R.id.module_d_fragment_d_d_my_card){
            Login.checkLogin (new Callback () {
                @Override
                public void onSuccess(User user) {

                }

                @Override
                public void onFailure() {
                    ToastUtil.showToast (obtainContext (), LOGIN_FAILED);
                }
            });
        }else if(id == R.id.module_d_fragment_d_d_drafts){
            Login.checkLogin (new Callback () {
                @Override
                public void onSuccess(User user) {

                }

                @Override
                public void onFailure() {
                    ToastUtil.showToast (obtainContext (), LOGIN_FAILED);
                }
            });
        }else if(id == R.id.module_d_fragment_d_d_share){
            Login.checkLogin (new Callback () {
                @Override
                public void onSuccess(User user) {

                }

                @Override
                public void onFailure() {
                    ToastUtil.showToast (obtainContext (), LOGIN_FAILED);
                }
            });
        }else if(id == R.id.module_d_fragment_d_d_user_head_image || id == R.id.module_d_fragment_d_d_user_name){
            mFragmentDDPresenter.gotoUserActivity ();
        }
    }

    /**
     * {@link IFragmentDDView}
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