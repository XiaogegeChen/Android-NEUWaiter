package debug;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import cn.neuwljs.ocr.Callback;
import cn.neuwljs.ocr.OCR;
import cn.neuwljs.ocr.OCRException;
import cn.neuwljs.ocr.R;
import cn.neuwljs.ocr.Words;

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.ocr_activity_test);

        Bitmap bitmap = BitmapFactory.decodeResource (getResources (), R.drawable.ocr_test_bitmap);

        findViewById (R.id.ocr_test_recognize_button).setOnClickListener(v ->
                OCR.recognize (bitmap, new Callback<Words> () {
                    @Override
                    public void onSuccess(Words words) {

                        Gson gson = new Gson ();
                        String result = gson.toJson (words);
                        Log.d (TAG, "onSuccess: " + result);
                    }

                    @Override
                    public void onFailure(OCRException e) {
                        Log.d (TAG, "onFailure: " + e.getErrorMessage ());
                    }
                })
        );

    }
}
