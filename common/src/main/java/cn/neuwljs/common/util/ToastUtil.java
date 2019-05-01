package cn.neuwljs.common.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    private ToastUtil(){
    }

    public static void showToast(Context context, String message){
        Toast.makeText (context, message, Toast.LENGTH_SHORT).show ();
    }
}
