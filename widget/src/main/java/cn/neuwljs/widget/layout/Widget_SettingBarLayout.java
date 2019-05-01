package cn.neuwljs.widget.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.neuwljs.widget.R;

public class Widget_SettingBarLayout extends LinearLayout {
    private TextView mContent;

    public Widget_SettingBarLayout(Context context) {
        this (context, null);
    }

    public Widget_SettingBarLayout(Context context, @Nullable AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public Widget_SettingBarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);

        //加载布局
        LayoutInflater.from (context).inflate (R.layout.widget_setting_bar_layout, this);

        // 拿到控件
        TextView mName = findViewById (R.id.widget_setting_bar_layout_item_name);
        mContent = findViewById (R.id.widget_setting_bar_layout_item_content);

        // 拿到资源和属性
        TypedArray typedArray = context.obtainStyledAttributes (attrs, R.styleable.Widget_SettingBarLayout);

        String nameText = typedArray.getString (R.styleable.Widget_SettingBarLayout_widget_setItemName);
        String contentText = typedArray.getString (R.styleable.Widget_SettingBarLayout_widget_setItemContent);

        // 设置属性
        mName.setText (nameText);
        mContent.setText (contentText);

        // 回收资源
        typedArray.recycle ();
    }

    // 代码更改content的文本
    public void setContentText(String text){
        mContent.setText (text);
    }
}
