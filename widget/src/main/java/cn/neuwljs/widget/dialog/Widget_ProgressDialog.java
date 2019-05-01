package cn.neuwljs.widget.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.neuwljs.widget.R;

public class Widget_ProgressDialog extends LinearLayout {
    public Widget_ProgressDialog(Context context) {
        this (context, null);
    }

    public Widget_ProgressDialog(Context context, AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public Widget_ProgressDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);

        //加载布局
        LayoutInflater.from (context).inflate (R.layout.widget_progress_dialog, this);

        // 拿到控件
        TextView description = findViewById (R.id.widget_progress_dialog_text);

        // 拿到资源
        TypedArray typedArray = context.obtainStyledAttributes (attrs, R.styleable.Widget_ProgressDialog);

        // 拿到属性
        String descriptionText = typedArray.getString (R.styleable.Widget_ProgressDialog_widget_progress_description);

        // 设置到布局
        description.setText (descriptionText);

        // 回收资源
        typedArray.recycle ();
    }
}
