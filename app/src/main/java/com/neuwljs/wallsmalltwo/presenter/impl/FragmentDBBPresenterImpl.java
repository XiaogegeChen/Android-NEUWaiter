package com.neuwljs.wallsmalltwo.presenter.impl;

import android.content.Context;
import android.graphics.Bitmap;
import com.neuwljs.wallsmalltwo.presenter.PresenterContract;
import com.neuwljs.wallsmalltwo.util.ImageUtil;
import com.neuwljs.wallsmalltwo.view.ViewContract;
import com.neuwljs.wallsmalltwo.view.fragment.FragmentDBB;
import com.neuwljs.wallsmalltwo.view.fragment.FragmentDBC;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;

import static com.neuwljs.wallsmalltwo.model.Constant.PHOTO_FILE_NAME;

public class FragmentDBBPresenterImpl
        implements PresenterContract.FragmentDBBPresenter{

    private Context mContext;

    public FragmentDBBPresenterImpl(FragmentDBB fragmentDBB) {
        mContext = fragmentDBB.obtainContext ();
    }

    //视图的引用
    private ViewContract.FragmentDBBView mFragmentDBBView;

    @Override
    public void attach(ViewContract.FragmentDBBView fragmentDBBView) {
        mFragmentDBBView = fragmentDBBView;
    }

    @Override
    public void detach() {
        mFragmentDBBView = null;
    }

    @Override
    public void notifyPropertyRefresh(String information) {
        FragmentDBC.RefreshPropertyInformationEvent event = new FragmentDBC.RefreshPropertyInformationEvent ();
        event.setInformation (information);
        EventBus.getDefault ().post (event);
    }

    @Override
    public void notifyFragmentDBCLoad() {
        FragmentDBC.LoadEvent event = new FragmentDBC.LoadEvent ();
        event.setBegin (true);
        EventBus.getDefault ().post (event);
    }

    @Override
    public void notifyFragmentDBCRefreshUI(String information) {
        FragmentDBC.DisplayInformationEvent event = new FragmentDBC.DisplayInformationEvent ();
        event.setInformation (information);
        EventBus.getDefault ().post (event);
    }

    @Override
    public void saveAndShow(Bitmap bitmap) {

        //创建文件存储照片
        File file = new File (mContext.getExternalCacheDir (), PHOTO_FILE_NAME);
        try {
            if(file.exists ()){
                file.delete ();
            }
            file.createNewFile ();
        } catch (IOException e) {
            e.printStackTrace ();
        }

        // 存储
        ImageUtil.saveImage (bitmap, file);

        //显示
        mFragmentDBBView.showImage (bitmap);
    }
}
