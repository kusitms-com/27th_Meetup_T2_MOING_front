package com.example.moing.mission;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.moing.R;
import com.example.moing.s3.ImageUtils;
import com.example.moing.team.MakeTeamActivity3;

import java.io.File;

public class MissionClickActivity extends AppCompatActivity {

    private SubsamplingScaleImageView picture;
    private Dialog dialog;
    private ImageButton back_dialog;
    private Button back, mission;
    private TextView curState,d_day,title1,title2;
    private static final int READ_EXTERNAL_STORAGE_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_click);

        // 사진 확대 기능
        picture = (SubsamplingScaleImageView) findViewById(R.id.mission_pic);

        /** 이미지 setting 예시 **/
//        imageView.setImage(ImageSource.resource(R.drawable.monkey));
//        imageView.setImage(ImageSource.asset("map.png"))
//        imageView.setImage(ImageSource.uri("/sdcard/DCIM/DSCM00123.JPG"));

        // 뒤로 가기
        back = findViewById(R.id.btn_close);
        // 인증 상태
        curState = findViewById(R.id.tv_state);
        // d_day
        d_day = findViewById(R.id.tv_dDay);
        // 맨위제목
        title1 = findViewById(R.id.tv_title);
        // 레이아웃 내부 제목
        title2 = findViewById(R.id.tv_title2);
        // 사진 팝업 버튼
        dialog = new Dialog(MissionClickActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.mission_picture_dialog);
        // 다이얼로그에서 뒤로가기 버튼
        back_dialog = dialog.findViewById(R.id.btn_back);
        // 미션인증버튼
        mission = findViewById(R.id.btn_auth);


        // 뒤로 가기 버튼 클릭 리스너
        back.setOnClickListener(backClickListener);
        // 선택 이미지 클릭 리스너
        picture.setOnClickListener(pictureClickListener);
        // 다이얼로그 뒤로 가기 클릭 리스너
        back_dialog.setOnClickListener(dialogBackClickListener);
        // 마션 인증하기 클릭 리스너
        mission.setOnClickListener(missionClickListener);
    }

    // 뒤로 가기 버튼 클릭 시
    View.OnClickListener backClickListener = v -> { finish();};

    // 사진 클릭 시
    View.OnClickListener pictureClickListener = v -> {
        dialog.show();
    };

    // 다이얼로그에서 뒤로 나오기
    View.OnClickListener dialogBackClickListener = v -> {
      dialog.dismiss();
    };

    // 미션 인증 버튼
    View.OnClickListener missionClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // API 레벨 23 이상 READ_EXTERNAL_STORAGE 권환 필요
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // 권한 X - 요청
                ActivityCompat.requestPermissions(MissionClickActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_REQUEST);
            } else {
                // 권한 O - 갤러리 접근
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryLauncher.launch(intent);
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
                        // 업로드할 이미지 파일의 uri
                        Uri imageFileUri = result.getData().getData();

                        picture.setImage(ImageSource.uri(imageFileUri));

                        // Glide 를 통한 이미지 로딩
//                        Glide.with(getApplicationContext())
//                                .load(imageFileUri)
//                                .transform(new RoundedCorners(10))
//                                .into(picture);

                        // visibility 설정
//                        btnImageUpload.setVisibility(View.GONE);
//                        ivImageUpload.setVisibility(View.VISIBLE);
//                        btnImageUploadRe.setVisibility(View.VISIBLE);

                        //checkInputs();

                        // 파일의 절대경로 Get
//                        String filePath = ImageUtils.getAbsolutePathFromUri(getApplicationContext(), imageFileUri);
//                        if (filePath != null) {
//                            // 선택한 사진을 파일로 변환
//                            imageFile = new File(filePath);
//                            // UUID를 사용하여 고유한 파일 이름 생성 ( 파일명.파일확장자.UUID)
//                            uniqueFileNameWithExtension = ImageUtils.generateUniqueFileName(filePath);
//                        }
                    }
                }
            }
    );
}