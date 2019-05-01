package cn.neuwljs.photo;

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
import android.widget.Toast;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class TakePhotoDialog
        extends DialogFragment
        implements View.OnClickListener, OnPermission {

    /**
     * 获取权限失败
     */
    private static final String NO_PERMISSION = "获取权限失败,部分功能无法使用";

    // 文件位置为sdcard/Android/cn.neuwljs.neuwaiter/files/Bitmap/photo_temp.jpg
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
        View view = LayoutInflater.from (getContext ()).inflate (R.layout.photo_take_photo_dialog, null);

        //点击事件
        view.findViewById (R.id.photo_take_photo_camera).setOnClickListener (this);
        view.findViewById (R.id.photo_take_photo_album).setOnClickListener (this);
        view.findViewById (R.id.photo_take_photo_cancel).setOnClickListener (this);

        AppCompatDialog appCompatDialog = new AppCompatDialog (getContext (), R.style.photo_base_dialog_style);

        // 配置相关设置
        appCompatDialog.setContentView (view);
        appCompatDialog.setCancelable (true);
        WindowManager.LayoutParams params = appCompatDialog.getWindow ().getAttributes ();
        params.gravity = Gravity.BOTTOM;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = R.style.photo_dialog_bottom_anim;
        appCompatDialog.getWindow ().setAttributes (params);

        return appCompatDialog;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // 先拿到context，关闭之后就不能getContext了
        Context context = getContext ();

        // 关闭对话框
        dismiss ();

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
                    Bitmap bitmap = ImageUtil.handleImageOnKitKat (context, intent);
                    if(bitmap != null){

                        // 成功，回调onChoosePhotoSuccess()
                        callback.onChoosePhotoSuccess (bitmap);
                    }else {

                        // 失败，回调onChoosePhotoFailure()
                        callback.onChoosePhotoFailure ();
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
     * {@link View.OnClickListener}
     */
    @Override
    public void onClick(View v) {
        int i = v.getId ();
        if (i == R.id.photo_take_photo_camera) {
            mListener.onCameraClick ();
        } else if (i == R.id.photo_take_photo_album) {
            mListener.onAlbumClick ();
        } else if (i == R.id.photo_take_photo_cancel) {
            mListener.onCancelClick ();
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
                    "cn.neuwljs.photo.fileprovider", file);
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
            Toast.makeText (getContext (), NO_PERMISSION, Toast.LENGTH_SHORT).show ();
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
