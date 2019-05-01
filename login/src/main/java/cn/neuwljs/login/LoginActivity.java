package cn.neuwljs.login;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.neuwljs.widget.layout.Widget_EditLayout;

import static cn.neuwljs.login.Constants.sLoginCallback;

public class LoginActivity
        extends AppCompatActivity
        implements View.OnClickListener {

    private CardView mCardView;
    private Widget_EditLayout mAccountLayout;
    private Widget_EditLayout mPasswordLayout;
    private TextView mForgetText;
    private Button mLoginButton;
    private FloatingActionButton mFloatingActionButton;

    private EditText mAccountEdit;
    private EditText mPasswordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        // 改变状态栏颜色
        if(Build.VERSION.SDK_INT >= 21){
            getWindow ().setStatusBarColor (getResources ().getColor (R.color.login_accent_color));
        }

        setContentView (R.layout.login_activity_login);

        initView ();
        initData ();
    }

    private void initView(){
        mCardView = findViewById (R.id.login_activity_login_d_card_view);
        mAccountLayout = findViewById (R.id.login_activity_login_d_account);
        mPasswordLayout = findViewById (R.id.login_activity_login_d_password);
        mForgetText = findViewById (R.id.login_activity_login_d_forget);
        mLoginButton = findViewById (R.id.login_activity_login_d_login);
        mFloatingActionButton = findViewById (R.id.login_activity_login_d_fab);
    }

    private void initData(){
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

    /**
     * {@link android.view.View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
        int id = v.getId ();

        if (id == R.id.login_activity_login_d_fab) {
            Intent intent = new Intent (this, RegisterActivity.class);

            if (Build.VERSION.SDK_INT >= 21) {

                // 移除转场效果，调转到注册页面
                getWindow ().setEnterTransition (null);
                getWindow ().setExitTransition (null);

                // 公共元素
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation (this, mFloatingActionButton, mFloatingActionButton.getTransitionName ());

                // 启动注册页面
                startActivity (intent, options.toBundle ());
            } else {
                startActivity (intent);
            }
        } else if (id == R.id.login_activity_login_d_login) {
            // TODO 回调，保存用户信息，finish活动
            sLoginCallback.onFailure ();
            finish ();
        }
    }
}
