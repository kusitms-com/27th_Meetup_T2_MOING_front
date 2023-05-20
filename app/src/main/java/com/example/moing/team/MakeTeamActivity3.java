package com.example.moing.team;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageButton;
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
import com.example.moing.Request.MakeTeamRequest;
import com.example.moing.Response.MakeTeamResponse;
import com.example.moing.retrofit.ChangeJwt;
import com.example.moing.retrofit.RetrofitAPI;
import com.example.moing.retrofit.RetrofitClientJwt;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakeTeamActivity3 extends AppCompatActivity {
    private static final String TAG = "MakeTeamActivity3";
    private static final int READ_EXTERNAL_STORAGE_REQUEST = 0;
    private Uri uri;
    private EditText etIntroduce;
    private EditText etResolution;
    private Button btnImageUpload;
    private Button btnImageUploadRe;
    private ImageView ivImageUpload;
    private Button btnCreateTeam;
    private ImageView ivProgressBar;

    //Intent Data
    private String major, name, cnt, data, predictDate;
    private SharedPreferences sharedPreferences;

    private static final String PREF_NAME = "Token";
    private static final String JWT_ACCESS_TOKEN = "JWT_access_token";
    private static final String JWT_REFRESH_TOKEN = "JWT_refresh_token";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_team3);

        // Intent 를 통한 이전 값 전달 받기
        Intent intent = getIntent();
        name = intent.getStringExtra("name"); // 소모임 이름
        cnt = intent.getStringExtra("member"); // 소모임 구성원 수
        data = intent.getStringExtra("startDate"); // 소모임 시작일
        predictDate = intent.getStringExtra("predict"); // 소모임 예상 활동 기간
        major = intent.getStringExtra("major"); // 소모임 목표

        /** API 통신을 위한 헤더에 담을 토큰값 가져오기 **/
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // 뒤로 가기
        ImageButton btnBack = findViewById(R.id.make_team3_btn_back);
        btnBack.setOnClickListener(view -> finish());

        // 소모임 소개
        etIntroduce = findViewById(R.id.make_team3_et_introduce);
        TextView tvIntroduce = findViewById(R.id.make_team3_tv_introduce);
        TextView tvIntroduceCnt = findViewById(R.id.make_team3_tv_introduce_count);

        // 소모임장 각오
        etResolution = findViewById(R.id.make_team3_et_resolution);
        TextView tvResolution = findViewById(R.id.make_team3_tv_resolution);
        TextView tvResolutionCnt = findViewById(R.id.make_team3_tv_resolution_count);

        // 소모임 생성 및 진행 상황
        btnCreateTeam = findViewById(R.id.make_team3_btn_create_team);
        ivProgressBar = findViewById(R.id.make_team3_iv_progress);

        // 소모임 사진 업로드
        btnImageUpload = findViewById(R.id.make_team3_btn_image_upload);
        btnImageUploadRe = findViewById(R.id.make_team3_btn_image_upload_re);
        ivImageUpload = findViewById(R.id.make_team3_iv_image_upload);

        // onFocusChangeListener, TextWatcher 등록
        setFocus(etIntroduce, tvIntroduce, tvIntroduceCnt);
        setTextWatcher(etIntroduce, tvIntroduceCnt, 300);
        setFocus(etResolution, tvResolution, tvResolutionCnt);
        setTextWatcher(etResolution, tvResolutionCnt, 100);

        // onClickListener 등록
        btnImageUpload.setOnClickListener(onImageUploadClickListener);
        btnImageUploadRe.setOnClickListener(onImageUploadClickListener);
        btnCreateTeam.setOnClickListener(onCreateTeamClickListener);

        // 모임 생성 버튼 비활성화
        btnCreateTeam.setClickable(false);

    }

    /** 버튼 클릭 시 소모임 Data 전송 후 다음 화면 실행 **/
    View.OnClickListener onCreateTeamClickListener = view -> {
        // 구현 예정 - 소모임 Data Retrofit 사용하여 전달
        String token = sharedPreferences.getString(JWT_ACCESS_TOKEN, null);
        Log.d("MAKETEAMACITIVITY3", token);
        String period = predictDate.substring(0,1);
        String category= "";

        switch(major) {
            case "스포츠/운동":
                category="SPORTS";
                break;
            case "생활습관 개선":
                category = "HABIT";
                break;
            case "시험/취업준비":
                category = "TEST";
                break;
            case "스터디/공부":
                category = "STUDY";
                break;
            case "독서":
                category = "READING";
                break;
            case "그외 자기계발":
                category = "ETC";
                break;
        }

        RetrofitAPI apiService = RetrofitClientJwt.getApiService(token);
        MakeTeamRequest makeTeamRequest = new MakeTeamRequest(category, etIntroduce.getText().toString(), name,
                Integer.parseInt(period), Integer.parseInt(cnt), uri.toString(), etResolution.getText().toString(), data);

        Call<MakeTeamResponse> call = apiService.makeTeam(token, makeTeamRequest);
        call.enqueue(new Callback<MakeTeamResponse>() {
            @Override
            public void onResponse(Call<MakeTeamResponse> call, Response<MakeTeamResponse> response) {
                // 응답 성공 시
                Log.d("MAKETEAMACTIVITY3", "응답 성공!!!");

                //
                MakeTeamResponse mtResponse = response.body();
                String msg = mtResponse.getMessage();
                // 연결 성공
                if(msg.equals("소모임을 생성하였습니다"))
                {
                    // S3를 통한 사진 업로드
                    File file = new File((getAbsolutePathFromUri(getApplicationContext(),uri)));
                    uploadWithTransferUtility(file.getName(),file);

                    // 다음 화면
                    Intent intent = new Intent(getApplicationContext(), MakeTeamActivity4.class);
                    startActivity(intent);
                }

                else if (msg.equals("만료된 토큰입니다."))
                {
                    /** 만료된 토큰 처리 **/
                    ChangeJwt.updateJwtToken(MakeTeamActivity3.this);

                }

            }

            @Override
            public void onFailure(Call<MakeTeamResponse> call, Throwable t) {
                // 응답 실패 시
                Log.d("MAKETEAMACTIVITY3", "응답 실패 ...");
            }
        });

    };

    /** 버튼 클릭 시 권한 확인 후 갤러리 접근 **/
    View.OnClickListener onImageUploadClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // API 레벨 23 이상 READ_EXTERNAL_STORAGE 권환 필요
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // 권한 X - 요청
                    ActivityCompat.requestPermissions(MakeTeamActivity3.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_REQUEST);
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
                         uri = result.getData().getData();

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
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String result = "(" + editable.length() + "/" + maxLength + ")";
                countTextView.setText(result);
                checkInputs();
            }
        });
    }

    /** 소모임 소개, 각오, 사진이 올바르게 입력되었는지 확인 후 버튼 및 ProgressBar 변경 **/
    private void checkInputs(){
        if(etIntroduce.length() > 0 && etResolution.length() > 0 && ivImageUpload.getVisibility() == View.VISIBLE) {
            btnCreateTeam.setBackgroundResource(R.drawable.button_round_black1);
            btnCreateTeam.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.secondary_grey_black_12));
            btnCreateTeam.setClickable(true);
            ivProgressBar.setBackgroundResource(R.drawable.maketeam_progress5);
        }
        else{
            btnCreateTeam.setTextColor(ContextCompat.getColorStateList(getApplicationContext(), R.color.secondary_grey_black_10));
            btnCreateTeam.setBackgroundResource(R.drawable.button_round_black12);
            btnCreateTeam.setClickable(false);
            ivProgressBar.setBackgroundResource(R.drawable.maketeam_progress4);
        }
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