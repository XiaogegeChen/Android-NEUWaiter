package cn.neuwljs.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;

public class RegisterActivity
        extends AppCompatActivity
        implements View.OnClickListener, Transition.TransitionListener {

    private CardView mCardView;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);

        // 改变状态栏颜色
        if(Build.VERSION.SDK_INT >= 21){
            getWindow ().setStatusBarColor (getResources ().getColor (R.color.login_accent_color));
        }

        setContentView (R.layout.login_activity_register);

        initView ();
        initData ();
    }

    private void initView() {
        mFloatingActionButton = findViewById (R.id.login_activity_register_fab);
        mCardView = findViewById (R.id.login_activity_register_card_view);
    }

    private void initData() {

        // 进场动画
        if(Build.VERSION.SDK_INT > 21){
            ChangeBounds changeBounds = new ChangeBounds ();
            ArcMotion arcMotion = new ArcMotion ();

            arcMotion.setMaximumAngle (0);
            arcMotion.setMinimumHorizontalAngle (90f);
            arcMotion.setMinimumVerticalAngle (60f);

            changeBounds.setPathMotion (arcMotion);
            changeBounds.setInterpolator (new LinearOutSlowInInterpolator ());
            changeBounds.setDuration (300);
            changeBounds.addListener (this);

            getWindow ().setSharedElementEnterTransition (changeBounds);
        }

        // 设置监听器
        mFloatingActionButton.setOnClickListener (this);
    }

    /**
     * {@link android.view.View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
        int id = v.getId ();

        if (id == R.id.login_activity_register_fab) {
            animateRevealClose ();
        }
    }

    /**
     * {@link android.transition.Transition.TransitionListener}
     */
    @Override
    public void onTransitionStart(Transition transition) {

        // 隐藏mCardView
        mCardView.setVisibility (View.GONE);
    }

    @Override
    public void onTransitionEnd(Transition transition) {

        // 移除监听
        transition.removeListener (this);

        // 显示覆盖动画
        animateRevealShow();
    }

    @Override
    public void onTransitionCancel(Transition transition) {
    }

    @Override
    public void onTransitionPause(Transition transition) {
    }

    @Override
    public void onTransitionResume(Transition transition) {
    }

    /**
     * 显示覆盖动画
     */
    private void animateRevealShow(){
        if(Build.VERSION.SDK_INT >= 21){

            // 覆盖动画
            Animator animator = ViewAnimationUtils.createCircularReveal (mCardView,
                    mCardView.getWidth ()/2,
                    0,
                    (float) (mFloatingActionButton.getWidth ()/2),
                    mCardView.getHeight ());
            animator.setDuration (500);
            animator.setInterpolator (new AccelerateDecelerateInterpolator ());

            animator.addListener (new AnimatorListenerAdapter () {

                @Override
                public void onAnimationStart(Animator animation) {

                    // 显示mCardView
                    mCardView.setVisibility (View.VISIBLE);
                    super.onAnimationStart (animation);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd (animation);
                }
            });
            animator.start ();
        }
    }

    /**
     * 关闭覆盖动画
     */
    private void animateRevealClose(){

        if(Build.VERSION.SDK_INT >= 21){

            // 覆盖动画
            Animator animator = ViewAnimationUtils.createCircularReveal (mCardView,
                    mCardView.getWidth ()/2,
                    0,
                    mCardView.getHeight (),
                    (float) (mFloatingActionButton.getWidth ()/2));
            animator.setDuration (500);
            animator.setInterpolator (new AccelerateDecelerateInterpolator ());

            animator.addListener (new AnimatorListenerAdapter () {

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart (animation);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    //隐藏mCardView，回退，改变fab的图标
                    mCardView.setVisibility (View.INVISIBLE);
                    super.onAnimationEnd(animation);
                    mFloatingActionButton.setImageResource (R.drawable.login_plus);

                    // 调用这个方法保证退出和进入时动画一致性
                    supportFinishAfterTransition ();
                }
            });
            animator.start ();
        }
    }

    @Override
    public void onBackPressed() {

        // 回退到登陆界面
        animateRevealClose ();
    }

}
