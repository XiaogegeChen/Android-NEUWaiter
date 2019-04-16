package com.neuwljs.wallsmalltwo.view.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.neuwljs.wallsmalltwo.R;
import com.neuwljs.wallsmalltwo.util.ImageUtil;
import com.neuwljs.wallsmalltwo.util.LogUtil;
import com.neuwljs.wallsmalltwo.util.ToastUtil;
import com.neuwljs.wallsmalltwo.util.helper.PhotoHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.neuwljs.wallsmalltwo.model.Constant.NO_PERMISSION;

public class TakePhotoDialog
        extends DialogFragment
        implements View.OnClickListener, OnPermission {

    private static final String TAG = "TakePhotoDialog";

    // 文件位置为sdcard/Android/com.neuwljs.wallsmalltwo/files/Bitmap/photo_temp.jpg
    private static final String FILE_PATH = "photo_temp.jpg";

    // 请求码
    private static final int TAKE_PHOTO = 0;
    private static final int CHOOSE_PHOTO = 1;

    // 图片的uri地址
    private Uri imageUri;

    // 三个按键的点击监听器
    private OnClickListener mListener;

    // 拍照结果的回调
    private static List<PhotoHelper.Callback> sCallbackList;

    public void setListener(OnClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from (getContext ()).inflate (R.layout.fragment_d_b_b_dialog, null);

        //点击事件
        view.findViewById (R.id.fragment_d_b_b_camera).setOnClickListener (this);
        view.findViewById (R.id.fragment_d_b_b_album).setOnClickListener (this);
        view.findViewById (R.id.fragment_d_b_b_cancel).setOnClickListener (this);

        AppCompatDialog appCompatDialog = new AppCompatDialog (getContext (), R.style.BaseDialogStyle);

        // 配置相关设置
        appCompatDialog.setContentView (view);
        appCompatDialog.setCancelable (true);
        WindowManager.LayoutParams params = appCompatDialog.getWindow ().getAttributes ();
        params.gravity = Gravity.BOTTOM;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = R.style.DialogBottomAnim;
        appCompatDialog.getWindow ().setAttributes (params);

        return appCompatDialog;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // 先拿到context，关闭之后就不能getContext了
        Context context = getContext ();

        // 关闭对话框
        dismiss ();

        LogUtil.d (TAG, "requestCode: " + requestCode);

        // 拿到callback，如果为空直接返回
        PhotoHelper.Callback callback = sCallbackList.get (0);

        if(callback == null){
            return;
        }

        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream (context
                                .getContentResolver ().openInputStream (imageUri));

                        // 成功，回调onTakePhotoSuccess()
                        callback.onTakePhotoSuccess (bitmap);
                    } catch (FileNotFoundException e) {

                        // 失败，回调onTakePhotoFailure()
                        callback.onTakePhotoFailure ();
                        e.printStackTrace ();
                    }
                }else{

                    // 失败，回调onTakePhotoFailure()
                    callback.onTakePhotoFailure ();
                }
                break;

            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    if(Build.VERSION.SDK_INT >= 19){
                        Bitmap bitmap = ImageUtil.handleImageOnKitKat (context, intent);
                        if(bitmap != null){

                            // 成功，回调onChoosePhotoSuccess()
                            callback.onChoosePhotoSuccess (bitmap);
                        }else {

                            // 失败，回调onChoosePhotoFailure()
                            callback.onChoosePhotoFailure ();
                        }
                    }else{
                        Bitmap bitmap = ImageUtil.handleImageBeforeKitkat (context, intent);

                        if(bitmap != null){

                            // 成功，回调onChoosePhotoSuccess()
                            callback.onChoosePhotoSuccess (bitmap);
                        }else {

                            // 失败，回调onChoosePhotoFailure()
                            callback.onChoosePhotoFailure ();
                        }
                    }
                }else{

                    // 失败，回调onChoosePhotoFailure()
                    callback.onChoosePhotoFailure ();
                }
                break;

            default:
                break;
        }
    }

    public void prepareTakePhoto(PhotoHelper.Callback callback){
        sCallbackList = new ArrayList<> ();
        sCallbackList.add (callback);

        openCamera ();
    }

    public void prepareChoosePhoto(PhotoHelper.Callback callback){
        sCallbackList = new ArrayList<> ();
        sCallbackList.add (callback);

        // 查看权限
        XXPermissions.with (getActivity ())
                .permission (Permission.Group.STORAGE)
                .request (this);
    }

    /**
     * {@link android.view.View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
        switch (v.getId ()){
            case R.id.fragment_d_b_b_camera:
                mListener.onCameraClick ();
                break;
            case R.id.fragment_d_b_b_album:
                mListener.onAlbumClick ();
                break;
            case R.id.fragment_d_b_b_cancel:
                mListener.onCancelClick ();
                break;
            default:
                break;
        }
    }

    // 打开相机
    private void openCamera(){
        File bitmapFile = new File (getContext ().getExternalFilesDir (null), "Bitmap");
        if(!bitmapFile.exists ()){
            bitmapFile.mkdirs ();
        }

        File file = new File (bitmapFile, FILE_PATH);

        if(file.exists ()){
            file.delete ();
        }
        try {
            file.createNewFile ();
        } catch (IOException e) {
            e.printStackTrace ();
        }

        // 拿到拍照后图片位置的uri
        if(Build.VERSION.SDK_INT >= 24){
            imageUri = FileProvider.getUriForFile (getContext (),
                    "com.neuwljs.wallsmalltwo.fileprovider", file);
        }else{
            imageUri = Uri.fromFile (file);
        }

        //启动相机
        Intent intent = new Intent ("android.media.action.IMAGE_CAPTURE");
        intent.putExtra (MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult (intent, TAKE_PHOTO);
    }

    // 打开相册
    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType ("image/*");
        startActivityForResult (intent, CHOOSE_PHOTO);
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
            XXPermissions.gotoPermissionSettings (getContext ());
        }else {

            // 拿到callback，如果为空直接返回
            PhotoHelper.Callback callback = sCallbackList.get (0);

            if(callback == null){
                return;
            }

            // 失败，回调onChoosePhotoFailure()
            callback.onChoosePhotoFailure ();
            ToastUtil.showToast (getContext(), NO_PERMISSION);
        }
    }

    /**
     * 三个按键的点击监听器
     */
    public interface OnClickListener{
        void onCancelClick();
        void onCameraClick();
        void onAlbumClick();
    }
}
