package debug;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import cn.neuwljs.photo.PhotoHelper;
import cn.neuwljs.photo.R;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.photo_test_activity);
        
        findViewById (R.id.photo_test_button).setOnClickListener (v -> PhotoHelper.getPhoto (getSupportFragmentManager (), new PhotoHelper.Callback () {
            @Override
            public void onCancel() {
                Log.d (TAG, "onCancel: ");
            }

            @Override
            public void onTakePhotoSuccess(Bitmap bitmap) {
                Log.d (TAG, "onTakePhotoSuccess: ");
            }

            @Override
            public void onTakePhotoFailure() {
                Log.d (TAG, "onTakePhotoFailure: ");
            }

            @Override
            public void onChoosePhotoSuccess(Bitmap bitmap) {
                Log.d (TAG, "onChoosePhotoSuccess: ");
            }

            @Override
            public void onChoosePhotoFailure() {
                Log.d (TAG, "onChoosePhotoFailure: ");
            }
        }));
        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
    }
}
