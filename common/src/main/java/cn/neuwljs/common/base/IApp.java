package cn.neuwljs.common.base;

import android.content.Context;

import java.util.Map;

public interface IApp {

    // 初始化network
    Map<String, String> initNetwork();

    // 子模块通过这个方法拿到context
    void initContext(Context context);

}
