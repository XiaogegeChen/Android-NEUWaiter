package com.neuwljs.wallsmalltwo.view.widget.custom.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.neuwljs.wallsmalltwo.R;

import androidx.annotation.Nullable;

/**
 * 自定义一个标题栏
 */
public class TitleLayout extends FrameLayout implements View.OnClickListener {

    private OnClickListener mOnClickListener;

    public TitleLayout(Context context) {
        this (context, null);
    }

    public TitleLayout(Context context, @Nullable AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public TitleLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);

        // 加载布局
        LayoutInflater.from (context).inflate (R.layout.title_layout, this);

        // 拿到控件
        ImageView left = findViewById (R.id.title_layout_left);
        ImageView right = findViewById (R.id.title_layout_right);
        TextView title = findViewById (R.id.title_layout_text);

        // 拿到资源
        TypedArray typedArray = context.obtainStyledAttributes (attrs, R.styleable.TitleLayout);

        // 拿到属性
        String text = typedArray.getString (R.styleable.TitleLayout_titleText);
        boolean leftGone = typedArray.getBoolean (R.styleable.TitleLayout_leftGone, false);
        boolean rightGone = typedArray.getBoolean (R.styleable.TitleLayout_rightGone, false);

        // 设置到布局
        title.setText (text);

        left.setOnClickListener (this);
        right.setOnClickListener (this);

        if(leftGone){
            left.setOnClickListener (null);
            left.setVisibility (GONE);
        }
        if(rightGone){
            right.setOnClickListener (null);
            right.setVisibility (GONE);
        }

        // 回收资源
        typedArray.recycle ();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    /**
     * {@link android.view.View.OnClickListener}
     */
    @Override
    public void onClick(View v) {

        // 如果没设置监听就直接返回
        if(mOnClickListener == null){
            return;
        }

        switch (v.getId ()){
            case R.id.title_layout_left:
                mOnClickListener.onLeftClick ();
                break;
            case R.id.title_layout_right:
                mOnClickListener.onRightClick ();
                break;
            default:
                break;
        }
    }

    /**
     * 设置两个按钮的监听
     */
    public interface OnClickListener{
        void onLeftClick();
        void onRightClick();
    }
}
