package com.neuwljs.wallsmalltwo.view.widget.custom.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neuwljs.wallsmalltwo.R;

import androidx.annotation.Nullable;

/**
 * 自定义一个用于显示列表的layout
 */
public class ListLayout extends LinearLayout {

    public ListLayout(Context context) {
        this (context, null);
    }

    public ListLayout(Context context, @Nullable AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public ListLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);

        // 加载布局
        LayoutInflater.from (context).inflate (R.layout.list_layout, this);

        // 拿到控件
        ImageView icon = findViewById (R.id.list_layout_image_view);
        TextView description = findViewById (R.id.list_layout_text_view);

        // 拿到资源
        TypedArray typedArray = context.obtainStyledAttributes (attrs, R.styleable.ListLayout);

        String text = typedArray.getString (R.styleable.ListLayout_text);
        int iconId = typedArray.getResourceId (R.styleable.ListLayout_icon, R.drawable.ic_list_share);

        // 设置到布局
        description.setText (text);
        icon.setImageResource (iconId);

        // 回收资源
        typedArray.recycle ();
    }
}
