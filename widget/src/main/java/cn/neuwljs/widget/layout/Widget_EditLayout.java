package cn.neuwljs.widget.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.neuwljs.widget.R;

public class Widget_EditLayout extends LinearLayout {
    private EditText mEditText;

    public Widget_EditLayout(Context context) {
        this (context, null);
    }

    public Widget_EditLayout(Context context, @Nullable AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public Widget_EditLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);

        //加载布局
        LayoutInflater.from (context).inflate (R.layout.widget_edit_layout, this);

        // 拿到控件
        ImageView icon = findViewById (R.id.widget_edit_layout_image_view);
        mEditText = findViewById (R.id.widget_edit_layout_edit_text);

        // 拿到资源和属性
        TypedArray typedArray = context.obtainStyledAttributes (attrs, R.styleable.Widget_EditLayout);

        int imageId = typedArray.getResourceId (R.styleable.Widget_EditLayout_widget_editIcon, R.drawable.widget_edit_layout_tools_share);
        String hintText = typedArray.getString (R.styleable.Widget_EditLayout_widget_hint);
        int textColor = typedArray.getColor (R.styleable.Widget_EditLayout_widget_textColor, Color.BLACK);
        int inputType = typedArray.getInt (R.styleable.Widget_EditLayout_widget_inputType, 0);

        // 设置属性
        icon.setImageResource (imageId);
        mEditText.setHint (hintText);
        mEditText.setTextColor (textColor);

        switch (inputType){
            case 2:
                mEditText.setInputType (InputType.TYPE_CLASS_NUMBER);
                break;
            case 1:
                mEditText.setInputType (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            default:
                mEditText.setInputType (InputType.TYPE_CLASS_TEXT);
                break;
        }

        // 回收资源
        typedArray.recycle ();
    }

    // 提供一个拿到edit的公开方法
    public EditText getEditText(){
        return mEditText;
    }
}
