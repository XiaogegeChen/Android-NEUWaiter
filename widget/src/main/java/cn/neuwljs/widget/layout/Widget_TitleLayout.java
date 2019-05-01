package cn.neuwljs.widget.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import cn.neuwljs.widget.R;

public class Widget_TitleLayout extends FrameLayout implements View.OnClickListener {

    private OnArrowClickListener mOnArrowClickListener;

    public Widget_TitleLayout(Context context) {
        this (context, null);
    }

    public Widget_TitleLayout(Context context, @Nullable AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public Widget_TitleLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);

        // 加载布局
        LayoutInflater.from (context).inflate (R.layout.widget_title_layout, this);

        // 拿到控件
        ImageView left = findViewById (R.id.widget_title_layout_left);
        ImageView right = findViewById (R.id.widget_title_layout_right);
        TextView title = findViewById (R.id.widget_title_layout_text);

        // 拿到资源
        TypedArray typedArray = context.obtainStyledAttributes (attrs, R.styleable.Widget_TitleLayout);

        // 拿到属性
        String text = typedArray.getString (R.styleable.Widget_TitleLayout_widget_titleText);
        boolean leftGone = typedArray.getBoolean (R.styleable.Widget_TitleLayout_widget_leftGone, false);
        boolean rightGone = typedArray.getBoolean (R.styleable.Widget_TitleLayout_widget_rightGone, false);

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

    public void setOnArrowClickListener(OnArrowClickListener onClickListener) {
        mOnArrowClickListener = onClickListener;
    }

    /**
     * {@link android.view.View.OnClickListener}
     */
    @Override
    public void onClick(View v) {

        // 如果没设置监听就直接返回
        if(mOnArrowClickListener == null){
            return;
        }

        int id = v.getId ();
        if(id == R.id.widget_title_layout_left){
            mOnArrowClickListener.onLeftClick ();
        }else if(id == R.id.widget_title_layout_right){
            mOnArrowClickListener.onRightClick ();
        }
    }

    /**
     * 设置两个按钮的监听
     */
    public interface OnArrowClickListener{
        void onLeftClick();
        void onRightClick();
    }
}
