package debug;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.neuwljs.photo.PhotoHelper;
import cn.neuwljs.photo.R;

public class TestFragment extends Fragment {

    private static final String TAG = "TestFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from (getContext ()).inflate (R.layout.photo_test_fragment, container, false);

        view.findViewById (R.id.photo_test_fragment_button).setOnClickListener (v -> PhotoHelper.getPhoto (getFragmentManager (), new PhotoHelper.Callback () {
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

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        DialogFragment fragment = (DialogFragment) getFragmentManager ().findFragmentByTag (PhotoHelper.getDialogTag ());

        if(fragment != null){

            Log.d (TAG, "onActivityResult: ");
            
            fragment.onActivityResult (requestCode, resultCode, data);
        }

    }
}
