package com.neuwljs.wallsmalltwo.view.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 待处理,需要实现它的外层不能消费滑动事件
 */
public class BannerViewPager extends ViewPager {

    public BannerViewPager(@NonNull Context context) {
        super (context);
    }

    public BannerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super (context, attrs);
    }

}
