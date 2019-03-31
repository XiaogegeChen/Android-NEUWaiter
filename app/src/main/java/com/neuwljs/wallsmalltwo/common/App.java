package com.neuwljs.wallsmalltwo.common;

import android.app.Application;

import org.litepal.LitePal;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate ();

        //初始化侧滑
        BGASwipeBackHelper.init (this,null);

        //初始化litepal
        LitePal.initialize (this);
    }
}
