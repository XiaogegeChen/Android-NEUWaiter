package com.neuwljs.wallsmalltwo.view;

import android.graphics.Bitmap;

import com.neuwljs.wallsmalltwo.model.base.Owner;
import com.neuwljs.wallsmalltwo.model.base.Publisher;

public class ViewContract {

    public interface BaseView{
        void showProgressPage();
        void showErrorPage();
        void showToast(String message);
    }

    public interface MainActivityView extends BaseView{

    }

    public interface FragmentAView extends BaseView{

    }

    public interface FragmentDView extends BaseView{

    }

    public interface FragmentDBView extends BaseView{

    }

    public interface FragmentDBAView extends BaseView{

    }

    public interface FragmentDBBView extends BaseView{
        /**
         * 显示拍照的dialog
         */
        void showDialog();

        /**
         *关闭拍照的dialog
         */
        void dismissDialog();

        /**
         * 显示拍照或者从相册拿到的图片
         * @param bitmap 拿到的图片
         */
        void showImage(Bitmap bitmap);
    }

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
    }

    public interface FragmentDBDView extends BaseView{

    }
}
