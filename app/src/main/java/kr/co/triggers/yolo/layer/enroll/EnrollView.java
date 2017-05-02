package kr.co.triggers.yolo.layer.enroll;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.inject.Inject;

import kr.co.triggers.yolo.R;
import kr.co.triggers.yolo.component.activity.YoloView;
import kr.co.triggers.yolo.databinding.LayoutViewEnrollBinding;
import kr.co.triggers.yolo.inject.component.ActivityComponent;
import kr.co.triggers.yolo.inject.component.DaggerActivityComponent;
import kr.co.triggers.yolo.inject.module.ActivityModule;
import kr.co.triggers.yolo.layer.entry.EntryView;

public class EnrollView extends YoloView {
    private static final Pattern PATTERN_BIRTH = Pattern.compile("/^[0-9]+$/");
    public static final String FEMALE = "Female";
    public static final String MALE = "Male";
    private String gender;
    private Uri mCropImageUri;
    LayoutViewEnrollBinding binding;
    AlertDialog dialogDenyEnroll;

    @Inject
    EnrollPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ActivityComponent component = DaggerActivityComponent.builder().appComponent(getAppComponent()).activityModule(new ActivityModule(this)).build();
        component.inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.layout_view_enroll);
        binding.setPresenter(presenter);

        binding.femaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGender(FEMALE);
            }
        });

        binding.maleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGender(MALE);
            }
        });

        binding.enrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enroll();
            }
        });

        init();
    }

    public void init(){
        LayoutInflater layoutInflater = getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.layout_dialog_enroll_failure, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(layout);
        builder.setCancelable(false);

        TextView textViewOk = (TextView) layout.findViewById(R.id.text_view_ok);
        textViewOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                dialogDenyEnroll.dismiss();
            }
        });


        dialogDenyEnroll = builder.create();
        setGender(FEMALE);
    }

    public void enroll(){
        Editable editname = binding.enrollNickname.getText();
        Editable editbirth = binding.enrollBirth.getText();

        if(TextUtils.isEmpty(editname)){
            binding.enrollNickname.setError(getResources().getString(R.string.label_dialog_error_name));
            return;
        }

        if(TextUtils.isEmpty(editbirth) || editbirth.length()!=6 || !PATTERN_BIRTH.matcher(editbirth).matches()){
            binding.enrollBirth.setError(getResources().getString(R.string.label_dialog_error_birth));
            return;
        }

        String name = editname.toString();
        String url = binding.enrollProfile.getTag() != null ? String.valueOf(binding.enrollProfile.getTag()) : null;
        String accessToken = AccessToken.getCurrentAccessToken().getToken();
        Date birth;

        SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
        try {
            birth = format.parse(editbirth.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            //TODO : 에러 다이얼로그?
            return;
        }

        presenter.enroll(accessToken, name, birth, gender, url);
    }

    public void setGender(String gender){
        // TODO : click에 따른 이미지 변경
        this.gender = gender;
        if(gender.equalsIgnoreCase(FEMALE)){
            binding.femaleTxt.setTextColor(Color.WHITE);
            binding.maleTxt.setTextColor(Color.GRAY);
        }else{
            binding.femaleTxt.setTextColor(Color.GRAY);
            binding.maleTxt.setTextColor(Color.WHITE);
        }
    }

    public void enrollSuccess(){
        Toast.makeText(getApplicationContext(), "enroll success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), EntryView.class);
        startActivity(intent);
    }

    public void enrollFail(){
        // TODO : 가입 실패 다이얼로그
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                mCropImageUri = imageUri;
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                return;
            }
            startCropImageActivity(imageUri);
            return;
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                binding.enrollProfile.setImageURI(result.getUri());
                return;
            }

            if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(getApplicationContext(), "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
                return;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCropImageActivity(mCropImageUri);
            return;
        }
        Toast.makeText(getApplicationContext(), "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    public void onSelectImageClick(View view) {
        CropImage.startPickImageActivity(this);
    }
}
