package cn.neuwljs.widget.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.neuwljs.widget.R;

public class Widget_ChooseLayout extends LinearLayout {

    private TextView mContent;

    public Widget_ChooseLayout(Context context) {
        this (context, null);
    }

    public Widget_ChooseLayout(Context context, AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public Widget_ChooseLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);

        //加载布局
        LayoutInflater.from (context).inflate (R.layout.widget_choose_layout, this);

        // 拿到控件
        TextView mName = findViewById (R.id.widget_choose_layout_item_name);
        mContent = findViewById (R.id.widget_choose_layout_item_content);

        // 拿到资源和属性
        TypedArray typedArray = context.obtainStyledAttributes (attrs, R.styleable.Widget_ChooseLayout);

        String nameText = typedArray.getString (R.styleable.Widget_ChooseLayout_widget_choose_layout_name);

        // 设置属性
        mName.setText (nameText);

        // 回收资源
        typedArray.recycle ();
    }

    public TextView getTextView(){
        return mContent;
    }
}
