package cn.neuwljs.module_d.presenter.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import cn.neuwljs.common.base.model.User;
import cn.neuwljs.common.helper.UserInfoHelper;
import cn.neuwljs.common.network.MyErrorConsumer;
import cn.neuwljs.common.network.ResponseHelper;
import cn.neuwljs.common.util.ImageUtil;
import cn.neuwljs.common.util.LogUtil;
import cn.neuwljs.common.util.StringUtil;
import cn.neuwljs.common.util.XmlIOUtil;
import cn.neuwljs.module_d.Api;
import cn.neuwljs.module_d.model.base.LostPropertyType;
import cn.neuwljs.module_d.model.base.Owner;
import cn.neuwljs.module_d.model.base.Publisher;
import cn.neuwljs.module_d.model.event.NotifyFragmentDBDRefreshSerialNumberEvent;
import cn.neuwljs.module_d.model.gson.SubmitPropertyResult;
import cn.neuwljs.module_d.model.submit.Property;
import cn.neuwljs.module_d.model.submit.SubmitProperty;
import cn.neuwljs.module_d.presenter.IFragmentDBCPresenter;
import cn.neuwljs.module_d.view.IFragmentDBCView;
import cn.neuwljs.module_d.view.impl.FragmentDBC;
import cn.neuwljs.module_d.view.impl.FragmentDBD;
import cn.neuwljs.network.Network;
import cn.neuwljs.ocr.Callback;
import cn.neuwljs.ocr.OCR;
import cn.neuwljs.ocr.OCRException;
import cn.neuwljs.ocr.Words;

import static cn.neuwljs.module_d.Constants.COLLEGE_MAX_LENGTH;
import static cn.neuwljs.module_d.Constants.NAME_MAX_LENGTH;
import static cn.neuwljs.module_d.Constants.OWNER_COLLEGE_DEFAULT;
import static cn.neuwljs.module_d.Constants.OWNER_COLLEGE_LIMITED;
import static cn.neuwljs.module_d.Constants.OWNER_ID_DEFAULT;
import static cn.neuwljs.module_d.Constants.OWNER_ID_LIMITED;
import static cn.neuwljs.module_d.Constants.OWNER_NAME_DEFAULT;
import static cn.neuwljs.module_d.Constants.OWNER_NAME_LIMITED;
import static cn.neuwljs.module_d.Constants.PHOTO_FILE_NAME;
import static cn.neuwljs.module_d.Constants.PROPERTY_IS_ALREADY_SAVED;
import static cn.neuwljs.module_d.Constants.PUBLISHER_NAME_DEFAULT;
import static cn.neuwljs.module_d.Constants.XML_KEY_UNDONE_PROPERTY;

public class FragmentDBCPresenterImpl
        implements IFragmentDBCPresenter, MyErrorConsumer.OnErrorListener, Callback<Words> {

    private static final String TAG = "FragmentDBCPresenterImpl";

    private IFragmentDBCView mFragmentDBCView;

    private Context mContext;

    // 待上传的bean
    private Property mProperty;

    public FragmentDBCPresenterImpl(FragmentDBC fragmentDBC) {
        //对应的fragment的引用
        mContext = fragmentDBC.obtainContext ();
    }

    @Override
    public void attach(IFragmentDBCView fragmentDBCView) {
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

        File file = new File (mContext.getExternalCacheDir (), PHOTO_FILE_NAME);
        Bitmap bitmap = BitmapFactory.decodeFile (file.getPath ());
        if (bitmap != null) {
            OCR.recognize (bitmap, this);
        }
    }

    @Override
    public Publisher loadPublisher() {
        Publisher publisher = null;

        // 从xml中拿到用户信息
        User user = UserInfoHelper.getUserInfo (mContext);
        if (user != null) {
            publisher = new Publisher ();

            // 拿到头像
            String headImageStr = user.getHeadImage ();
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
        Network.query ()
                .create (Api.class)
                .uploadProperty (submitProperty)
                .compose (Network.changeScheduler ())
                .compose (ResponseHelper.transform ())
                .subscribe (submitPropertyResults -> {
                    // 关闭对话框,通知最后一页更新
                    mFragmentDBCView.dismissLoading ();
                    SubmitPropertyResult result = submitPropertyResults.get (0);
                    if(result != null){
                        notifyFragmentDBDRefresh (result.getSerialNumber ());
                        mFragmentDBCView.goToNextPage ();
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

        XmlIOUtil.xmlPut (XML_KEY_UNDONE_PROPERTY, propertyJson, mContext);
        mFragmentDBCView.showToast (PROPERTY_IS_ALREADY_SAVED);
    }

    /**
     * {@link cn.neuwljs.common.network.MyErrorConsumer.OnErrorListener}
     */
    @Override
    public void onError() {
        mFragmentDBCView.dismissLoading ();
        mFragmentDBCView.showFailure ();
    }


    /**
     * {@link Callback<Words>}
     */
    @Override
    public void onSuccess(Words words) {
        mFragmentDBCView.showOwner (convertWords2Owner (words));
    }

    @Override
    public void onFailure(OCRException e) {

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
     * 检查即将上传的数据的格式
     * @param property property
     * @return 格式正确就返回true，错误返回false
     */
    private boolean checkFormat(Property property){
        if(StringUtil.isEmpty (property.getOwnerName ()) || OWNER_NAME_DEFAULT.equals (property.getOwnerName ())){

            // 名字是空的
            mFragmentDBCView.showToast (OWNER_NAME_LIMITED);
            return false;
        }
        if(StringUtil.isEmpty (property.getOwnerId ()) || OWNER_ID_DEFAULT.equals (property.getOwnerId ())){

            // 学号是空的
            mFragmentDBCView.showToast (OWNER_ID_LIMITED);
            return false;
        }
        if(StringUtil.isEmpty (property.getOwnerCollege ()) || OWNER_COLLEGE_DEFAULT.equals (property.getOwnerCollege ())){

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

    // 通知最后一页更新
    private void notifyFragmentDBDRefresh(String serialNumber){

        // 发送事件
        NotifyFragmentDBDRefreshSerialNumberEvent event = new NotifyFragmentDBDRefreshSerialNumberEvent ();
        event.setSerialNumber (serialNumber);
        EventBus.getDefault ().post (event);
    }
}
