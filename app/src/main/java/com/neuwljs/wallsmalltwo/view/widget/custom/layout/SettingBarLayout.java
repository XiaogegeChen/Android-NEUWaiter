package com.neuwljs.wallsmalltwo.view.widget.custom.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neuwljs.wallsmalltwo.R;

import androidx.annotation.Nullable;

/**
 * 自定义一个显示设置项的layout
 */
public class SettingBarLayout extends LinearLayout {

    private TextView mContent;

    public SettingBarLayout(Context context) {
        this (context, null);
    }

    public SettingBarLayout(Context context, @Nullable AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public SettingBarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);

        //加载布局
        LayoutInflater.from (context).inflate (R.layout.setting_bar_layout, this);

        // 拿到控件
        TextView mName = findViewById (R.id.setting_bar_layout_item_name);
        mContent = findViewById (R.id.setting_bar_layout_item_content);

        // 拿到资源和属性
        TypedArray typedArray = context.obtainStyledAttributes (attrs, R.styleable.SettingBarLayout);

        String nameText = typedArray.getString (R.styleable.SettingBarLayout_setItemName);
        String contentText = typedArray.getString (R.styleable.SettingBarLayout_setItemContent);

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
