package com.neuwljs.wallsmalltwo.presenter.impl;

import android.annotation.SuppressLint;
import android.content.Context;

import com.neuwljs.wallsmalltwo.model.gson.Words;
import com.neuwljs.wallsmalltwo.presenter.PresenterContract;
import com.neuwljs.wallsmalltwo.util.ImageUtil;
import com.neuwljs.wallsmalltwo.util.LogUtil;
import com.neuwljs.wallsmalltwo.util.manager.RetrofitManager;
import com.neuwljs.wallsmalltwo.view.ViewContract;
import com.neuwljs.wallsmalltwo.view.fragment.FragmentDBC;

import java.io.File;
import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.neuwljs.wallsmalltwo.model.Constant.PHOTO_FILE_NAME;

public class FragmentDBCPresenterImpl implements PresenterContract.FragmentDBCPresenter {

    private static final String TAG = "FragmentDBCPresenterImpl";

    /**
     * xml里面access_token的key
     */
    private static final String XML_ACCESS_TOKEN_KEY = "xml_access_token_key";

    /**
     * xml里面access_token第一次请求到时间的key
     */
    private static final String XML_ACCESS_TOKEN_TIME_KEY = "xml_access_token_time_key";

    private static final long TERM_OF_VALIDITY = 30*24*60*60*1000;

    private ViewContract.FragmentDBCView mFragmentDBCView;

    //对应的fragment的引用
    private FragmentDBC mFragmentDBC;

    private Context mContext;

    public FragmentDBCPresenterImpl(FragmentDBC fragmentDBC) {
        mFragmentDBC = fragmentDBC;
        mContext = mFragmentDBC.obtainContext ();
    }

    @Override
    public void attach(ViewContract.FragmentDBCView fragmentDBCView) {
        mFragmentDBCView = fragmentDBCView;
    }

    @Override
    public void detach() {
        mFragmentDBCView = null;
    }

    @SuppressLint("CheckResult")
    @Override
    public void load() {


        String at = "24.f4e914eb1d2d8c264d1bedf34039bb0d.2592000.1556628502.282335-15306874";

        // 拿到base64编码
        String imageStr = encodeImage();
        if(imageStr != null){
            RetrofitManager.getInstance ()
                    .create ()
                    .queryOCRWords (at, encodeImage ())
                    .subscribeOn (Schedulers.io ())
                    .observeOn (AndroidSchedulers.mainThread ())
                    .subscribe (new Consumer<Words> () {
                        @Override
                        public void accept(Words words) throws Exception {
                            LogUtil.d (TAG, "words: " + words.getWordsResultNum () + "");
                        }
                    }, new Consumer<Throwable> () {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            throwable.printStackTrace ();
                        }
                    }, new Action () {
                        @Override
                        public void run() throws Exception {
                        }
                    });
        }
    }

    // base64编码
    private String encodeImage(){
        // 拿到拍照获得的图片
        File file = new File (mContext.getExternalCacheDir (), PHOTO_FILE_NAME);

        // 裁剪并保存
        File newFile = new File (mContext.getExternalCacheDir (), "1_" + PHOTO_FILE_NAME);
        ImageUtil.resize (file.getPath (), newFile.getPath (), ImageUtil.DEFAULT_WIDTH,
                ImageUtil.DEFAULT_HEIGHT, ImageUtil.DEFAULT_QUALITY);

        // 拿到流
        byte[] imgData = null;
        try {
            imgData = ImageUtil.readFileByBytes (file.getPath ());
        } catch (IOException e) {
            e.printStackTrace ();
        }

        // 拿到base64字符串并返回
        String imgStr = null;
        if(imgData != null){
            imgStr = ImageUtil.encode (imgData);
            imgStr = imgStr.replace("data:image/jpg;base64,", "");
        }
        return imgStr;
    }

    // 拿到access_token的值
    private String getaccessToken(){
        //TODO
        return null;
    }
}
