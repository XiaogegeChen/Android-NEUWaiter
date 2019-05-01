package debug;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import cn.neuwljs.common.R;
import cn.neuwljs.common.dialog.UploadFailDialog;
import cn.neuwljs.common.dialog.UploadProgressDialog;
import cn.neuwljs.widget.dialog.Widget_MessageDialog;

public class TestActivity
        extends AppCompatActivity
        implements Widget_MessageDialog.OnClickListener {

    private static final String TAG = "TestActivity";
    private static final String UPLOAD_FAIL_DIALOG_TAG = "upload_fail_dialog";
    private static final String UPLOAD_PROGRESS_DIALOG_TAG = "upload_progress_dialog";

    private UploadFailDialog mUploadFailDialog;
    private UploadProgressDialog mUploadProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.common_activity_test);

        mUploadFailDialog = new UploadFailDialog ();
        mUploadProgressDialog = new UploadProgressDialog ();
        mUploadFailDialog.setListener (this);

        // 测试UploadFailDialog
        findViewById (R.id.common_test_upload_fail_dialog_button)
                .setOnClickListener (v ->
                        mUploadFailDialog.show (getSupportFragmentManager (), UPLOAD_FAIL_DIALOG_TAG)
                );

        findViewById (R.id.common_test_upload_progress_dialog_button)
                .setOnClickListener (v ->
                        mUploadProgressDialog.show (getSupportFragmentManager (), UPLOAD_PROGRESS_DIALOG_TAG)
                );

    }

    /**
     * {@link cn.neuwljs.widget.dialog.Widget_MessageDialog.OnClickListener}
     */
    @Override
    public void onCancel() {
        Log.d (TAG, "onCancel: ");
    }

    @Override
    public void onConfirm() {
        Log.d (TAG, "onConfirm: ");
    }
}
