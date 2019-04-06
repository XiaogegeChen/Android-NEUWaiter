package com.neuwljs.wallsmalltwo.presenter.impl;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.neuwljs.wallsmalltwo.presenter.PresenterContract;
import com.neuwljs.wallsmalltwo.util.ImageUtil;
import com.neuwljs.wallsmalltwo.util.ToastUtil;
import com.neuwljs.wallsmalltwo.view.ViewContract;
import com.neuwljs.wallsmalltwo.view.fragment.FragmentDBB;
import com.neuwljs.wallsmalltwo.view.fragment.FragmentDBC;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.neuwljs.wallsmalltwo.model.Constant.NO_PERMISSION;
import static com.neuwljs.wallsmalltwo.model.Constant.PHOTO_FILE_NAME;

public class FragmentDBBPresenterImpl
        implements PresenterContract.FragmentDBBPresenter, OnPermission {

    private static final int TAKE_PHOTO = 1;
    private static final int CHOOSE_PHOTO = 2;

    //对应的fragment的引用
    private FragmentDBB mFragmentDBB;

    private Context mContext;

    private Uri mImageUri;

    public FragmentDBBPresenterImpl(FragmentDBB fragmentDBB) {
        mFragmentDBB = fragmentDBB;
        mContext = mFragmentDBB.obtainContext ();
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
    public void cancel() {
        mFragmentDBBView.dismissDialog ();
    }

    @Override
    public void takePhoto() {
        //关闭dialog
        mFragmentDBBView.dismissDialog ();

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

        //设置uri
        if(Build.VERSION.SDK_INT >= 24){
            mImageUri = FileProvider.getUriForFile (mContext,
                    "com.neuwljs.wallsmalltwo.fileprovider", file);
        }else{
            mImageUri = Uri.fromFile (file);
        }

        //启动相机
        Intent intent = new Intent ("android.media.action.IMAGE_CAPTURE");
        intent.putExtra (MediaStore.EXTRA_OUTPUT, mImageUri);
        mFragmentDBB.startActivityForResult (intent, TAKE_PHOTO);
    }

    @Override
    public void selectPhoto() {
        //关闭dialog
        mFragmentDBBView.dismissDialog ();

        //请求权限并打开相册
        XXPermissions.with (mFragmentDBB.obtainActivity ())
                .permission (Permission.Group.STORAGE)
                .request (this);
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
    public void handleActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    //拿到图片并显示
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream (mContext
                                .getContentResolver ().openInputStream (mImageUri));
                        mFragmentDBBView.showImage (bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace ();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    if(Build.VERSION.SDK_INT >= 19){
                        handleImageOnKitKat(intent);
                    }else{
                        handleImageBeforeKitkat(intent);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * {@link OnPermission}
     */
    @Override
    public void hasPermission(List<String> granted, boolean isAll) {
        //拿到存储权限,打开相机
        if(isAll){
            openAlbum ();
        }
    }

    @Override
    public void noPermission(List<String> denied, boolean quick) {
        //永久拒绝,打开设置
        if(quick){
            XXPermissions.gotoPermissionSettings (mContext);
        }else {
            ToastUtil.showToast (mContext, NO_PERMISSION);
        }
    }


    /**
     * 打开相册
     */
    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType ("image/*");
        mFragmentDBB.startActivityForResult (intent,CHOOSE_PHOTO);
    }

    //4.4以上版本的处理得到的图片的方法
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        Uri uri = data.getData ();
        String imagePath = null;
        if(DocumentsContract.isDocumentUri (mContext,uri)){
            String docId = DocumentsContract.getDocumentId (uri);
            assert uri != null;
            if("com.android.providers.media.documents".equals (uri.getAuthority ())){
                String id = docId.split (":")[1];
                String selection = MediaStore.Images.Media._ID+"="+id;
                imagePath = getImagePath (MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.download.documents".equals (uri.getAuthority ())){
                Uri contentUri = ContentUris.withAppendedId (
                        Uri.parse ("content://downloads/public_downloads"),Long.valueOf (docId));
                imagePath = getImagePath (contentUri,null);
            }
        }else {
            assert uri != null;
            if("content".equalsIgnoreCase (uri.getScheme ())){
                imagePath = getImagePath (uri,null);
            }else if("file".equalsIgnoreCase (uri.getScheme ())){
                imagePath = uri.getPath ();
            }
        }
        displayImage (imagePath);
    }

    //4.4以下版本的处理得到的图片的方法
    private void handleImageBeforeKitkat(Intent data){
        Uri uri = data.getData ();
        String imagePath = getImagePath (uri,null);
        displayImage (imagePath);
    }

    private String getImagePath(Uri uri,String selection){
        String path = null;
        Cursor cursor = mContext.getContentResolver ().query (uri,null,selection
                ,null,null);
        if(cursor != null){
            if(cursor.moveToFirst ()){
                path = cursor.getString (cursor.getColumnIndex (MediaStore.Images.Media.DATA));
            }
            cursor.close ();
        }
        return path;
    }

    //显示图片并存储
    private void displayImage(String path){
        if(path != null){
            Bitmap bitmap = BitmapFactory.decodeFile (path);

            // 存入文件中
            ImageUtil.saveImage (bitmap, new File (mContext.getExternalCacheDir (), PHOTO_FILE_NAME));
            mFragmentDBBView.showImage (bitmap);
        }
    }
}
