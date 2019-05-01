package cn.neuwljs.widget.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.neuwljs.widget.R;

public class Widget_ListLayout extends LinearLayout {

    public Widget_ListLayout(Context context) {
        this (context, null);
    }

    public Widget_ListLayout(Context context, @Nullable AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public Widget_ListLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);

        // 加载布局
        LayoutInflater.from (context).inflate (R.layout.widget_list_layout, this);

        // 拿到控件
        ImageView icon = findViewById (R.id.widget_list_layout_image_view);
        TextView description = findViewById (R.id.widget_list_layout_text_view);

        // 拿到资源
        TypedArray typedArray = context.obtainStyledAttributes (attrs, R.styleable.Widget_ListLayout);

        String text = typedArray.getString (R.styleable.Widget_ListLayout_widget_text);
        int iconId = typedArray.getResourceId (R.styleable.Widget_ListLayout_widget_icon, R.drawable.widget_edit_layout_tools_share);

        // 设置到布局
        description.setText (text);
        icon.setImageResource (iconId);

        // 回收资源
        typedArray.recycle ();
    }
}
