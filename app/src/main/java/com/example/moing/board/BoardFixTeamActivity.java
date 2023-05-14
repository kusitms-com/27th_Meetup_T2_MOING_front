package com.example.moing.board;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.moing.R;

import java.io.File;
import java.util.Calendar;

public class BoardFixTeamActivity extends AppCompatActivity {
    private static final String TAG = "BoardFixTeam";
    private static final int READ_EXTERNAL_STORAGE_REQUEST = 0;

    // 소모임 이름
    private String beforeName;

    // 종료 날짜
    private Button btnEndDate;
    private String beforeEndDate="", afterEndDate="";

    // 소모임 이미지 업로드
    private Button btnImageUpload;
    private Button btnImageUploadRe;
    private ImageView ivImageUpload;
    private String beforeImage;

    // 소모임 정보 수정
    private Button btnFixTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_fix_team);


        // 구현 예정 - 서버 통신으로 소모임 정보 받아오기
        // 기존 소모임 이름, 종료 날짜, 사진
        beforeName = "name";
        beforeEndDate = "2023/5/14";
        beforeImage = "현재 소모임 사진";

        // 소모임 이름
        // 소모임 이름
        EditText etName = findViewById(R.id.board_fix_et_name);
        TextView tvName = findViewById(R.id.board_fix_tv_name);
        TextView tvNameCnt = findViewById(R.id.board_fix_tv_name_count);
        // onFocusChangeListener, TextWatcher 등록
        setFocus(etName, tvName, tvNameCnt);
        setTextWatcher(etName, tvNameCnt, 10);
        // 초기값 설정
        etName.setHint("name");

        // 소모임 종료 날짜
        TextView tvEndDate = findViewById(R.id.board_fix_tv_end_date);
        btnEndDate = findViewById(R.id.board_fix_btn_end_date);
        // onClickListener 등록
        btnEndDate.setOnClickListener(onEndDateClickListener);
        // 초기값 설정
        btnEndDate.setHint("2023/05/14");

        // 소모임 사진 업로드
        btnImageUpload = findViewById(R.id.board_fix_btn_image_upload);
        btnImageUploadRe = findViewById(R.id.board_fix_btn_image_upload_re);
        ivImageUpload = findViewById(R.id.board_fix_iv_image_upload);
        // onClickListener 등록
        btnImageUpload.setOnClickListener(onImageUploadClickListener);
        btnImageUploadRe.setOnClickListener(onImageUploadClickListener);

        // 소모임 정보 수정
        btnFixTeam = findViewById(R.id.board_fix_btn_fix_team);
        // onClickListener 등록
        btnFixTeam.setOnClickListener(onFixTeamClickListener);
        btnFixTeam.setClickable(false);
    }
    /** Button click 시 변경된 소모임 이름 or 종료날짜 or 사진 서버에 전송 구현 예정) 및 종료 **/
    View.OnClickListener onFixTeamClickListener = view -> finish();

    /** Button click 시 소모임 종료 날짜 설정 **/
    View.OnClickListener onEndDateClickListener = view -> {
        Calendar calendar = Calendar.getInstance();
        int initialYear = calendar.get(Calendar.YEAR); // 년
        int initialMonth = calendar.get(Calendar.MONTH); // 월
        int initialDay = calendar.get(Calendar.DAY_OF_MONTH); // 일

        // DatePickerDialog를 통해 날짜 선택
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePickerView, year, month, dayOfMonth) -> {
            // 선택한 날짜
            afterEndDate = year + "/" + (month + 1) + "/" + dayOfMonth;

            // 선택한 날짜와 기존 값이 다른 경우에만 버튼을 활성화
            if (!beforeEndDate.equals(afterEndDate)) {
                activeBtnFixTeam();
            } else {
                inactiveBtnFixTeam();
            }

        }, initialYear, initialMonth, initialDay);
        datePickerDialog.show();
    };

    /** 버튼 클릭 시 권한 확인 후 갤러리 접근 **/
    View.OnClickListener onImageUploadClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // API 레벨 23 이상 READ_EXTERNAL_STORAGE 권환 필요
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // 권한 X - 요청
                    ActivityCompat.requestPermissions(BoardFixTeamActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_REQUEST);
                } else {
                    // 권한 O - 갤러리 접근
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryLauncher.launch(intent);
                }
            }
        }
    };


    /** 갤러리에서 이미지 선택을 위한 ActivityResultLauncher **/
    ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // 선택된 이미지 처리
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();

                        // Glide 를 통한 이미지 로딩
                        Glide.with(getApplicationContext())
                                .load(uri)
                                .transform(new RoundedCorners(10))
                                .into(ivImageUpload);

                        // visibility 설정
                        btnImageUpload.setVisibility(View.GONE);
                        ivImageUpload.setVisibility(View.VISIBLE);
                        btnImageUploadRe.setVisibility(View.VISIBLE);

                        checkInputs();
                    }
                }
            }
    );

    /** EditText focus 시 제목 색상 변경 **/
    private void setFocus(EditText editText, TextView textView, TextView textViewCnt) {
        editText.setOnFocusChangeListener((view, isFocused) -> {
            if(isFocused){
                textView.setTextColor(getResources().getColor(R.color.main_dark_200));
                textViewCnt.setTextColor(getResources().getColor(R.color.main_dark_200));
            }
            else{
                textView.setTextColor(getResources().getColor(R.color.secondary_grey_black_7));
                textViewCnt.setTextColor(getResources().getColor(R.color.secondary_grey_black_7));
            }
        });
    }

    /** EditText 입력 시 입력된 글자 수 변경 **/
    private void setTextWatcher(EditText editText, TextView countTextView, int maxLength) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String result = "(" + editable.length() + "/" + maxLength + ")";
                countTextView.setText(result);
                String afterName = editable.toString();
                if(afterName.length() >0 && !beforeName.equals(editable.toString())){
                    activeBtnFixTeam();
                }
                else inactiveBtnFixTeam();
            }
        });
    }

    /** 소모임 이름, 종료날짜, 이미지 중 하나라도 변경되었으면 버튼 활성화 **/
    private void checkInputs(){


    }

    /** btnFixTeam 활성화 **/
    private void activeBtnFixTeam(){
        btnFixTeam.setBackgroundResource(R.drawable.button_round_black1);
        btnFixTeam.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.secondary_grey_black_12));
        btnFixTeam.setClickable(true);
    }

    /** btnFixTeam 비활성화 **/
    private void inactiveBtnFixTeam(){
        btnFixTeam.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.secondary_grey_black_10));
        btnFixTeam.setBackgroundResource(R.drawable.button_round_black12);
        btnFixTeam.setClickable(false);
    }

    /** AWS S3에 파일 업로드를 수행하는 메소드  **/
    public void uploadWithTransferUtility(String fileName, File file) {

        // accessKey 와 secretKey 를 사용하는 기본 자격 증명 객체
        AWSCredentials awsCredentials = new BasicAWSCredentials("AKIA5L3BRFIIWLIJ62JQ", "xywH1VJjcAxp0xcEyFuVrOnknLp3XtWsyT2KaFEx");    // IAM 생성하며 받은 것 입력

        // AWS S3 서비스와 상호 작용하기 위한 클라이언트 객체
        AmazonS3Client s3Client = new AmazonS3Client(awsCredentials, Region.getRegion(Regions.AP_NORTHEAST_2));

        // Amazon S3 전송 관리를 쉽게 할 수 있도록 하는 고급 API - for Image File Upload
        TransferUtility transferUtility = TransferUtility.builder().s3Client(s3Client).context(getApplicationContext()).build();
        TransferNetworkLossHandler.getInstance(getApplicationContext());
        TransferObserver uploadObserver = transferUtility.upload("moing-images", fileName, file);    // (bucket api, file, file)

        uploadObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                //if (state == TransferState.COMPLETED) { // 업로드 성공 }
            }

            @Override
            public void onProgressChanged(int id, long current, long total) {
                int done = (int) (((double) current / total) * 100.0);
                Log.d(TAG, "UPLOAD - - ID: $id, percent done = $done");
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.d(TAG, "UPLOAD ERROR - - ID: $id - - EX:" + ex.toString());
            }
        });
    }

    /** Uri 에서 절대경로를 get하는 메소드**/
    public static String getAbsolutePathFromUri(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android Q 이상에서는 uri.getPath()가 /document/... 형태로 반환되므로
            // getContentResolver().query()를 사용하여 실제 경로를 가져와야 합니다.
            try (Cursor cursor = context.getContentResolver().query(uri, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    return cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                }
            }
        } else {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    String path = cursor.getString(column_index);
                    cursor.close();
                    return path;
                }
                cursor.close();
            }
        }
        return null;
    }
}