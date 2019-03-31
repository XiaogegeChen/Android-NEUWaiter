package com.neuwljs.wallsmalltwo.view;

import android.graphics.Bitmap;

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
        void showDialog();
        void dismissDialog();
        void showImage(Bitmap bitmap);
    }

    public interface FragmentDBCView extends BaseView{
        void showScrollView();
    }

    public interface FragmentDBDView extends BaseView{

    }
}
