package cn.neuwljs.module_d.presenter.impl;

import android.content.Context;
import android.graphics.Bitmap;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;

import cn.neuwljs.common.util.ImageUtil;
import cn.neuwljs.module_d.model.event.NotifyFragmentDBCDisplayInformationEvent;
import cn.neuwljs.module_d.model.event.NotifyFragmentDBCLoadOwnerAndPublisherEvent;
import cn.neuwljs.module_d.model.event.NotifyFragmentDBCRefreshPropertyInformationEvent;
import cn.neuwljs.module_d.presenter.IFragmentDBBPresenter;
import cn.neuwljs.module_d.view.IFragmentDBBView;
import cn.neuwljs.module_d.view.impl.FragmentDBB;

import static cn.neuwljs.module_d.Constants.PHOTO_FILE_NAME;

public class FragmentDBBPresenterImpl
        implements IFragmentDBBPresenter {

    private Context mContext;

    public FragmentDBBPresenterImpl(FragmentDBB fragmentDBB) {
        mContext = fragmentDBB.obtainContext ();
    }

    //视图的引用
    private IFragmentDBBView mFragmentDBBView;

    @Override
    public void attach(IFragmentDBBView fragmentDBBView) {
        mFragmentDBBView = fragmentDBBView;
    }

    @Override
    public void detach() {
        mFragmentDBBView = null;
    }

    @Override
    public void notifyPropertyRefresh(String information) {
        NotifyFragmentDBCRefreshPropertyInformationEvent event = new NotifyFragmentDBCRefreshPropertyInformationEvent ();
        event.setInformation (information);
        EventBus.getDefault ().post (event);
    }

    @Override
    public void notifyFragmentDBCLoad() {
        NotifyFragmentDBCLoadOwnerAndPublisherEvent event = new NotifyFragmentDBCLoadOwnerAndPublisherEvent ();
        EventBus.getDefault ().post (event);
    }

    @Override
    public void notifyFragmentDBCRefreshUI(String information) {
        NotifyFragmentDBCDisplayInformationEvent event = new NotifyFragmentDBCDisplayInformationEvent ();
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
