package com.neuwljs.wallsmalltwo.presenter.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.neuwljs.wallsmalltwo.common.ApiService;
import com.neuwljs.wallsmalltwo.model.base.LostOrFound;
import com.neuwljs.wallsmalltwo.model.base.LostPropertyType;
import com.neuwljs.wallsmalltwo.model.base.Owner;
import com.neuwljs.wallsmalltwo.model.base.Publisher;
import com.neuwljs.wallsmalltwo.model.base.Response;
import com.neuwljs.wallsmalltwo.model.database.User;
import com.neuwljs.wallsmalltwo.model.gson.AccessToken;
import com.neuwljs.wallsmalltwo.model.gson.Found;
import com.neuwljs.wallsmalltwo.model.gson.SubmitPropertyResult;
import com.neuwljs.wallsmalltwo.model.gson.Words;
import com.neuwljs.wallsmalltwo.model.submit.Property;
import com.neuwljs.wallsmalltwo.model.submit.SubmitProperty;
import com.neuwljs.wallsmalltwo.presenter.PresenterContract;
import com.neuwljs.wallsmalltwo.util.ImageUtil;
import com.neuwljs.wallsmalltwo.util.LogUtil;
import com.neuwljs.wallsmalltwo.util.StringUtil;
import com.neuwljs.wallsmalltwo.util.XmlIOUtil;
import com.neuwljs.wallsmalltwo.util.helper.QueryDataHelper;
import com.neuwljs.wallsmalltwo.util.helper.ResponseHelper;
import com.neuwljs.wallsmalltwo.util.manager.RetrofitManager;
import com.neuwljs.wallsmalltwo.util.manager.SchedulerManager;
import com.neuwljs.wallsmalltwo.util.network.MyErrorConsumer;
import com.neuwljs.wallsmalltwo.view.ViewContract;
import com.neuwljs.wallsmalltwo.view.fragment.FragmentDBC;
import com.neuwljs.wallsmalltwo.view.fragment.FragmentDBD;

import org.greenrobot.eventbus.EventBus;
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

import static com.neuwljs.wallsmalltwo.model.Constant.COLLEGE_MAX_LENGTH;
import static com.neuwljs.wallsmalltwo.model.Constant.NAME_MAX_LENGTH;
import static com.neuwljs.wallsmalltwo.model.Constant.OWNER_COLLEGE_LIMITED;
import static com.neuwljs.wallsmalltwo.model.Constant.OWNER_ID_LIMITED;
import static com.neuwljs.wallsmalltwo.model.Constant.OWNER_NAME_LIMITED;
import static com.neuwljs.wallsmalltwo.model.Constant.PHOTO_FILE_NAME;
import static com.neuwljs.wallsmalltwo.model.Constant.PROPERTY_IS_ALREADY_SAVED;
import static com.neuwljs.wallsmalltwo.model.Constant.PUBLISHER_NAME_DEFAULT;
import static com.neuwljs.wallsmalltwo.model.Constant.XmlConstants.XML_ACCESS_TOKEN_KEY;
import static com.neuwljs.wallsmalltwo.model.Constant.XmlConstants.XML_ACCESS_TOKEN_TIME_KEY;
import static com.neuwljs.wallsmalltwo.model.Constant.XmlConstants.XML_UNDONE_PROPERTY_KEY;

public class FragmentDBCPresenterImpl
        implements PresenterContract.FragmentDBCPresenter, MyErrorConsumer.OnErrorListener {

    private static final String TAG = "FragmentDBCPresenterImpl";

    /**
     * access_token的有效期
     */
    private static final long TERM_OF_VALIDITY = 29L*24L*60L*60L*1000L;

    private ViewContract.FragmentDBCView mFragmentDBCView;

    private Context mContext;

    // 待上传的bean
    private Property mProperty;

    // 上传者头像url
    private String mPublisherHeadImageUrl;

    public FragmentDBCPresenterImpl(FragmentDBC fragmentDBC) {
        //对应的fragment的引用
        mContext = fragmentDBC.obtainContext ();
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
    public Publisher loadPublisher() {
        Publisher publisher = new Publisher ();

        // 从数据库拿到用户信息
        List<User> userList = LitePal.findAll (User.class);
        if(userList.size () > 0){
            User user = userList.get (0);

            // 拿到头像
            String headImageStr = user.getHeadImage ();
            mPublisherHeadImageUrl = headImageStr;
            if(headImageStr != null){
                Bitmap headImage = ImageUtil.decode (headImageStr);
                publisher.setHeadImage (headImage);
            }

            // 拿到学院
            String college = user.getCollege ();
            if(college != null){
                publisher.setCollege (college);
            }

            // 拿到学号
            String id = user.getStudentId ();
            LogUtil.d (TAG, "student_id: "+id);
            if(id != null){
                publisher.setId (id);
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

        return publisher;
    }

    @SuppressLint("CheckResult")
    @Override
    public void upload(Property property) {

        // 格式不对不上传
        if (!checkFormat (property)){
            return;
        }

        mProperty = property;

        // 在显示进度条
        mFragmentDBCView.showLoading ();

        // 转化为上传的数据格式
        SubmitProperty submitProperty = convertProperty2SubmitProperty (mProperty);

        String json = new Gson ().toJson (submitProperty);
        LogUtil.d (TAG, json);

        // 上传数据
        RetrofitManager.getInstance ()
                .create ()
                .uploadProperty (submitProperty)
                .compose (SchedulerManager.getInstance ().<Response<List<SubmitPropertyResult>>>applySchedulers ())
                .compose (ResponseHelper.<List<SubmitPropertyResult>>transform ())
                .subscribe (new Consumer<List<SubmitPropertyResult>> () {
                    @Override
                    public void accept(List<SubmitPropertyResult> submitPropertyResults) throws Exception {

                        // 关闭对话框,通知最后一页更新
                        mFragmentDBCView.dismissLoading ();
                        SubmitPropertyResult result = submitPropertyResults.get (0);
                        if(result != null){
                            notifyFragmentDBDRefresh (result.getSerialNumber ());
                            mFragmentDBCView.goToNextPage ();
                        }
                    }
                }, new MyErrorConsumer (mContext, this));
    }

    @Override
    public void retry() {
        if(mProperty != null){
            mFragmentDBCView.dismissFailure ();
            upload (mProperty);
        }
    }

    @Override
    public void cancelAndSave(Property property) {
        // 关闭对话框
        mFragmentDBCView.dismissFailure ();

        // 存储数据并提示
        String propertyJson = new Gson().toJson (property);

        LogUtil.d (TAG, propertyJson);

        XmlIOUtil.xmlPut (XML_UNDONE_PROPERTY_KEY, propertyJson, mContext);
        mFragmentDBCView.showToast (PROPERTY_IS_ALREADY_SAVED);
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
     * {@link com.neuwljs.wallsmalltwo.util.network.MyErrorConsumer.OnErrorListener}
     */
    @Override
    public void onError() {
        mFragmentDBCView.dismissLoading ();
        mFragmentDBCView.showFailure ();
    }

    /**
     * 把Property对象转化成SubmitProperty进行上传
     * @param property Property实例
     * @return SubmitProperty实例
     */
    private SubmitProperty convertProperty2SubmitProperty(Property property){
        SubmitProperty submitProperty = new SubmitProperty ();

        if(property.getLostPropertyType () == null){
            submitProperty.setType (LostPropertyType.STUDENT_CARD);
        }else {
            submitProperty.setType (property.getLostPropertyType ());
        }

        if(property.getPublisherName () == null){
            submitProperty.setPub_name (PUBLISHER_NAME_DEFAULT);
        }else{
            submitProperty.setPub_name (property.getPublisherName ());
        }

        if(property.getPublisherId () == null){
            submitProperty.setPub_id ("");
        }else{
            submitProperty.setPub_id (property.getPublisherId ());
        }

        if(property.getPublishTime () == null){
            submitProperty.setPub_time (System.currentTimeMillis () + "");
        }else{
            submitProperty.setPub_time (property.getPublishTime ());
        }

        if(property.getInformation () == null){
            submitProperty.setPub_info ("");
        }else{
            submitProperty.setPub_info (property.getInformation ());
        }

        if(property.getOwnerId () == null){
            submitProperty.setOwn_id ("");
        }else {
            submitProperty.setOwn_id (property.getOwnerId ());
        }

        if(property.getOwnerCollege () == null){
            submitProperty.setOwn_college ("");
        }else{
            submitProperty.setOwn_college (property.getOwnerCollege ());
        }

        if(property.getOwnerName () == null){
            submitProperty.setOwn_name ("");
        }else{
            submitProperty.setOwn_name (property.getOwnerName ());
        }

        return submitProperty;
    }

    /**
     * 把Property对象转化为Found对象，以便传递给最后一页使用
     * @param property Property实例
     * @return Found实例
     */
    private Found convertPublisher2Found(Property property){
        Found found = new Found ();

        found.setPublisherHeadIamge (mPublisherHeadImageUrl);
        found.setPublisherName (property.getPublisherName ());
        found.setPublishTime (property.getPublishTime ());
        found.setInformation (property.getInformation ());
        found.setOwnerName (property.getOwnerName ());
        found.setOwnerId (property.getOwnerId ());
        found.setOwnerCollege (property.getOwnerCollege ());

        return found;
    }

    /**
     * 检查即将上传的数据的格式
     * @param property property
     * @return 格式正确就返回true，错误返回false
     */
    private boolean checkFormat(Property property){
        if(StringUtil.isEmpty (property.getOwnerName ())){

            // 名字是空的
            mFragmentDBCView.showToast (OWNER_NAME_LIMITED);
            return false;
        }
        if(StringUtil.isEmpty (property.getOwnerId ())){

            // 学号是空的
            mFragmentDBCView.showToast (OWNER_ID_LIMITED);
            return false;
        }
        if(StringUtil.isEmpty (property.getOwnerCollege ())){

            // 学院是空的
            mFragmentDBCView.showToast (OWNER_COLLEGE_LIMITED);
            return false;
        }
        if(property.getOwnerName ().length () > NAME_MAX_LENGTH){

            // 名字字数超过限制
            mFragmentDBCView.showToast (OWNER_NAME_LIMITED);
            return false;
        }
        if(property.getOwnerCollege ().length () > COLLEGE_MAX_LENGTH){

            // 学院字数超过限制
            mFragmentDBCView.showToast (OWNER_COLLEGE_LIMITED);
            return false;
        }
        if(!StringUtil.isAllNumber (property.getOwnerId ())){

            // 学号格式错误
            mFragmentDBCView.showToast (OWNER_ID_LIMITED);
            return false;
        }
        return true;
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

    // 通知最后一页更新并把Found传递过去
    private void notifyFragmentDBDRefresh(String serialNumber){

        // 发送事件
        FragmentDBD.RefreshSerialNumberEvent event = new FragmentDBD.RefreshSerialNumberEvent ();
        event.setSerialNumber (serialNumber);
        event.setFound (convertPublisher2Found (mProperty));
        EventBus.getDefault ().post (event);
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
}
