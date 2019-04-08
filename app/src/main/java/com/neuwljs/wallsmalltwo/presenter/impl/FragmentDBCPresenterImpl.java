package com.neuwljs.wallsmalltwo.presenter.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;

import com.neuwljs.wallsmalltwo.common.ApiService;
import com.neuwljs.wallsmalltwo.model.base.Owner;
import com.neuwljs.wallsmalltwo.model.base.Publisher;
import com.neuwljs.wallsmalltwo.model.database.User;
import com.neuwljs.wallsmalltwo.model.gson.AccessToken;
import com.neuwljs.wallsmalltwo.model.gson.Words;
import com.neuwljs.wallsmalltwo.model.submit.Property;
import com.neuwljs.wallsmalltwo.presenter.PresenterContract;
import com.neuwljs.wallsmalltwo.util.ImageUtil;
import com.neuwljs.wallsmalltwo.util.XmlIOUtil;
import com.neuwljs.wallsmalltwo.util.helper.QueryDataHelper;
import com.neuwljs.wallsmalltwo.util.manager.RetrofitManager;
import com.neuwljs.wallsmalltwo.util.manager.SchedulerManager;
import com.neuwljs.wallsmalltwo.view.ViewContract;
import com.neuwljs.wallsmalltwo.view.fragment.FragmentDBC;

import org.litepal.LitePal;

import java.io.File;
import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

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

    /**
     * access_token的有效期
     */
    private static final long TERM_OF_VALIDITY = 29L*24L*60L*60L*1000L;

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
    public void loadOwner() {
        mFragmentDBCView.showProgressPage ();
        new QueryAccessTokenHelper ().queryData (QueryDataHelper.Type.NORMAL)
                .filter (new Predicate<AccessToken> () {
                    @Override
                    public boolean test(AccessToken accessToken) throws Exception {
                        return accessToken != null && accessToken.getAccessToken () != null;
                    }
                })
                .subscribe (new Consumer<AccessToken> () {
                    @Override
                    public void accept(AccessToken accessToken) throws Exception {
                        // 拿到accessToken值
                        String accessTokenValue = accessToken.getAccessToken ();

                        // 请求图片文字
                        String imageStr = encodeImage();
                        if(imageStr != null){
                            RetrofitManager.getInstance ()
                                    .create ()
                                    .queryOCRWords (accessTokenValue, encodeImage ())
                                    .compose (SchedulerManager.getInstance ().<Words>applySchedulers ())
                                    .subscribe (new Consumer<Words> () {
                                        @Override
                                        public void accept(Words words) throws Exception {
                                            if(words != null && words.getWordsResult () != null
                                                    && words.getWordsResult ().size () > 0){
                                                mFragmentDBCView.showOwner (convertWords2Owner (words));
                                            }else{
                                                mFragmentDBCView.showErrorPage ();
                                            }
                                        }
                                    }, new Consumer<Throwable> () {
                                        @Override
                                        public void accept(Throwable e) throws Exception {
                                            e.printStackTrace ();
                                            mFragmentDBCView.showErrorPage ();
                                        }
                                    });
                        }else{
                            mFragmentDBCView.showErrorPage ();
                        }
                    }
                }, new Consumer<Throwable> () {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        e.printStackTrace ();
                        mFragmentDBCView.showErrorPage ();
                    }
                });
    }

    @Override
    public void loadPublisher() {
        Publisher publisher = new Publisher ();

        // 从数据库拿到用户信息
        List<User> userList = LitePal.findAll (User.class);
        if(userList.size () > 0){
            User user = userList.get (0);

            // 拿到头像
            String headImageStr = user.getHeadImage ();
            if(headImageStr != null){
                Bitmap headImage = ImageUtil.decode (headImageStr);
                publisher.setHeadImage (headImage);
            }

            // 拿到昵称
            String name = user.getName ();
            if(name != null){
                publisher.setName (name);
            }

            // 拿到时间
            publisher.setTime (System.currentTimeMillis ());
            mFragmentDBCView.showPublisher (publisher);
        }
    }

    @Override
    public void upload(Property property) {

    }

    // base64编码
    private String encodeImage(){
        // 拿到拍照获得的图片
        File file = new File (mContext.getExternalCacheDir (), PHOTO_FILE_NAME);

        // 如果没有这个图，就代表没有拍，直接返回null
        if(!file.exists ()){
            return null;
        }

        // 裁剪并保存
        File newFile = new File (mContext.getExternalCacheDir (), "1_" + PHOTO_FILE_NAME);
        ImageUtil.resize (file.getPath (), newFile.getPath (), ImageUtil.DEFAULT_WIDTH,
                ImageUtil.DEFAULT_HEIGHT, ImageUtil.DEFAULT_QUALITY);

        // 拿到流
        byte[] imgData = null;
        try {
            imgData = ImageUtil.readFileByBytes (newFile.getPath ());
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

    /**
     * 拿到access_token的实现类
     */
    class QueryAccessTokenHelper extends QueryDataHelper<AccessToken>{

        @Override
        public Observable<AccessToken> queryFromNet() {
            return RetrofitManager.getInstance ()
                    .create ()
                    .queryOCRAccessToken (ApiService.GRANT_TYPE, ApiService.OCR_AK, ApiService.OCR_SK)
                    .filter (new Predicate<AccessToken> () {
                        @Override
                        public boolean test(AccessToken accessToken) throws Exception {
                            return accessToken != null && accessToken.getAccessToken () != null;
                        }
                    })
                    .flatMap (new Function<AccessToken, ObservableSource<AccessToken>> () {
                        @Override
                        public ObservableSource<AccessToken> apply(final AccessToken accessToken) throws Exception {

                            // 写入sharePreference
                            XmlIOUtil.xmlPut (XML_ACCESS_TOKEN_KEY, accessToken.getAccessToken (), mContext);
                            XmlIOUtil.xmlPut (XML_ACCESS_TOKEN_TIME_KEY, System.currentTimeMillis (), mContext);

                            return Observable.create (new ObservableOnSubscribe<AccessToken> () {
                                @Override
                                public void subscribe(ObservableEmitter<AccessToken> emitter) throws Exception {
                                    emitter.onNext (accessToken);
                                    emitter.onComplete ();
                                }
                            });
                        }
                    });
        }

        @Override
        public Observable<AccessToken> queryFromLocal() {
            // 从sharePreference拿数据
            long time = XmlIOUtil.xmlGetLong (XML_ACCESS_TOKEN_TIME_KEY, mContext);

            if(time != 0){

                // 过期重新获取
                if(System.currentTimeMillis () - time >= TERM_OF_VALIDITY){
                    return queryFromNet();
                }else{
                    return Observable.create (new ObservableOnSubscribe<AccessToken> () {
                        @Override
                        public void subscribe(ObservableEmitter<AccessToken> emitter) throws Exception {
                            AccessToken accessToken;

                            // 从sharePreference拿数据
                            String accessTokenValue = XmlIOUtil.xmlGetString (XML_ACCESS_TOKEN_KEY, mContext);
                            if(accessTokenValue != null){
                                accessToken = new AccessToken ();
                                accessToken.setAccessToken (accessTokenValue);
                                emitter.onNext (accessToken);
                            }
                            emitter.onComplete ();
                        }
                    });
                }
            }else{
                return Observable.create (new ObservableOnSubscribe<AccessToken> () {
                    @Override
                    public void subscribe(ObservableEmitter<AccessToken> emitter) throws Exception {
                        emitter.onComplete ();
                    }
                });
            }
        }
    }

    private Owner convertWords2Owner(Words words){
        Owner owner = new Owner ();
        for(Words.WordsResultBean wordsResultBean : words.getWordsResult ()){
            String[] result = wordsResultBean.getWords ().split (":");
            if(result.length > 1){
                if("姓名".equals (result[0])){
                    owner.setName (result[1]);
                }
                if("学号".equals (result[0])){
                    owner.setId (result[1]);
                }
                if("学院".equals (result[0])){
                    owner.setCollege (result[1]);
                }
            }
        }
        return owner;
    }
}
