package com.neuwljs.wallsmalltwo.presenter;

import android.content.Intent;

import com.neuwljs.wallsmalltwo.model.base.Publisher;
import com.neuwljs.wallsmalltwo.model.gson.Found;
import com.neuwljs.wallsmalltwo.model.submit.Property;
import com.neuwljs.wallsmalltwo.view.ViewContract;

/**
 * 业务逻辑接口集合
 */
public class PresenterContract {

    /**
     * 业务逻辑接口基类
     * 绑定view
     * 解除绑定
     */
    public interface BasePresenter<T extends ViewContract.BaseView>{
        void attach(T t);
        void detach();
    }

    /**
     * MainActivity的业务逻辑接口
     */
    public interface MainActivityPresenter extends BasePresenter<ViewContract.MainActivityView>{

    }

    public interface FragmentAPresenter extends BasePresenter<ViewContract.FragmentAView>{

    }

    public interface FragmentDPresenter extends BasePresenter<ViewContract.FragmentDView>{

    }

    /**
     * FragmentDA的业务逻辑接口
     */
    public interface FragmentDAPresenter extends BasePresenter<ViewContract.FragmentDAAView>{

    }

    /**
     * FragmentDAA的业务逻辑接口
     */
    public interface FragmentDAAPresenter extends BasePresenter<ViewContract.FragmentDAAView>{
        /**
         * 请求一页数据
         * @param page 页码，第几页
         */
        void queryPage(String page);

        /**
         * 请求下一页
         */
        void queryNext();
    }

    public interface FragmentDABPresenter extends BasePresenter<ViewContract.FragmentDABView>{
        /**
         * 请求一页数据
         * @param page 页码，第几页
         */
        void queryPage(String page);

        /**
         * 请求下一页
         */
        void queryNext();
    }

    /**
     * FragmentDBA的业务逻辑接口
     */
    public interface FragmentDBAPresenter extends BasePresenter<ViewContract.FragmentDBAView>{
        /**
         * 通知第三页的Property对象更新失物类型
         * @param lostPropertyType 失物类型
         */
        void notifyPropertyRefresh(String lostPropertyType);
    }

    /**
     * FragmentDBB的业务逻辑接口
     */
    public interface FragmentDBBPresenter extends BasePresenter<ViewContract.FragmentDBBView>{
        /**
         * 取消按钮点击时候的逻辑
         */
        void cancel();

        /**
         * 打开相机并且拿到返回的图片
         */
        void takePhoto();

        /**
         * 从相册中选择图片
         */
        void selectPhoto();

        /**
         * 通知第三页的Property对象更新  详细信息
         * @param information 详细信息
         */
        void notifyPropertyRefresh(String information);

        /**
         * 通知第三页开始请求网络识别图片
         */
        void notifyFragmentDBCLoad();

        /**
         * 通知第三页刷新UI
         */
        void notifyFragmentDBCRefreshUI(String information);

        /**
         * 开启相机或者开启相册之后onActivityResult()方法的实现
         * @param requestCode 请求码
         * @param resultCode 结果码
         * @param intent intent
         */
        void handleActivityResult(int requestCode, int resultCode, Intent intent);
    }

    /**
     * FragmentDBC的业务逻辑接口
     */
    public interface FragmentDBCPresenter extends BasePresenter<ViewContract.FragmentDBCView>{
        /**
         * 请求网络拿到识别得到的结果并显示
         */
        void loadOwner();

        /**
         * 从本地拿到Publisher的实例并显示
         */
        Publisher loadPublisher();

        /**
         * 向服务器上传发布的这个信息
         * @param property 物品实例
         */
        void upload(Property property);

        /**
         * 重新上传信息
         */
        void retry();

        /**
         * 关闭对话框并保存之前填写的信息
         * @param property
         */
        void cancelAndSave(Property property);
    }

    /**
     * FragmentDBD的业务逻辑接口
     */
    public interface FragmentDBDPresenter extends BasePresenter<ViewContract.FragmentDBDView>{

        /**
         * 通知首页更新
         */
        void notifyFragmentDAARefresh(Found found);

        /**
         * 通知填写信息的第一页更新
         */
        void notifyFragmentDBRefresh();
    }
}
