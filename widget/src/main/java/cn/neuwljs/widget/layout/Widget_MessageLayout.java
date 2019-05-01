package cn.neuwljs.widget.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.neuwljs.widget.R;

public class Widget_MessageLayout extends LinearLayout {

    private EditText mContent;

    public Widget_MessageLayout(Context context) {
        this (context, null);
    }

    public Widget_MessageLayout(Context context, AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public Widget_MessageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);

        //加载布局
        LayoutInflater.from (context).inflate (R.layout.widget_message_layout, this);

        // 拿到控件
        TextView mName = findViewById (R.id.widget_message_layout_item_name);
        mContent = findViewById (R.id.widget_message_layout_item_content);

        // 拿到资源和属性
        TypedArray typedArray = context.obtainStyledAttributes (attrs, R.styleable.Widget_MessageLayout);

        String nameText = typedArray.getString (R.styleable.Widget_MessageLayout_widget_message_layout_name);

        // 设置属性
        mName.setText (nameText);

        // 回收资源
        typedArray.recycle ();
    }

    // 拿到editText
    public EditText getEditText(){
        return mContent;
    }
}
