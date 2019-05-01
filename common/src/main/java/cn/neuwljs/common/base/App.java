package cn.neuwljs.common.base;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.HashMap;
import java.util.Map;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import cn.neuwljs.network.Network;
import cn.neuwljs.network.TimeoutParam;

import static cn.neuwljs.common.AppConfig.ISDEBUG;

public class App extends Application {

    private Context mContext;

    // 初始化网络的所有module集合
    private static final String[] MODULE = new String[] {
            "cn.neuwljs.module_d.ModuleDApplication"
    };

    @Override
    public void onCreate() {
        super.onCreate ();

        mContext = getApplicationContext ();

        // 初始化Arouter
        if (ISDEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化

        //初始化侧滑
        BGASwipeBackHelper.init (this,null);

        // 初始化Network
        initModule ();
    }

    private void initModule(){

        // 拿到映射关系
        Map<String, String> map = new HashMap<> ();

        // 反射获取所有module的IApp实现类并调用初始化方法
        for(String s : MODULE){
            try {
                Class<?> clazz = Class.forName (s);
                Object obj = clazz.newInstance ();
                if(obj instanceof IApp){

                    // 先执行这个context的赋值
                    ((IApp) obj).initContext (mContext);

                    // 拿到所有的映射关系
                    Map<String, String> temp = ((IApp) obj).initNetwork ();
                    if (temp != null) {
                        map.putAll (temp);
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace ();
            } catch (IllegalAccessException e) {
                e.printStackTrace ();
            } catch (InstantiationException e) {
                e.printStackTrace ();
            }
        }

        // 初始化network
        Network.init (map, new TimeoutParam.Builder ()
                .connectTime (6)
                .readTime (6)
                .writeTime (6)
                .build ());
    }
}
