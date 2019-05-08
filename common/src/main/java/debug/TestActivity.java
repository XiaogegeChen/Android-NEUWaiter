package debug;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;

import cn.neuwljs.common.R;
import cn.neuwljs.common.dialog.DatePickerDialog;
import cn.neuwljs.common.dialog.TimePickerDialog;
import cn.neuwljs.common.dialog.UploadFailDialog;
import cn.neuwljs.common.dialog.UploadProgressDialog;
import cn.neuwljs.widget.dialog.Widget_MessageDialog;

public class TestActivity
        extends AppCompatActivity
        implements Widget_MessageDialog.OnClickListener, CalendarView.OnDateChangeListener, TimePicker.OnTimeChangedListener {

    private static final String TAG = "TestActivity";
    private static final String UPLOAD_FAIL_DIALOG_TAG = "upload_fail_dialog";
    private static final String UPLOAD_PROGRESS_DIALOG_TAG = "upload_progress_dialog";
    private static final String DATE_PICKER_DIALOG_TAG = "date_picker_dialog";
    private static final String TIME_PICKER_DIALOG_TAG = "time_picker_dialog";


    private UploadFailDialog mUploadFailDialog;
    private UploadProgressDialog mUploadProgressDialog;
    private DatePickerDialog mDatePickerDialog;
    private TimePickerDialog mTimePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.common_activity_test);

        mUploadFailDialog = new UploadFailDialog ();
        mUploadProgressDialog = new UploadProgressDialog ();
        mDatePickerDialog = new DatePickerDialog ();
        mTimePickerDialog = new TimePickerDialog ();

        mDatePickerDialog.setOnDateChangeListener (this);
        mUploadFailDialog.setListener (this);
        mTimePickerDialog.setOnTimeChangedListener (this);

        // 测试UploadFailDialog
        findViewById (R.id.common_test_upload_fail_dialog_button)
                .setOnClickListener (v ->
                        mUploadFailDialog.show (getSupportFragmentManager (), UPLOAD_FAIL_DIALOG_TAG)
                );

        findViewById (R.id.common_test_upload_progress_dialog_button)
                .setOnClickListener (v ->
                        mUploadProgressDialog.show (getSupportFragmentManager (), UPLOAD_PROGRESS_DIALOG_TAG)
                );

        findViewById (R.id.common_test_date_dialog_button)
                .setOnClickListener (v ->
                        mDatePickerDialog.show (getSupportFragmentManager (), DATE_PICKER_DIALOG_TAG)
                );

        findViewById (R.id.common_test_time_dialog_button)
                .setOnClickListener (v ->
                        mTimePickerDialog.show (getSupportFragmentManager (), TIME_PICKER_DIALOG_TAG)
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

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        Log.d (TAG, "onSelectedDayChange: " + year + ":" + month + ":" + dayOfMonth);
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        Log.d (TAG, "onTimeChanged: " + hourOfDay + " :" + minute);
    }
}
