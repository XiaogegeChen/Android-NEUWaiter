package com.neuwljs.wallsmalltwo.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.neuwljs.wallsmalltwo.R;
import com.neuwljs.wallsmalltwo.common.BaseActivity;
import com.neuwljs.wallsmalltwo.presenter.impl.LoginActivityDPresenterImpl;
import com.neuwljs.wallsmalltwo.util.LogUtil;
import com.neuwljs.wallsmalltwo.util.ToastUtil;
import com.neuwljs.wallsmalltwo.view.ViewContract;
import com.neuwljs.wallsmalltwo.view.widget.custom.layout.EditListLayout;

public class LoginActivityD
        extends BaseActivity
        implements View.OnClickListener, ViewContract.LoginActivityDView {

    private CardView mCardView;
    private EditListLayout mAccountLayout;
    private EditListLayout mPasswordLayout;
    private TextView mForgetText;
    private Button mLoginButton;
    private FloatingActionButton mFloatingActionButton;

    private EditText mAccountEdit;
    private EditText mPasswordEdit;

    private LoginActivityDPresenterImpl mLoginActivityDPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        mLoginActivityDPresenter = new LoginActivityDPresenterImpl (this);
        mLoginActivityDPresenter.attach (this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        mLoginActivityDPresenter.detach ();
    }

    @Override
    public void initView() {
        mCardView = findViewById (R.id.activity_login_d_card_view);
        mAccountLayout = findViewById (R.id.activity_login_d_account);
        mPasswordLayout = findViewById (R.id.activity_login_d_password);
        mForgetText = findViewById (R.id.activity_login_d_forget);
        mLoginButton = findViewById (R.id.activity_login_d_login);
        mFloatingActionButton = findViewById (R.id.activity_login_d_fab);
    }

    @Override
    public void initData() {
        mAccountEdit = mAccountLayout.getEditText ();
        mPasswordEdit = mPasswordLayout.getEditText ();

        // 设置监听器
        mFloatingActionButton.setOnClickListener (this);
        mLoginButton.setOnClickListener (this);

        if(Build.VERSION.SDK_INT >= 21){

            // 进出场动画
            Explode explode = new Explode ();
            explode.setDuration (500);

            getWindow ().setEnterTransition (explode);
            getWindow ().setExitTransition (explode);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login_d;
    }

    @Override
    public int getStatusBarColor() {
        return getContext ().getResources ().getColor (R.color.colorAccent);
    }

    @Override
    public boolean isSupportSwipeBack() {
        //这个界面支持侧滑返回
        return true;
    }

    /**
     * {@link android.view.View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
        switch (v.getId ()){
            case R.id.activity_login_d_fab:
                Intent intent = new Intent (this, RegisterActivityD.class);

                if(Build.VERSION.SDK_INT >= 21){

                    // 移除转场效果，调转到注册页面
                    getWindow ().setEnterTransition (null);
                    getWindow ().setExitTransition (null);

                    // 公共元素
                    ActivityOptionsCompat options = ActivityOptionsCompat
                            .makeSceneTransitionAnimation (this, mFloatingActionButton, mFloatingActionButton.getTransitionName ());

                    // 启动注册页面
                    startActivity (intent, options.toBundle ());
                }else{
                    startActivity (intent);
                }
                break;
            case R.id.activity_login_d_login:

                // 开始登陆操作
                mLoginActivityDPresenter.login ();
                break;

            default:
                break;
        }
    }

    @Override
    public void showProgressPage() {

    }

    @Override
    public void showErrorPage() {

    }

    @Override
    public void showToast(String message) {
        ToastUtil.showToast (this, message);
    }
}
