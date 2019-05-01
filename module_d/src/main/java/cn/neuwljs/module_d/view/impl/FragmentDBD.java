package cn.neuwljs.module_d.view.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import cn.neuwljs.module_d.IndicatorFragment;
import cn.neuwljs.module_d.R;
import cn.neuwljs.module_d.presenter.impl.FragmentDBDPresenterImpl;
import cn.neuwljs.module_d.view.IFragmentDBDView;
import cn.neuwljs.widget.NoSlideViewPager;

public class FragmentDBD
        extends IndicatorFragment
        implements IFragmentDBDView {

    private TextView mTextView;
    private TextView mSerialNumberText;

    //fragmentD的ViewPager
    private ViewPager mOutViewPager;

    //当前的ViewPager实例
    private NoSlideViewPager mNoSlideViewPager;

    //业务逻辑实现的引用
    private FragmentDBDPresenterImpl mFragmentDBDPresenter;

    public void setOutViewPager(ViewPager viewPager){
        mOutViewPager = viewPager;
    }

    public void setNoSlideViewPager(NoSlideViewPager noSlideViewPager) {
        mNoSlideViewPager = noSlideViewPager;
    }

    @Nullable
    @Override
    public View onCreateView(@android.support.annotation.NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView (inflater, container, savedInstanceState);
        mFragmentDBDPresenter.attach (this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
        mFragmentDBDPresenter.detach ();
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_d_fragment_d_b_d;
    }

    @Override
    public void initView(View view) {
        mTextView = view.findViewById (R.id.module_d_fragment_d_b_d_clickable_text);
        mSerialNumberText = view.findViewById (R.id.module_d_fragment_d_b_d_serial_number_text);
    }

    @Override
    public void initData() {
        mFragmentDBDPresenter = new FragmentDBDPresenterImpl ();

        SpannableString spannableString = new SpannableString (obtainResources ()
                .getString (R.string.module_d_fragment_d_b_d_clickable));

        //"去查看"的点击事件
        spannableString.setSpan (new Clickable (v -> {
            //跳转到首页
            mOutViewPager.setCurrentItem (0,false);

            //通知首页刷新
            mFragmentDBDPresenter.notifyFragmentDAARefresh ();
        }), 4, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //"继续发布"的点击事件
        spannableString.setSpan (new Clickable (v -> {
            //跳转到第一步
            mNoSlideViewPager.setCurrentItem (0, false);

            //通知页面刷新,通知首页刷新
            mFragmentDBDPresenter.notifyFragmentDBRefresh ();
            mFragmentDBDPresenter.notifyFragmentDAARefresh ();
        }), 10, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mTextView.setText (spannableString);
        mTextView.setMovementMethod (LinkMovementMethod.getInstance());
    }

    @Override
    public void showProgressPage() {

    }

    @Override
    public void showErrorPage() {

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public FragmentDB.OnArrowClickListener getOnArrowClickListener() {
        //不处理,这一页这两个按钮不可见
        return null;
    }

    @Override
    public void refresh() {
        // 不处理，自己发起的
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshSerialNumberEvent(RefreshSerialNumberEvent event){
        String serialNumber = event.getSerialNumber ();
        if(serialNumber != null){

            // 更新显示
            String text = obtainResources ()
                    .getString (R.string.module_d_fragment_d_b_d_thank).replace ("X", serialNumber);
            mSerialNumberText.setText (text);
        }
    }

    /**
     * 通知该碎片刷新序列号的事件类
     */
    public static class RefreshSerialNumberEvent{
        /**
         * 序列号
         */
        private String SerialNumber;

        public String getSerialNumber() {
            return SerialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            SerialNumber = serialNumber;
        }
    }

    /**
     * 可点击文字的点击事件类
     */
    class Clickable extends ClickableSpan{

        private View.OnClickListener mOnClickListener;

        Clickable(View.OnClickListener listener){
            mOnClickListener = listener;
        }

        @Override
        public void onClick(@NonNull View widget) {
            mOnClickListener.onClick (widget);
        }

        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            super.updateDrawState (ds);

            //去掉下划线,改变颜色
            ds.setUnderlineText (false);
            ds.setColor (obtainResources ().getColor (R.color.module_d_text_clickable_color));
        }
    }
}
