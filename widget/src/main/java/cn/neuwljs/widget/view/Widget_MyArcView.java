package cn.neuwljs.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cn.neuwljs.widget.R;

/**
 * 自定义一个带圆弧的view
 */
public class Widget_MyArcView extends View {

    // 布局的宽高
    private int mHeight;
    private int mWidth;

    // 圆弧的高度
    private int mArcHeight;

    // 画笔
    private Paint mPaint;

    // view中的圆弧的路径
    private Path mPath;

    public Widget_MyArcView(Context context) {
        this (context, null);
    }

    public Widget_MyArcView(Context context, @Nullable AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public Widget_MyArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);

        // 初始化使用到的对象
        mPaint = new Paint ();
        mPath = new Path ();

        TypedArray typedArray = context.obtainStyledAttributes (attrs, R.styleable.Widget_MyArcView);

        // 拿到属性值
        mArcHeight = typedArray.getDimensionPixelSize (R.styleable.Widget_MyArcView_widget_arcHeight, 0);
        int mColor = typedArray.getColor (R.styleable.Widget_MyArcView_widget_bgColor, Color.BLUE);

        // 设置画笔
        mPaint.setStyle (Paint.Style.FILL);
        mPaint.setColor (mColor);

        // 回收typedArray
        typedArray.recycle ();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw (canvas);

        mPath.moveTo (0, 0);
        mPath.lineTo (0, mHeight);
        mPath.quadTo ((float) (mWidth/2.0), mHeight - mArcHeight, mWidth, mHeight);
        mPath.lineTo (mWidth, 0);
        canvas.drawPath (mPath, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure (widthMeasureSpec, heightMeasureSpec);

        // 拿到view的宽高
        int widthSize = MeasureSpec.getSize (widthMeasureSpec);
        int widthMode = MeasureSpec.getMode (widthMeasureSpec);

        int heightSize = MeasureSpec.getSize (heightMeasureSpec);
        int heightMode = MeasureSpec.getMode (heightMeasureSpec);

        // 设置属性值
        if(widthMode == MeasureSpec.EXACTLY){
            mWidth = widthSize;
        }
        if(heightMode == MeasureSpec.EXACTLY){
            mHeight = heightSize;
        }
        setMeasuredDimension (widthSize, heightSize);
    }
}
