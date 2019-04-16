package com.neuwljs.wallsmalltwo.view;

import android.graphics.Bitmap;

import com.neuwljs.wallsmalltwo.model.base.Owner;
import com.neuwljs.wallsmalltwo.model.base.Publisher;
import com.neuwljs.wallsmalltwo.model.gson.Found;
import com.neuwljs.wallsmalltwo.model.gson.Lost;

import java.util.List;

public class ViewContract {

    public interface BaseView{
        void showProgressPage();
        void showErrorPage();
        void showToast(String message);
    }

    public interface MainActivityView extends BaseView{
    }

    public interface LoginActivityDView extends BaseView{

    }

    public interface FragmentAView extends BaseView{

    }

    public interface FragmentDView extends BaseView{

    }

    /**
     * FragmentDA的视图
     */
    public interface FragmentDAView extends BaseView{

    }

    /**
     * FragmentDAA的视图
     */
    public interface FragmentDAAView extends BaseView{
        /**
         * 显示初始的recyclerView
         * @param foundList 数据源
         */
        void showRecyclerView(List<Found> foundList);

        /**
         * 全部加载完成
         */
        void showDone();
    }

    /**
     * FragmentDAB的视图
     */
    public interface FragmentDABView extends BaseView{
        /**
         * 显示初始的recyclerView
         * @param lostList 数据源
         */
        void showRecyclerView(List<Lost> lostList);

        /**
         * 全部加载完成
         */
        void showDone();
    }

    public interface FragmentDBView extends BaseView{

    }

    /**
     * FragmentDBA的视图
     */
    public interface FragmentDBAView extends BaseView{

    }

    /**
     * FragmentDBB的视图
     */
    public interface FragmentDBBView extends BaseView{
        /**
         * 显示拍照或者从相册拿到的图片
         * @param bitmap 拿到的图片
         */
        void showImage(Bitmap bitmap);
    }

    /**
     * FragmentDBC的视图
     */
    public interface FragmentDBCView extends BaseView{

        /**
         * 显示识别后得到的失主信息
         * @param owner 识别后拿到的bean
         */
        void showOwner(Owner owner);

        /**
         * 显示发布者的信息
         * @param publisher 访问数据库拿到的Publisher实例
         */
        void showPublisher(Publisher publisher);

        /**
         * 显示上传对话框
         */
        void showLoading();

        /**
         * 关闭上传对话框
         */
        void dismissLoading();

        /**
         * 显示上传失败对话框
         */
        void showFailure();

        /**
         * 关闭上传失败对话框
         */
        void dismissFailure();

        /**
         * 跳转到最后一页
         */
        void goToNextPage();
    }

    public interface FragmentDBDView extends BaseView{

    }
}
